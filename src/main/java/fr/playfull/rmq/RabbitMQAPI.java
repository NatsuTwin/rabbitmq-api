package fr.playfull.rmq;

import fr.playfull.rmq.connect.Connector;
import fr.playfull.rmq.event.EventBus;
import fr.playfull.rmq.event.protocol.ProtocolEvent;
import fr.playfull.rmq.event.protocol.ProtocolListener;
import fr.playfull.rmq.forward.Forwarder;
import fr.playfull.rmq.protocol.ProtocolType;
import fr.playfull.rmq.protocol.Side;
import fr.playfull.rmq.query.Request;
import fr.playfull.rmq.serializer.ByteSerializableBufferManager;
import fr.playfull.rmq.serializer.RMQSerializable;
import fr.playfull.rmq.serializer.factory.SerializableFactory;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.ExecutorService;

public class RabbitMQAPI {

    private static RabbitMQMediator mediator;
    private static RabbitMQAPI instance;
    private RabbitMQAPI(RabbitMQMediator aMediator) {
        instance = this;
        mediator = aMediator;
    }

    @Nullable
    private static RabbitMQAPI getInstance() {
        return instance != null ? instance : null;
    }

    public static RabbitMQAPI hook(String filePath) throws IOException {
        return new RabbitMQAPI(new RabbitMQMediator(filePath));
    }

    public static RabbitMQAPI setThreadPool(ProtocolType type, Side side, ExecutorService threadPool) {
        getConnector().overrideThreadPool(type, side, threadPool);
        return getInstance();
    }

    public static RabbitMQAPI sendRequest(Request request) {
        getForwarder().send(request);
        return getInstance();
    }

    public static RabbitMQAPI listen(ProtocolType protocolType, String queueName) {
        getForwarder().listen(protocolType, queueName);
        return getInstance();
    }

    public static <T extends ProtocolEvent> RabbitMQAPI subscribe(Class<T> eventClass, ProtocolListener<T> event) {
        getEventBus().subscribe(eventClass, event);
        return getInstance();
    }

    public static <T extends RMQSerializable> RabbitMQAPI addFactory(Class<T> clazz, SerializableFactory<T> factory) {
        getBufferManager().addFactory(clazz, factory);
        return getInstance();
    }

    @Deprecated
    public static ByteSerializableBufferManager getBufferManager() {
        return mediator.getBufferManager();
    }

    @Deprecated
    public static Forwarder getForwarder() {
        return mediator.getForwarder();
    }

    @Deprecated
    public static EventBus getEventBus() {
        return mediator.getEventBus();
    }

    @Deprecated
    public static Connector getConnector() {
        return mediator.getConnector();
    }
}
