package fr.natsu.rmq.protocol;

import fr.natsu.rmq.connect.DefaultConnector;
import fr.natsu.rmq.protocol.store.ProtocolBucket;
import fr.natsu.rmq.query.Request;
import fr.natsu.rmq.allocate.DefaultThreadPoolAllocator;
import fr.natsu.rmq.allocate.ThreadPoolAllocator;
import fr.natsu.rmq.connect.Connector;
import fr.natsu.rmq.connect.Credentials;
import fr.natsu.rmq.forward.Forwarder;

import java.util.concurrent.ExecutorService;

public class ProtocolMediator {

    private static ProtocolMediator mediator;

    private final Credentials credentials;
    private final Forwarder forwarder;
    private final Connector connector;
    private final ThreadPoolAllocator allocator;
    private ProtocolMediator(Credentials credentials, ProtocolBucket bucket) {
        mediator = this;

        this.credentials = credentials;
        this.forwarder = new Forwarder(bucket);
        this.connector = new DefaultConnector(bucket);
        this.allocator = new DefaultThreadPoolAllocator(bucket);
    }

    public static ProtocolMediator of(Credentials credentials, ProtocolBucket bucket) {
        return mediator != null ? mediator : new ProtocolMediator(credentials, bucket);
    }

    public ProtocolMediator connectAll() {
        connector.connectAll(credentials);
        return mediator;
    }

    public ProtocolMediator disconnectAll() {
        connector.disconnectAll();
        return mediator;
    }

    public ProtocolMediator allocate(ProtocolType protocolType, Side side, ExecutorService threadPool) {
        allocator.allocate(protocolType, side, threadPool);
        return mediator;
    }

    public void send(Request request) {
        forwarder.send(request);
    }

    public void listen(ProtocolType protocolType, String queueName) {
        forwarder.listen(protocolType, queueName);
    }

}
