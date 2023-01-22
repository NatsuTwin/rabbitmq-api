package fr.erarealms.rmq.serializer.marshal;

import fr.erarealms.rmq.serializer.ObjectMarshal;
import fr.erarealms.rmq.serializer.entity.DoubleValueWrapper;
import fr.erarealms.rmq.serializer.entity.ValueWrapper;

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
