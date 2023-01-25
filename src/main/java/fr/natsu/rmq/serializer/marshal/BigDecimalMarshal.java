package fr.natsu.rmq.serializer.marshal;

import fr.natsu.rmq.serializer.ObjectMarshal;
import fr.natsu.rmq.serializer.entity.DoubleValueWrapper;
import fr.natsu.rmq.serializer.entity.ValueWrapper;

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
