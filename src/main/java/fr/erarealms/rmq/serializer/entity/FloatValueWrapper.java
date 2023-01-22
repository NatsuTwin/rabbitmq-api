package fr.erarealms.rmq.serializer.entity;

import fr.erarealms.rmq.serializer.SerializableType;

public class FloatValueWrapper extends AbstractValueWrapper<Float> {

    public FloatValueWrapper(Float value) {
        super(value);
    }

    @Override
    public SerializableType getType() {
        return SerializableType.FLOAT;
    }
}
