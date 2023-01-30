package fr.natsu.rmq.serializer.marshal;

import fr.natsu.rmq.serializer.ObjectMarshal;
import fr.natsu.rmq.serializer.RMQSerializable;
import fr.natsu.rmq.serializer.entity.GenericWrapper;
import fr.natsu.rmq.serializer.entity.ValueWrapper;

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
