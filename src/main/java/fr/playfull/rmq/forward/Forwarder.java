package fr.playfull.rmq.forward;

import fr.playfull.rmq.marshal.RMQMarshal;
import fr.playfull.rmq.protocol.ProtocolType;
import fr.playfull.rmq.query.Request;

public class Forwarder {

    public void send(ProtocolType protocolType, Request<?> request) {
        protocolType.getClientProtocol().send(request);
    }

    public void listen(ProtocolType protocolType, String queueName, RMQMarshal marshal) {
        protocolType.getServerProtocol().listen(queueName, marshal);
    }

}
