package fr.playfull.rmq.protocol.client;

import com.rabbitmq.client.AMQP;
import fr.playfull.rmq.RabbitMQRegistration;
import fr.playfull.rmq.query.RPCRequest;
import fr.playfull.rmq.query.Request;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ForkJoinPool;

public class RPCClient extends Client {

    public RPCClient(RabbitMQRegistration mediator) {
        super(mediator);
    }

    @Override
    public void send(Request request) {
        getThreadPool().execute(() -> {
            // Name the thead.
            Thread.currentThread().setName("rpc-client-thread" + UUID.randomUUID());
            // If the request isn't an RPC request
            if(!(request instanceof RPCRequest rpcRequest))
                return;
            try {
                // Message publication. (Managed in single thread).
                String correlationId = UUID.randomUUID().toString();
                String replyQueueName = getChannel().queueDeclare().getQueue();

                AMQP.BasicProperties properties = new AMQP.BasicProperties
                        .Builder()
                        .correlationId(correlationId)
                        .replyTo(replyQueueName)
                        .deliveryMode(1)
                        .build();

                // We publish our request data
                getChannel().basicPublish("", request.getQueue(), properties, getRegistration().getBufferManager().serialize(request.getPayload()));
                RabbitMQRegistration.getLogger().info("[Client] Request sent in queue " + request.getQueue());
                // Answer handling.
                ForkJoinPool.commonPool().execute(() -> {
                    try {
                        BlockingQueue<Object> response = new ArrayBlockingQueue<>(1);
                        String consumerTag = getChannel().basicConsume(replyQueueName, true, (cTag, delivery) -> {
                            if (delivery.getProperties().getCorrelationId().equals(correlationId)) {
                                // We take the offer
                                response.add(getRegistration().getBufferManager().deserialize(delivery.getBody()));
                            }
                        }, cTag -> {});
                        // Mark now instant.
                        Instant start = Instant.now();
                        // We take the result
                        Object result = response.poll(rpcRequest.getRequestTimeout().getTimeout(), rpcRequest.getRequestTimeout().getTimeUnit());
                        // Calculates the answer speed in millis.
                        long duration = Duration.between(start, Instant.now()).toMillis();
                        // Destroy channel
                        getChannel().basicCancel(consumerTag);
                        // Check if we timed out.
                        if(result == null) {
                            // Notice the console that we timed out.
                            RabbitMQRegistration.getLogger().info("[Client] " + request.getQueue() + " timed out in " + duration + "ms.");
                            // Notice the client that we timed out.
                            rpcRequest.getRequestTimeout().getConsumer().accept(true);
                            rpcRequest.getRequestAnswer().getFuture().complete(null);
                        } else {
                            RabbitMQRegistration.getLogger().info("[Client] Received answer in queue " + request.getQueue() + " in " + duration + "ms.");
                            // We consume the answer
                            rpcRequest.getRequestAnswer().getConsumer().accept(result);
                            rpcRequest.getRequestAnswer().getFuture().complete(result);
                        }
                    } catch (IOException | InterruptedException exception) {
                        RabbitMQRegistration.getLogger().error(exception.getMessage());
                        // Notice the client that something wrong happened.
                        rpcRequest.getRequestTimeout().getConsumer().accept(true);
                    }
                });
            } catch (IOException exception) {
                RabbitMQRegistration.getLogger().error(exception.getMessage());
                // Notice the client that something wrong happened.
                rpcRequest.getRequestTimeout().getConsumer().accept(true);
            }
        });
    }
}
