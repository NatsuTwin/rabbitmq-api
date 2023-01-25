package fr.natsu.rmq.serializer.marshal.primitive;

import fr.natsu.rmq.serializer.ObjectMarshal;
import fr.natsu.rmq.serializer.entity.FloatValueWrapper;
import fr.natsu.rmq.serializer.entity.ValueWrapper;
import fr.natsu.rmq.serializer.marshal.ValueWrapperMarshal;

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
