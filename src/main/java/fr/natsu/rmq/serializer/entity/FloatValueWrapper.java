package fr.natsu.rmq.serializer.entity;

import fr.natsu.rmq.serializer.SerializableType;

public class FloatValueWrapper extends AbstractValueWrapper<Float> {

    public FloatValueWrapper(Float value) {
        super(value);
    }

    @Override
    public SerializableType getType() {
        return SerializableType.FLOAT;
    }
}
