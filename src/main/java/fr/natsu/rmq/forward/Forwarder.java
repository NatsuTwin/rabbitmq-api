package fr.natsu.rmq.forward;

import fr.natsu.rmq.protocol.ProtocolType;
import fr.natsu.rmq.protocol.store.ProtocolBucket;
import fr.natsu.rmq.query.Request;

public class Forwarder {

    private final ProtocolBucket protocolBucket;
    public Forwarder(ProtocolBucket protocolBucket) {
        this.protocolBucket = protocolBucket;
    }

    public void send(Request request) {
        protocolBucket.getClientServerPairOf(request.getType()).client().send(request);
    }

    public void listen(ProtocolType protocolType, String queueName) {
        protocolBucket.getClientServerPairOf(protocolType).server().listen(queueName);
    }

}
