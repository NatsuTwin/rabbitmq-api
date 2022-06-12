package fr.playfull.rmq.query;

import java.util.function.Consumer;

public class RequestAnswer<T> implements RequestComponent {

    private final Consumer<T> tConsumer;
    private final Class<T> tClass;

    private RequestAnswer(Builder<T> thisBuilder) {
        this.tConsumer = thisBuilder.tConsumer;
        this.tClass = thisBuilder.tClass;
    }

    public Consumer<T> getConsumer() {
        return tConsumer;
    }

    public Class<T> getType() {
        return tClass;
    }

    protected static class Builder<T> implements RequestComponent.Builder<Builder<T>> {

        private Class<T> tClass = (Class<T>) Object.class;
        private Consumer<T> tConsumer = ignored -> {};

        public Builder<T> await(Consumer<T> tConsumer) {
            this.tConsumer = tConsumer;
            return self();
        }

        public Builder<T> type(Class<T> tClass) {
            this.tClass = tClass;
            return self();
        }

        @Override
        public RequestAnswer<T> build() {
            return new RequestAnswer<>(this);
        }

        @Override
        public Builder<T> self() {
            return this;
        }
    }



}
