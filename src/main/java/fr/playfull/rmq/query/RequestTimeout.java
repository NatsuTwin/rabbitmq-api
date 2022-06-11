package fr.playfull.rmq.query;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class RequestTimeout implements RequestComponent {

    private final TimeUnit timeUnit;
    private final int timeout;

    private RequestTimeout(TimeoutBuilder builder) {
        this.timeUnit = Objects.requireNonNullElse(builder.timeUnit, TimeUnit.SECONDS);
        this.timeout = Objects.requireNonNullElse(builder.timeout, 5);
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public int getTimeout() {
        return timeout;
    }

    public static class TimeoutBuilder implements RequestComponent.Builder<TimeoutBuilder> {

        private TimeUnit timeUnit;
        private int timeout;

        public TimeoutBuilder timeUnit(TimeUnit timeUnit) {
            this.timeUnit = timeUnit;
            return self();
        }

        public TimeoutBuilder timeout(int timeout) {
            this.timeout = timeout;
            return self();
        }

        @Override
        public RequestTimeout build() {
            return null;
        }

        @Override
        public TimeoutBuilder self() {
            return this;
        }
    }

}
