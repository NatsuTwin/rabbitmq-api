package fr.playfull.rmq.protocol.server;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DeliverCallback;
import fr.playfull.rmq.RabbitMQRegistration;
import fr.playfull.rmq.event.protocol.RPCMessageReceivedEvent;

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
                        this.actualListenedEvent = new RPCMessageReceivedEvent(queue, getRegistration().getBufferManager().deserialize(delivery.getBody()));
                        getRegistration().getEventBus().publish(actualListenedEvent);
                    } catch(RuntimeException runtimeException) {
                        runtimeException.printStackTrace();
                    } finally {
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
                };

                getChannel().basicConsume(queue, false, deliverCallback, consumerTag -> {});
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
    }
}
