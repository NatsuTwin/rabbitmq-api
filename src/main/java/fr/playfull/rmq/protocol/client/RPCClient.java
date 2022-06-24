package fr.playfull.rmq.protocol.client;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import fr.playfull.rmq.RabbitMQAPI;
import fr.playfull.rmq.query.RPCRequest;
import fr.playfull.rmq.query.Request;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

public class RPCClient extends Client {

    @Override
    public void send(Request request) {
        getThreadPool().execute(() -> {
            try {
                Channel channel = getConnection().createChannel();

                // If the request is not a RPC request
                if(!(request instanceof RPCRequest rpcRequest))
                    return;

                String correlationId = UUID.randomUUID().toString();

                String replyQueueName = channel.queueDeclare().getQueue();
                AMQP.BasicProperties properties = new AMQP.BasicProperties
                        .Builder()
                        .correlationId(correlationId)
                        .replyTo(replyQueueName)
                        .deliveryMode(1)
                        .build();

                // We publish our request data
                channel.basicPublish("", request.getQueue(), properties, RabbitMQAPI.getBufferManager().serialize(request.getPayload()));
                RabbitMQAPI.getLogger().info("[Client] Sending request in queue " + request.getQueue());

                BlockingQueue<Object> response = new ArrayBlockingQueue<>(1);

                String consumerTag = channel.basicConsume(replyQueueName, true, (cTag, delivery) -> {
                    if (delivery.getProperties().getCorrelationId().equals(correlationId)) {
                        // We take the offer
                        response.offer(RabbitMQAPI.getBufferManager().deserialize(delivery.getBody()));
                    }
                }, cTag -> {
                });

                // We take the result
                Object result = response.poll(rpcRequest.getRequestTimeout().getTimeout(), rpcRequest.getRequestTimeout().getTimeUnit());
                channel.basicCancel(consumerTag);

                RabbitMQAPI.getLogger().info("[Client] Received answer in queue " + request.getQueue());
                // We consume the answer
                rpcRequest.getRequestAnswer().getConsumer().accept(result);
                channel.close();
            } catch (IOException | InterruptedException | TimeoutException exception) {
                RabbitMQAPI.getLogger().severe(exception.getMessage());
            }
        });
    }
}
