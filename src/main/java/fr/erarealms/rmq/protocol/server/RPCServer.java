package fr.erarealms.rmq.protocol.server;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DeliverCallback;
import fr.erarealms.rmq.RabbitMQRegistration;
import fr.erarealms.rmq.event.protocol.RPCMessageReceivedEvent;

import java.io.IOException;
import java.util.UUID;

public class RPCServer extends Server {

    private RPCMessageReceivedEvent actualListenedEvent;

    public RPCServer(RabbitMQRegistration mediator) {
        super(mediator);
    }

    @Override
    public void listen(String queue) {
        getThreadPool().execute(() -> {
            Thread.currentThread().setName("rpc-server-thread" + UUID.randomUUID());
            try{
                // We declare & purge our queue
                getChannel().queueDeclare(queue, false, false, false, null);
                getChannel().queuePurge(queue);
                getChannel().basicQos(0);

                Object objectMonitor = new Object();

                DeliverCallback deliverCallback = (s, delivery) -> {
                    AMQP.BasicProperties properties = new AMQP.BasicProperties
                            .Builder()
                            .correlationId(delivery.getProperties().getCorrelationId())
                            .build();

                    try {
                        RabbitMQRegistration.getLogger().info("[Server] Received request in queue " + queue);
                        // retrieve the data.
                        Object data = getRegistration().getBufferManager().deserialize(delivery.getBody());
                        // check if the data isn't null.
                        // else don't fire the event to avoid NullPointExceptions.
                        if(data != null) {
                            this.actualListenedEvent = new RPCMessageReceivedEvent(queue, data);
                            getRegistration().getEventBus().publish(actualListenedEvent);
                        } else {
                            RabbitMQRegistration.getLogger().info("[Server] Consumer tried to pass NULL data into " + queue);
                        }
                    } catch(RuntimeException runtimeException) {
                        runtimeException.printStackTrace();
                    } finally {
                        if(actualListenedEvent != null) {
                            // We serialize our object and publish it
                            actualListenedEvent.getCallback().thenAccept(answer -> {
                                try {
                                    RabbitMQRegistration.getLogger().info("[Server] Sending answer in queue " + queue);
                                    getChannel().basicPublish("", delivery.getProperties().getReplyTo(), properties, getRegistration().getBufferManager().serialize(answer));
                                    getChannel().basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                                    synchronized(objectMonitor){
                                        objectMonitor.notify();
                                    }
                                } catch(IOException exception) {
                                    exception.printStackTrace();
                                }
                            });
                        }
                    }
                };

                getChannel().basicConsume(queue, false, deliverCallback, consumerTag -> {});
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
    }
}
