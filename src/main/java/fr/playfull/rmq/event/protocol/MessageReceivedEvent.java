package fr.playfull.rmq.event.protocol;

import fr.playfull.rmq.protocol.ProtocolType;

import java.util.concurrent.CompletableFuture;

public abstract class MessageReceivedEvent extends ProtocolEvent {

    public MessageReceivedEvent(ProtocolType protocolType, String queueName, Object payload) {
        super(protocolType, queueName, payload);
    }


}
