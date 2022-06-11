package fr.playfull.rmq.event.protocol;

import fr.playfull.rmq.protocol.ProtocolType;

public abstract class ProtocolEvent {

    private final ProtocolType protocolType;
    private final String message;
    private final String queueName;
    private final String extra;

    public ProtocolEvent(ProtocolType protocolType, String queueName, String message, String extra){
        this.protocolType = protocolType;
        this.queueName = queueName;
        this.message = message;
        this.extra = extra;
    }

    public ProtocolType getProtocolType() {
        return protocolType;
    }

    public String getQueueName() {
        return this.queueName;
    }

    public String getMessage() {
        return message;
    }

    public String getExtra() {
        return extra;
    }

}
