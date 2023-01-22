package fr.erarealms.rmq.event.protocol;

import fr.erarealms.rmq.protocol.ProtocolType;

public abstract class MessageReceivedEvent extends ProtocolEvent {

    public MessageReceivedEvent(ProtocolType protocolType, String queueName, Object payload) {
        super(protocolType, queueName, payload);
    }


}
