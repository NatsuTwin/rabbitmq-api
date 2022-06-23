package fr.playfull.rmq.forward;

import fr.playfull.rmq.protocol.ProtocolBucket;
import fr.playfull.rmq.protocol.ProtocolType;
import fr.playfull.rmq.query.Request;

public class Forwarder {

    private final ProtocolBucket protocolBucket;
    public Forwarder(ProtocolBucket protocolBucket) {
        this.protocolBucket = protocolBucket;
    }

    public void send(ProtocolType protocolType, Request request) {
        protocolBucket.getClientServerPairOf(protocolType).client().send(request);
    }

    public void listen(ProtocolType protocolType, String queueName) {
        protocolBucket.getClientServerPairOf(protocolType).server().listen(queueName);
    }

}
