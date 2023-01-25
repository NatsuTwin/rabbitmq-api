package fr.natsu.rmq.event.protocol;

import fr.natsu.rmq.protocol.ProtocolType;

public class TCPMessageReceivedEvent extends MessageReceivedEvent {

    public TCPMessageReceivedEvent(String queueName, Object payload) {
        super(ProtocolType.TCP, queueName, payload);
    }
}
