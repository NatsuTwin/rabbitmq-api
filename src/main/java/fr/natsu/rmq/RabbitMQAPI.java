package fr.natsu.rmq;

import fr.natsu.rmq.event.protocol.ProtocolEvent;
import fr.natsu.rmq.event.protocol.ProtocolListener;
import fr.natsu.rmq.query.Request;
import fr.natsu.rmq.protocol.ProtocolType;
import fr.natsu.rmq.protocol.Side;
import fr.natsu.rmq.serializer.RMQSerializable;
import fr.natsu.rmq.serializer.factory.SerializableFactory;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

public class RabbitMQAPI {

    private static RabbitMQRegistration registration;

    private RabbitMQAPI(RabbitMQRegistration aMediator) {
        registration = aMediator;
    }

    public static void disconnectAll() {
        registration.getMediator().disconnectAll();
    }

    public static void connectAll() {
        // Then we connect.
        registration.getMediator().connectAll();
    }

    public static RabbitMQAPI hook(String filePath) throws IOException {
        return new RabbitMQAPI(new RabbitMQRegistration(filePath));
    }

    public static void setThreadPool(ProtocolType type, Side side, ExecutorService threadPool) {
        registration.getMediator().allocate(type, side, threadPool);
    }

    public static void sendRequest(Request request) {
        registration.getMediator().send(request);
    }

    public static void listen(ProtocolType protocolType, String queueName) {
        registration.getMediator().listen(protocolType, queueName);
    }

    public static <T extends ProtocolEvent> void subscribe(Class<T> eventClass, ProtocolListener<T> event) {
        registration.getEventBus().subscribe(eventClass, event);
    }

    public static <T extends RMQSerializable> void addFactory(Class<T> clazz, SerializableFactory<T> factory) {
        registration.getBufferManager().addFactory(clazz, factory);
    }
}
