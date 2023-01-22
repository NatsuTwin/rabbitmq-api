package fr.erarealms.rmq.serializer.marshal.primitive;

import fr.erarealms.rmq.serializer.ObjectMarshal;
import fr.erarealms.rmq.serializer.entity.FloatValueWrapper;
import fr.erarealms.rmq.serializer.entity.ValueWrapper;
import fr.erarealms.rmq.serializer.marshal.ValueWrapperMarshal;

public class FloatValueMarshal implements ValueWrapperMarshal<Float> {

    @Override
    public ValueWrapper<?> serialize(ObjectMarshal objectMarshal, Object value) {
        return new FloatValueWrapper((float)value);
    }

    @Override
    public Float deserialize(ObjectMarshal objectMarshal, ValueWrapper<?> value) {
        return (float)value.getValue();
    }
}
