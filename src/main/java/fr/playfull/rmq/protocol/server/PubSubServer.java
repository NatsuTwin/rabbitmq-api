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
                    getRegistration().getEventBus().publish(new TCPMessageReceivedEvent(exchange, getRegistration().getBufferManager().deserialize(delivery.getBody())));
                };
                getChannel().basicConsume(queueName, true, deliverCallback, consumerTag -> { });
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
    }
}
