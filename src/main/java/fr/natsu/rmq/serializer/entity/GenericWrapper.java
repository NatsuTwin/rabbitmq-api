package fr.natsu.rmq.serializer.entity;

import fr.natsu.rmq.serializer.RMQSerializable;
import fr.natsu.rmq.serializer.SerializableType;

public class GenericWrapper extends AbstractValueWrapper<RMQSerializable> {

    public GenericWrapper(RMQSerializable value) {
        super(value);
    }

    @Override
    public SerializableType getType() {
        return SerializableType.CUSTOM;
    }

}
