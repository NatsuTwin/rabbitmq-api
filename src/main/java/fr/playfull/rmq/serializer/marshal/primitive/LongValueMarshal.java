package fr.playfull.rmq.serializer.marshal.primitive;

import fr.playfull.rmq.serializer.ObjectMarshal;
import fr.playfull.rmq.serializer.entity.LongValueWrapper;
import fr.playfull.rmq.serializer.entity.ValueWrapper;
import fr.playfull.rmq.serializer.marshal.ValueWrapperMarshal;

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
