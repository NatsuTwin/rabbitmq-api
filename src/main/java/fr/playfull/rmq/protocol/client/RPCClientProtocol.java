package fr.playfull.rmq.protocol.client;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import fr.playfull.rmq.RabbitMQAPI;
import fr.playfull.rmq.query.Request;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

public class RPCClientProtocol extends ClientProtocol {

    @Override
    public void send(Request request) {
        getThreadPool().execute(() -> {
            try {
                Channel channel = getConnection().createChannel();
                String correlationId = UUID.randomUUID().toString();

                String replyQueueName = channel.queueDeclare().getQueue();
                AMQP.BasicProperties properties = new AMQP.BasicProperties
                        .Builder()
                        .correlationId(correlationId)
                        .replyTo(replyQueueName)
                        .deliveryMode(1)
                        .build();

                StringBuilder requestBuilder = new StringBuilder();
                requestBuilder.append(request.getRequestMessage().getMessage());

                // If there is an extra
                if (request.getRequestMessage().getExtra() != null && !request.getRequestMessage().getExtra().isEmpty())
                    requestBuilder.append(":").append(request.getRequestMessage().getExtra());

                // We publish our request data
                channel.basicPublish("", request.getQueue(), properties, requestBuilder.toString().getBytes());
                RabbitMQAPI.getLogger().info("[Client] Sending request in queue " + request.getQueue());

                BlockingQueue<Object> response = new ArrayBlockingQueue<>(1);

                String consumerTag = channel.basicConsume(replyQueueName, true, (cTag, delivery) -> {
                    if (delivery.getProperties().getCorrelationId().equals(correlationId)) {
                        try {
                            // We take the offer
                            response.offer(request.getRequestMessage().getMarshal().deserialize(delivery.getBody()));
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }, cTag -> {
                });

                // We take the result
                Object result = response.poll(request.getRequestTimeout().getTimeout(), request.getRequestTimeout().getTimeUnit());
                channel.basicCancel(consumerTag);

                RabbitMQAPI.getLogger().info("[Client] Received answer in queue " + request.getQueue());
                // We consume the answer
                request.getRequestAnswer().getConsumer().accept(typeAdapter.get(request.getRequestAnswer().getType(), result));
                channel.close();
            } catch (IOException | InterruptedException | TimeoutException exception) {
                RabbitMQAPI.getLogger().severe(exception.getMessage());
            }
        });
    }
}
