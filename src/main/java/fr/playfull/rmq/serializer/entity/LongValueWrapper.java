package fr.playfull.rmq.serializer.entity;

import fr.playfull.rmq.serializer.SerializableType;

public class LongValueWrapper extends AbstractValueWrapper<Long> {

    public LongValueWrapper(Long value) {
        super(value);
    }

    @Override
    public SerializableType getType() {
        return SerializableType.LONG;
    }
}
