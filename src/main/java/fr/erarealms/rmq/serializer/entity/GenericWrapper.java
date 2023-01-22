package fr.erarealms.rmq.serializer.entity;

import fr.erarealms.rmq.serializer.RMQSerializable;
import fr.erarealms.rmq.serializer.SerializableType;

public class GenericWrapper extends AbstractValueWrapper<RMQSerializable> {

    public GenericWrapper(RMQSerializable value) {
        super(value);
    }

    @Override
    public SerializableType getType() {
        return SerializableType.CUSTOM;
    }

}
