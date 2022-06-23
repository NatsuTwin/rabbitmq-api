package fr.playfull.rmq.serializer.entity;

import fr.playfull.rmq.serializer.RMQSerializable;
import fr.playfull.rmq.serializer.SerializableType;

public class GenericWrapper extends AbstractValueWrapper<RMQSerializable> {

    public GenericWrapper(RMQSerializable value) {
        super(value);
    }

    @Override
    public SerializableType getType() {
        return SerializableType.CUSTOM;
    }

}
