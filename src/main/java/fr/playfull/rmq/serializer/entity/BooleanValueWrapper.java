package fr.playfull.rmq.serializer.entity;

import fr.playfull.rmq.serializer.SerializableType;

public class BooleanValueWrapper extends AbstractValueWrapper<Boolean> {

    public BooleanValueWrapper(Boolean value) {
        super(value);
    }

    @Override
    public SerializableType getType() {
        return SerializableType.BOOLEAN;
    }
}
