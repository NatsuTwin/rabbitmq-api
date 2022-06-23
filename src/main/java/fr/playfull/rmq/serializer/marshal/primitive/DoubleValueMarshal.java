package fr.playfull.rmq.serializer.marshal.primitive;

import fr.playfull.rmq.serializer.ObjectMarshal;
import fr.playfull.rmq.serializer.entity.DoubleValueWrapper;
import fr.playfull.rmq.serializer.entity.ValueWrapper;
import fr.playfull.rmq.serializer.marshal.ValueWrapperMarshal;

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
