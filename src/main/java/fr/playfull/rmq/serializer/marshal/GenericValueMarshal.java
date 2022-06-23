package fr.playfull.rmq.serializer.marshal;

import fr.playfull.rmq.serializer.ObjectMarshal;
import fr.playfull.rmq.serializer.RMQSerializable;
import fr.playfull.rmq.serializer.entity.GenericWrapper;
import fr.playfull.rmq.serializer.entity.ValueWrapper;

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
