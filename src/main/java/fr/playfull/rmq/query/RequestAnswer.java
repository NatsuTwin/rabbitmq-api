package fr.playfull.rmq.query;

import java.util.Objects;
import java.util.function.Consumer;

public class RequestAnswer<T> implements RequestComponent {

    private final Consumer<T> tConsumer;
    private final Class<T> tClass;

    private RequestAnswer(ProtocolAnswerBuilder<T> thisBuilder) {
        this.tConsumer = Objects.requireNonNullElse(thisBuilder.tConsumer, ignored -> {});
        this.tClass = thisBuilder.tClass;
    }

    public Consumer<T> getConsumer() {
        return tConsumer;
    }

    public Class<T> getType() {
        return tClass;
    }

    protected static class ProtocolAnswerBuilder<T> implements RequestComponent.Builder<ProtocolAnswerBuilder<T>> {

        private Class<T> tClass;
        private Consumer<T> tConsumer;

        public ProtocolAnswerBuilder<T> await(Consumer<T> tConsumer) {
            this.tConsumer = tConsumer;
            return self();
        }

        public ProtocolAnswerBuilder<T> type(Class<T> tClass) {
            this.tClass = tClass;
            return self();
        }

        @Override
        public RequestAnswer<T> build() {
            return new RequestAnswer<>(this);
        }

        @Override
        public ProtocolAnswerBuilder<T> self() {
            return this;
        }
    }



}
