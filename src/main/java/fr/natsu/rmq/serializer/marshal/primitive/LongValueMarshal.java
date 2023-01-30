package fr.natsu.rmq.serializer.marshal.primitive;

import fr.natsu.rmq.serializer.ObjectMarshal;
import fr.natsu.rmq.serializer.entity.LongValueWrapper;
import fr.natsu.rmq.serializer.entity.ValueWrapper;
import fr.natsu.rmq.serializer.marshal.ValueWrapperMarshal;

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
