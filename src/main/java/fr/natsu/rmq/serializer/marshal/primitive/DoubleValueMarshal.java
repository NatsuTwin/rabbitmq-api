package fr.natsu.rmq.serializer.marshal.primitive;

import fr.natsu.rmq.serializer.ObjectMarshal;
import fr.natsu.rmq.serializer.entity.DoubleValueWrapper;
import fr.natsu.rmq.serializer.entity.ValueWrapper;
import fr.natsu.rmq.serializer.marshal.ValueWrapperMarshal;

public class DoubleValueMarshal implements ValueWrapperMarshal<Double> {

    @Override
    public ValueWrapper<?> serialize(ObjectMarshal objectMarshal, Object value) {
        return new DoubleValueWrapper((double)value);
    }

    @Override
    public Double deserialize(ObjectMarshal objectMarshal, ValueWrapper<?> value) {
        return (double)value.getValue();
    }
}
