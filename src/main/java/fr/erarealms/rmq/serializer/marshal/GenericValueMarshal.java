package fr.erarealms.rmq.serializer.marshal;

import fr.erarealms.rmq.serializer.ObjectMarshal;
import fr.erarealms.rmq.serializer.RMQSerializable;
import fr.erarealms.rmq.serializer.entity.GenericWrapper;
import fr.erarealms.rmq.serializer.entity.ValueWrapper;

public class GenericValueMarshal implements ValueWrapperMarshal<RMQSerializable> {

    @Override
    public ValueWrapper<?> serialize(ObjectMarshal objectMarshal, Object value) {
        return new GenericWrapper((RMQSerializable) value);
    }

    @Override
    public RMQSerializable deserialize(ObjectMarshal objectMarshal, ValueWrapper<?> value) {
        return (RMQSerializable) value.getValue();
    }
}
