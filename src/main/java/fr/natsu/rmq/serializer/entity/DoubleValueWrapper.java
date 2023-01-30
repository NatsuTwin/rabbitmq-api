package fr.natsu.rmq.serializer.entity;

import fr.natsu.rmq.serializer.SerializableType;

public class DoubleValueWrapper extends AbstractValueWrapper<Double> {

    public DoubleValueWrapper(Double value) {
        super(value);
    }

    @Override
    public SerializableType getType() {
        return SerializableType.INTEGER;
    }
}
