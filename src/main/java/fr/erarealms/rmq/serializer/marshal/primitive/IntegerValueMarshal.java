package fr.erarealms.rmq.serializer.marshal.primitive;

import fr.erarealms.rmq.serializer.ObjectMarshal;
import fr.erarealms.rmq.serializer.entity.IntegerValueWrapper;
import fr.erarealms.rmq.serializer.entity.ValueWrapper;
import fr.erarealms.rmq.serializer.marshal.ValueWrapperMarshal;

public class IntegerValueMarshal implements ValueWrapperMarshal<Integer> {

    @Override
    public ValueWrapper<Integer> serialize(ObjectMarshal objectMarshal, Object value) {
        return new IntegerValueWrapper((int)value);
    }

    @Override
    public Integer deserialize(ObjectMarshal objectMarshal, ValueWrapper<?> value) {
        return (int)value.getValue();
    }
}
