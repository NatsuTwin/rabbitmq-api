package fr.playfull.rmq.query;

import fr.playfull.rmq.marshal.RMQMarshal;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class Request<T> {

    private final RequestAnswer<T> requestAnswer;
    private final RequestTimeout requestTimeout;
    private final RequestMessage requestMessage;
    private final String queueName;

    private Request(Builder<T> builder) {
        this.queueName = builder.queueName;

        this.requestAnswer = new RequestAnswer.Builder<T>()
                .type(builder.tClass)
                .await(builder.consumer)
                .build();

        this.requestTimeout = new RequestTimeout.Builder()
                .timeout(builder.timeout)
                .timeUnit(builder.timeUnit)
                .build();

        this.requestMessage = new RequestMessage.Builder()
                .message(builder.message)
                .extra(builder.extra)
                .marshal(builder.marshal)
                .build();
    }

    public String getQueue() {
        return this.queueName;
    }

    public RequestAnswer<T> getRequestAnswer() {
        return requestAnswer;
    }

    public RequestTimeout getRequestTimeout() {
        return requestTimeout;
    }

    public RequestMessage getRequestMessage() {
        return this.requestMessage;
    }

    public static class Builder<T> {

        private String queueName;
        // RequestAnswer.
        private Consumer<T> consumer = ignored -> {};
        private Class<T> tClass = (Class<T>) Object.class;
        //RequestTimeout
        private int timeout = 5;
        private TimeUnit timeUnit = TimeUnit.SECONDS;
        // RequestMessage
        private RMQMarshal<?> marshal = RMQMarshal.DEFAULT_MARSHAL;
        private String message;
        private String extra = "";

        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public Builder<T> queueName(String queueName) {
            this.queueName = queueName;
            return this;
        }

        public Builder<T> extra(String extra) {
            this.extra = extra;
            return this;
        }

        public Builder<T> marshal(RMQMarshal<?> marshal) {
            this.marshal = marshal;
            return this;
        }

        public Builder<T> timeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        public Builder<T> timeUnit(TimeUnit timeUnit) {
            this.timeUnit = timeUnit;
            return this;
        }

        public Builder<T> type(Class<T> tClass) {
            this.tClass = tClass;
            return this;
        }

        public Builder<T> await(Consumer<T> consumer) {
            this.consumer = consumer;
            return this;
        }

        public Request<T> build() {
            return new Request<>(this);
        }
    }

}
