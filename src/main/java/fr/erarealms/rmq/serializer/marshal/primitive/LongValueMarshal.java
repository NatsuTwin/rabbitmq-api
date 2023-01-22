package fr.erarealms.rmq.serializer.marshal.primitive;

import fr.erarealms.rmq.serializer.ObjectMarshal;
import fr.erarealms.rmq.serializer.entity.LongValueWrapper;
import fr.erarealms.rmq.serializer.entity.ValueWrapper;
import fr.erarealms.rmq.serializer.marshal.ValueWrapperMarshal;

public class LongValueMarshal implements ValueWrapperMarshal<Long> {

    @Override
    public ValueWrapper<?> serialize(ObjectMarshal objectMarshal, Object value) {
        return new LongValueWrapper((long)value);
    }

    @Override
    public Long deserialize(ObjectMarshal objectMarshal, ValueWrapper<?> value) {
        return (long)value.getValue();
    }
}
