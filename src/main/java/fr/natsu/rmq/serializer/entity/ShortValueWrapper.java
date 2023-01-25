package fr.natsu.rmq.serializer.entity;

import fr.natsu.rmq.serializer.SerializableType;

public class ShortValueWrapper extends AbstractValueWrapper<Short> {

    public ShortValueWrapper(Short value) {
        super(value);
    }

    @Override
    public SerializableType getType() {
        return SerializableType.SHORT;
    }
}
