package fr.erarealms.rmq.event.protocol;

import fr.erarealms.rmq.protocol.ProtocolType;

import java.util.concurrent.CompletableFuture;

public class RPCMessageReceivedEvent extends MessageReceivedEvent {

    private final CompletableFuture<Object> responseFuture = new CompletableFuture<>();

    public RPCMessageReceivedEvent(String queueName, Object payload) {
        super(ProtocolType.RPC, queueName, payload);
    }

    public void setAnswer(Object answer) {
        responseFuture.complete(answer);
    }

    public CompletableFuture<Object> getCallback() {
        return responseFuture;
    }
}
