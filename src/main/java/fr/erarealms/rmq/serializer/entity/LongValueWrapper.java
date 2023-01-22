package fr.erarealms.rmq.serializer.entity;

import fr.erarealms.rmq.serializer.SerializableType;

public class LongValueWrapper extends AbstractValueWrapper<Long> {

    public LongValueWrapper(Long value) {
        super(value);
    }

    @Override
    public SerializableType getType() {
        return SerializableType.LONG;
    }
}
