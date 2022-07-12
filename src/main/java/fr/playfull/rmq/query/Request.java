package fr.playfull.rmq.query;

import fr.playfull.rmq.protocol.ProtocolType;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.Objects;
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
        protected Consumer<Object> answerConsumer = ignored -> {};
        // Request timeout
        protected Consumer<Object> timeoutConsumer = ignored -> {};
        //RequestTimeout
        protected int timeout = 5;
        protected TimeUnit timeUnit = TimeUnit.SECONDS;
        // Message
        protected Object payload = "DEFAULT_PAYLOAD";
        protected final ProtocolType protocolType;

        public Builder(ProtocolType protocolType) {
            this.protocolType = protocolType;
        }

        public Builder payload(@Nonnull Object payload) {
            this.payload = Objects.requireNonNull(payload);
            return this;
        }

        public Builder queueName(@Nonnull String queueName) {
            this.queueName = Objects.requireNonNull(queueName);
            return this;
        }

        public Builder onTimeout(@Nonnull Consumer<Object> timeoutConsumer) {
            this.timeoutConsumer = Objects.requireNonNull(timeoutConsumer);
            return this;
        }

        public Builder timeout(@Nonnegative int timeout) {
            this.timeout = Math.abs(timeout);
            return this;
        }

        public Builder timeUnit(@Nonnull TimeUnit timeUnit) {
            this.timeUnit = Objects.requireNonNull(timeUnit);
            return this;
        }

        public Builder await(@Nonnull Consumer<Object> consumer) {
            this.answerConsumer = Objects.requireNonNull(consumer);
            return this;
        }

        public Request build() {
            return this.protocolType == ProtocolType.RPC ? new RPCRequest(this) : new TCPRequest(this);
        }
    }

}
