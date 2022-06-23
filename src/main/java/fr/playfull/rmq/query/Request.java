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

        private String queueName = "default_queue";
        // RequestAnswer.
        private Consumer<Object> consumer = ignored -> {};
        //RequestTimeout
        private int timeout = 5;
        private TimeUnit timeUnit = TimeUnit.SECONDS;
        // Message
        private Object payload = "DEFAULT_PAYLOAD";

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
            return new Request(this);
        }
    }

}
