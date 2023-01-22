package fr.erarealms.rmq.serializer.marshal.primitive;

import fr.erarealms.rmq.serializer.ObjectMarshal;
import fr.erarealms.rmq.serializer.entity.BooleanValueWrapper;
import fr.erarealms.rmq.serializer.entity.ValueWrapper;
import fr.erarealms.rmq.serializer.marshal.ValueWrapperMarshal;

public class BooleanValueMarshal implements ValueWrapperMarshal<Boolean> {

    @Override
    public ValueWrapper<?> serialize(ObjectMarshal objectMarshal, Object value) {
        return new BooleanValueWrapper((boolean)value);
    }

    @Override
    public Boolean deserialize(ObjectMarshal objectMarshal, ValueWrapper<?> value) {
        return (boolean)value.getValue();
    }
}
