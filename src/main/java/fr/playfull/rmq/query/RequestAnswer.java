package fr.playfull.rmq.query;

import java.util.function.Consumer;

public class RequestAnswer implements RequestComponent {

    private final Consumer<Object> consumer;

    private RequestAnswer(Builder thisBuilder) {
        this.consumer = thisBuilder.tConsumer;
    }

    public Consumer<Object> getConsumer() {
        return this.consumer;
    }

    protected static class Builder implements RequestComponent.Builder {

        private Consumer<Object> tConsumer = ignored -> {};

        public Builder await(Consumer<Object> tConsumer) {
            this.tConsumer = tConsumer;
            return this;
        }

        @Override
        public RequestAnswer build() {
            return new RequestAnswer(this);
        }

    }



}
