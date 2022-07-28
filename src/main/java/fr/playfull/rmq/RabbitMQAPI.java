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

import java.io.IOException;
import java.util.concurrent.ExecutorService;

public class RabbitMQAPI {

    private static RabbitMQMediator mediator;
    private RabbitMQAPI(RabbitMQMediator aMediator) {
        mediator = aMediator;
    }

    public static RabbitMQAPI hook(String filePath) throws IOException {
        return new RabbitMQAPI(new RabbitMQMediator(filePath));
    }

    public static void setThreadPool(ProtocolType type, Side side, ExecutorService threadPool) {
        getConnector().overrideThreadPool(type, side, threadPool);
    }

    public static void sendRequest(Request request) {
        getForwarder().send(request);
    }

    public static void listen(ProtocolType protocolType, String queueName) {
        getForwarder().listen(protocolType, queueName);
    }

    public static <T extends ProtocolEvent> void subscribe(Class<T> eventClass, ProtocolListener<T> event) {
        getEventBus().subscribe(eventClass, event);
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
