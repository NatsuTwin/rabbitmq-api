package fr.playfull.rmq.query;

import java.util.concurrent.TimeUnit;

public class RequestTimeout implements RequestComponent {

    private final TimeUnit timeUnit;
    private final int timeout;

    private RequestTimeout(Builder builder) {
        this.timeUnit = builder.timeUnit;
        this.timeout = builder.timeout;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public int getTimeout() {
        return timeout;
    }

    public static class Builder implements RequestComponent.Builder {

        private TimeUnit timeUnit = TimeUnit.SECONDS;
        private int timeout = 5;

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
