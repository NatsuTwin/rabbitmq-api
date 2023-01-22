package fr.erarealms.rmq.query;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class RequestTimeout implements RequestComponent {

    private final Consumer<Object> timeoutConsumer;
    private final TimeUnit timeUnit;
    private final int timeout;

    private RequestTimeout(Builder builder) {
        this.timeoutConsumer = builder.timeoutConsumer;
        this.timeUnit = builder.timeUnit;
        this.timeout = builder.timeout;
    }

    public Consumer<Object> getConsumer() {
        return timeoutConsumer;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public int getTimeout() {
        return timeout;
    }

    public static class Builder implements RequestComponent.Builder {

        private Consumer<Object> timeoutConsumer = ignored -> {};
        private TimeUnit timeUnit = TimeUnit.SECONDS;
        private int timeout = 5;

        public Builder onTimeout(Consumer<Object> timeoutConsumer) {
            this.timeoutConsumer = timeoutConsumer;
            return this;
        }

        public Builder timeUnit(TimeUnit timeUnit) {
            this.timeUnit = timeUnit;
            return this;
        }

        public Builder timeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        @Override
        public RequestTimeout build() {
            return new RequestTimeout(this);
        }

    }

}
