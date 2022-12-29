package fr.playfull.rmq.serializer.marshal;

import fr.playfull.rmq.serializer.ObjectMarshal;
import fr.playfull.rmq.serializer.entity.DoubleValueWrapper;
import fr.playfull.rmq.serializer.entity.ValueWrapper;

import java.math.BigDecimal;

public class BigDecimalMarshal implements ValueWrapperMarshal<Double> {

    @Override
    public ValueWrapper<?> serialize(ObjectMarshal objectMarshal, Object value) {
        return new DoubleValueWrapper(((BigDecimal)value).doubleValue());
    }

    @Override
    public Double deserialize(ObjectMarshal objectMarshal, ValueWrapper<?> value) {
        return (double)value.getValue();
    }
}
