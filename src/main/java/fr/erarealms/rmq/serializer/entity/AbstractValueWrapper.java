package fr.erarealms.rmq.serializer.entity;

public abstract class AbstractValueWrapper<T> implements ValueWrapper<T> {

    private final T value;
    public AbstractValueWrapper(T value) {
        this.value = value;
    }

    @Override
    public T getValue() {
        return value;
    }
}
