package fr.playfull.rmq.event.protocol;

import fr.playfull.rmq.protocol.ProtocolType;

public class TCPMessageReceivedEvent extends MessageReceivedEvent {

    public TCPMessageReceivedEvent(String queueName, Object payload) {
        super(ProtocolType.TCP, queueName, payload);
    }
}
