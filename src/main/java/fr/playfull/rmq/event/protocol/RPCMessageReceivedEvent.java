package fr.playfull.rmq.event.protocol;

import fr.playfull.rmq.protocol.ProtocolType;

import java.util.concurrent.CompletableFuture;

public class RPCMessageReceivedEvent extends MessageReceivedEvent {

    private final CompletableFuture<Object> responseFuture = new CompletableFuture<>();

    public RPCMessageReceivedEvent(String message, String extra) {
        super(ProtocolType.RPC, message, extra);
    }

    public void setAnswer(Object answer) {
        responseFuture.complete(answer);
    }

    public CompletableFuture<Object> getCallback() {
        return responseFuture;
    }
}
