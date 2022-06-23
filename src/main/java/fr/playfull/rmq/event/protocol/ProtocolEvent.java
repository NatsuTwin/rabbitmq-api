package fr.playfull.rmq.event.protocol;

import fr.playfull.rmq.protocol.ProtocolType;

public abstract class ProtocolEvent {

    private final ProtocolType protocolType;
    private final Object payload;
    private final String queueName;

    public ProtocolEvent(ProtocolType protocolType, String queueName, Object payload){
        this.protocolType = protocolType;
        this.queueName = queueName;
        this.payload = payload;
    }

    public ProtocolType getProtocolType() {
        return protocolType;
    }

    public String getQueueName() {
        return this.queueName;
    }

    public Object getPayload() {
        return payload;
    }

}
