package fr.playfull.rmq.query;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class Request {

    private final RequestAnswer requestAnswer;
    private final RequestTimeout requestTimeout;
    private final Object payload;
    private final String queueName;

    private Request(Builder builder) {
        this.queueName = builder.queueName;

        this.requestAnswer = new RequestAnswer.Builder()
                .await(builder.consumer)
                .build();

        this.requestTimeout = new RequestTimeout.Builder()
                .timeout(builder.timeout)
                .timeUnit(builder.timeUnit)
                .build();

        this.payload = builder.payload;
    }

    public String getQueue() {
        return this.queueName;
    }

    public RequestAnswer getRequestAnswer() {
        return requestAnswer;
    }

    public RequestTimeout getRequestTimeout() {
        return requestTimeout;
    }

    public Object getPayload() {
        return this.payload;
    }

    public static class Builder {

        private final String queueName;
        // RequestAnswer.
        private Consumer<Object> consumer = ignored -> {};
        //RequestTimeout
        private int timeout = 5;
        private TimeUnit timeUnit = TimeUnit.SECONDS;
        // Message
        private final Object payload;

        public Builder(String queueName, Object payload) {
            this.queueName = queueName;
            this.payload = payload;
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
            return new Request(this);
        }
    }

}
