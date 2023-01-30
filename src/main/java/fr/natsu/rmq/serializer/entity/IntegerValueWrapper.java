package fr.natsu.rmq.serializer.entity;

import fr.natsu.rmq.serializer.SerializableType;

public class IntegerValueWrapper extends AbstractValueWrapper<Integer> {

    public IntegerValueWrapper(int value) {
        super(value);
    }

    @Override
    public SerializableType getType() {
        return SerializableType.INTEGER;
    }
}
