package fr.playfull.rmq.protocol.server;

import com.rabbitmq.client.DeliverCallback;
import fr.playfull.rmq.RabbitMQAPI;
import fr.playfull.rmq.event.protocol.TCPMessageReceivedEvent;

import java.io.IOException;
import java.util.UUID;

public class TCPServer extends Server {

    @Override
    public void listen(String queue) {
        getThreadPool().execute(() -> {
            Thread.currentThread().setName("tcp-server-thread" + UUID.randomUUID());
            try {
                // We declare and purge our queue
                getChannel().queueDeclare(queue, false, false, false, null);
                getChannel().queuePurge(queue);
                getChannel().basicQos(0);

                DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                    RabbitMQAPI.getLogger().info("[Server TCP] Received message in queue " + queue);
                    // We publish the event.
                    TCPMessageReceivedEvent messageReceivedEvent = new TCPMessageReceivedEvent(queue, RabbitMQAPI.getBufferManager().deserialize(delivery.getBody()));
                    RabbitMQAPI.getEventBus().publish(messageReceivedEvent);
                };
                getChannel().basicConsume(queue, false, deliverCallback, cancelled -> {});
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
    }
}
