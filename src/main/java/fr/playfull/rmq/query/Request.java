package fr.playfull.rmq.query;

import fr.playfull.rmq.protocol.ProtocolType;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public abstract class Request {

    private final ProtocolType protocolType;
    private final Object payload;
    private final String queueName;

    protected Request(Builder builder) {
        this.protocolType = builder.protocolType;
        this.queueName = builder.queueName;
        this.payload = builder.payload;
    }

    public ProtocolType getType() {
        return this.protocolType;
    }

    public String getQueue() {
        return this.queueName;
    }

    public Object getPayload() {
        return this.payload;
    }

    public static class Builder {

        protected String queueName = "default_queue";
        // RequestAnswer.
        protected Consumer<Object> consumer = ignored -> {};
        //RequestTimeout
        protected int timeout = 5;
        protected TimeUnit timeUnit = TimeUnit.SECONDS;
        // Message
        protected Object payload = "DEFAULT_PAYLOAD";
        protected final ProtocolType protocolType;

        public Builder(ProtocolType protocolType) {
            this.protocolType = protocolType;
        }


        public Builder payload(Object payload) {
            this.payload = payload;
            return this;
        }

        public Builder queueName(String queueName) {
            this.queueName = queueName;
            return this;
        }

        public Builder timeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        public Builder timeUnit(TimeUnit timeUnit) {
            this.timeUnit = timeUnit;
            return this;
        }

        public Builder await(Consumer<Object> consumer) {
            this.consumer = consumer;
            return this;
        }

        public Request build() {
            return this.protocolType == ProtocolType.RPC ? new RPCRequest(this) : new TCPRequest(this);
        }
    }

}
