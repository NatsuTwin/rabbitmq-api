package fr.playfull.rmq.protocol.client;

import com.rabbitmq.client.AMQP;
import fr.playfull.rmq.RabbitMQAPI;
import fr.playfull.rmq.query.RPCRequest;
import fr.playfull.rmq.query.Request;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class RPCClient extends Client {

    @Override
    public void send(Request request) {
        getThreadPool().execute(() -> {
            Thread.currentThread().setName("rpc-client-thread" + UUID.randomUUID());

            // If the request isn't an RPC request
            if(!(request instanceof RPCRequest rpcRequest))
                return;

            try {
                String correlationId = UUID.randomUUID().toString();
                String replyQueueName = getChannel().queueDeclare().getQueue();

                AMQP.BasicProperties properties = new AMQP.BasicProperties
                        .Builder()
                        .correlationId(correlationId)
                        .replyTo(replyQueueName)
                        .deliveryMode(1)
                        .build();

                // We publish our request data
                getChannel().basicPublish("", request.getQueue(), properties, RabbitMQAPI.getBufferManager().serialize(request.getPayload()));
                RabbitMQAPI.getLogger().info("[Client] Sending request in queue " + request.getQueue());

                BlockingQueue<Object> response = new ArrayBlockingQueue<>(1);

                String consumerTag = getChannel().basicConsume(replyQueueName, true, (cTag, delivery) -> {
                    if (delivery.getProperties().getCorrelationId().equals(correlationId)) {
                        // We take the offer
                        response.add(RabbitMQAPI.getBufferManager().deserialize(delivery.getBody()));
                    }
                }, cTag -> {
                });

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
                    RabbitMQAPI.getLogger().info("[Client] " + request.getQueue() + " timed out in " + duration + "ms.");
                    // Notice the client that we timed out.
                    rpcRequest.getRequestTimeout().getConsumer().accept(true);
                } else {
                    RabbitMQAPI.getLogger().info("[Client] Received answer in queue " + request.getQueue() + " in " + duration + "ms.");
                    // We consume the answer
                    rpcRequest.getRequestAnswer().getConsumer().accept(result);
                }
            } catch (IOException | InterruptedException exception) {
                RabbitMQAPI.getLogger().error(exception.getMessage());
                // Notice the client that something wrong happened.
                rpcRequest.getRequestTimeout().getConsumer().accept(true);
            }
        });
    }
}
