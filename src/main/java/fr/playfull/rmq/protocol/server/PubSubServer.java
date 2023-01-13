package fr.playfull.rmq.protocol.server;

import com.rabbitmq.client.DeliverCallback;
import fr.playfull.rmq.RabbitMQRegistration;
import fr.playfull.rmq.event.protocol.TCPMessageReceivedEvent;

import java.io.IOException;

public class PubSubServer extends Server {

    public PubSubServer(RabbitMQRegistration mediator) {
        super(mediator);
    }

    @Override
    public void listen(String exchange) {
        getThreadPool().execute(() -> {
            try {
                getChannel().exchangeDeclare(exchange, "fanout");
                String queueName = getChannel().queueDeclare().getQueue();
                getChannel().queueBind(queueName, exchange, "");

                RabbitMQRegistration.getLogger().info(" [PubSub] Waiting for messages with exchange name : " + exchange + " (queue name : " + queueName + ")");

                DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                    RabbitMQRegistration.getLogger().info("[PUBSUB Server] Received message with exchange name : " + exchange + ".");
                    // retrieve the data.
                    Object data = getRegistration().getBufferManager().deserialize(delivery.getBody());
                    // check if the data isn't null.
                    // else don't fire the event to avoid NullPointExceptions.
                    if(data != null) {
                        // We publish the event.
                        getRegistration().getEventBus().publish(new TCPMessageReceivedEvent(exchange, data));
                    } else {
                        RabbitMQRegistration.getLogger().info("[PUBSUB Server] Consumer tried to pass NULL data into " + queueName);
                    }
                };
                getChannel().basicConsume(queueName, true, deliverCallback, consumerTag -> { });
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
    }
}
