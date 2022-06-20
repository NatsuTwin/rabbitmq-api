package fr.playfull.rmq.protocol.server;

import com.rabbitmq.client.DeliverCallback;
import fr.playfull.rmq.RabbitMQAPI;
import fr.playfull.rmq.event.protocol.TCPMessageReceivedEvent;
import fr.playfull.rmq.marshal.RMQMarshal;

import java.io.IOException;

public class TCPServerProtocol extends ServerProtocol {

    @Override
    public void listen(String queue, RMQMarshal marshal) {
        getThreadPool().execute(() -> {
            try {

                // We declare and purge our queue
                getChannel().queueDeclare(queue, false, false, false, null);
                getChannel().queuePurge(queue);
                getChannel().basicQos(0);

                DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                    RabbitMQAPI.getLogger().info("[Server TCP] Received message in queue " + queue);
                    String message = new String(delivery.getBody(), "UTF-8");
                    String extra = "";
                    if(message.contains(":")) {
                        extra = message.split(":")[1];
                        message = message.split(":")[0];
                    }
                    // We publish the event.
                    TCPMessageReceivedEvent messageReceivedEvent = new TCPMessageReceivedEvent(queue, message, extra);
                    RabbitMQAPI.getEventBus().publish(messageReceivedEvent);
                };
                getChannel().basicConsume(queue, true, deliverCallback, consumerTag -> { });
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
    }
}
