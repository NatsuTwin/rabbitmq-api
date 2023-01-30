package fr.natsu.rmq.query;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class RequestAnswer implements RequestComponent {

    private final Consumer<Object> consumer;
    private final CompletableFuture<Object> future;

    private RequestAnswer(Builder thisBuilder) {
        this.consumer = thisBuilder.tConsumer;
        this.future = thisBuilder.future;
    }

    public Consumer<Object> getConsumer() {
        return this.consumer;
    }

    public CompletableFuture<Object> getFuture() { return this.future; }

    protected static class Builder implements RequestComponent.Builder {

        private Consumer<Object> tConsumer = ignored -> {};
        private CompletableFuture<Object> future = new CompletableFuture<>();

        public Builder await(Consumer<Object> tConsumer) {
            this.tConsumer = tConsumer;
            return this;
        }

        public Builder future(CompletableFuture<Object> future) {
            this.future = future;
            return this;
        }

        @Override
        public RequestAnswer build() {
            return new RequestAnswer(this);
        }

    }



}
