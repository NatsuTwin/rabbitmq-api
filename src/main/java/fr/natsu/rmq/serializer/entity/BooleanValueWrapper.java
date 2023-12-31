package fr.natsu.rmq.serializer.entity;

import fr.natsu.rmq.serializer.SerializableType;

public class BooleanValueWrapper extends AbstractValueWrapper<Boolean> {

    public BooleanValueWrapper(Boolean value) {
        super(value);
    }

    @Override
    public SerializableType getType() {
        return SerializableType.BOOLEAN;
    }
}
