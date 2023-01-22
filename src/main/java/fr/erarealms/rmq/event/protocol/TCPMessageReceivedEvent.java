package fr.erarealms.rmq.event.protocol;

import fr.erarealms.rmq.protocol.ProtocolType;

public class TCPMessageReceivedEvent extends MessageReceivedEvent {

    public TCPMessageReceivedEvent(String queueName, Object payload) {
        super(ProtocolType.TCP, queueName, payload);
    }
}
