package fr.playfull.rmq.protocol.server;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DeliverCallback;
import fr.playfull.rmq.RabbitMQAPI;
import fr.playfull.rmq.event.protocol.RPCMessageReceivedEvent;
import fr.playfull.rmq.marshal.RMQMarshal;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class RPCServerProtocol extends ServerProtocol {

    private RPCMessageReceivedEvent actualListenedEvent;

    @Override
    public void listen(String queue, RMQMarshal marshal) {
        getThreadPool().execute(() -> {
            try{
                // We declare & purge our queue
                getChannel().queueDeclare(queue, false, false, false, null);
                getChannel().queuePurge(queue);
                getChannel().basicQos(0);

                Object objectMonitor = new Object();
                AtomicReference<Object> response = new AtomicReference<>();

                DeliverCallback deliverCallback = (s, delivery) -> {
                    AMQP.BasicProperties properties = new AMQP.BasicProperties
                            .Builder()
                            .correlationId(delivery.getProperties().getCorrelationId())
                            .build();

                    try {
                        RabbitMQAPI.getLogger().info("[Server] Received request in queue " + queue);
                        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);

                        String extra = "";

                        if(message.contains(":")) {
                            extra = message.split(":")[1];
                            message = message.split(":")[0];
                        }

                        this.actualListenedEvent = new RPCMessageReceivedEvent(queue, message, extra);

                        RabbitMQAPI.getEventBus().publish(actualListenedEvent);
                    } catch(RuntimeException runtimeException) {
                        runtimeException.printStackTrace();
                    } finally {
                        // We serialize our object and publish it
                        actualListenedEvent.getCallback().thenAccept(answer -> {
                            response.set(answer);
                            try {
                                RabbitMQAPI.getLogger().info("[Server] Sending answer in queue " + queue);
                                getChannel().basicPublish("", delivery.getProperties().getReplyTo(), properties, marshal.serialize(response.get()));
                                getChannel().basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                            } catch (IOException exception) {
                                exception.printStackTrace();
                            }
                        });
                        synchronized(objectMonitor){
                            objectMonitor.notify();
                        }
                    }
                };

                getChannel().basicConsume(queue, false, deliverCallback, consumerTag -> {});

                while(true) {
                    synchronized (objectMonitor) {
                        try {
                            objectMonitor.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
    }
}
