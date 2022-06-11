package fr.playfull.rmq.event.protocol;

import fr.playfull.rmq.protocol.ProtocolType;

public class TCPMessageReceivedEvent extends MessageReceivedEvent {

    public TCPMessageReceivedEvent(String queueName, String message, String extra) {
        super(ProtocolType.TCP, queueName, message, extra);
    }
}
