package fr.natsu.rmq.protocol.server;

import com.rabbitmq.client.DeliverCallback;
import fr.natsu.rmq.RabbitMQRegistration;
import fr.natsu.rmq.event.protocol.TCPMessageReceivedEvent;

import java.io.IOException;
import java.util.UUID;

public class TCPServer extends Server {

    public TCPServer(RabbitMQRegistration mediator) {
        super(mediator);
    }

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
                    RabbitMQRegistration.getLogger().info("[Server TCP] Received message in queue " + queue);
                    // retrieve the data.
                    Object data = getRegistration().getBufferManager().deserialize(delivery.getBody());
                    // check if the data isn't null.
                    // else don't fire the event to avoid NullPointExceptions.
                    if(data != null) {
                        // We publish the event.
                        getRegistration().getEventBus().publish(new TCPMessageReceivedEvent(queue, data));
                    } else {
                        RabbitMQRegistration.getLogger().info("[Server TCP] Consumer tried to pass NULL data into " + queue);
                    }
                };
                getChannel().basicConsume(queue, true, deliverCallback, cancelled -> {});
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
    }
}
