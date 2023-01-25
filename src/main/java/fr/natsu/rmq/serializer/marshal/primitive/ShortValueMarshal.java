package fr.natsu.rmq.serializer.marshal.primitive;

import fr.natsu.rmq.serializer.ObjectMarshal;
import fr.natsu.rmq.serializer.entity.ShortValueWrapper;
import fr.natsu.rmq.serializer.entity.ValueWrapper;
import fr.natsu.rmq.serializer.marshal.ValueWrapperMarshal;

public class ShortValueMarshal implements ValueWrapperMarshal<Short> {

    @Override
    public ValueWrapper<?> serialize(ObjectMarshal objectMarshal, Object value) {
        return new ShortValueWrapper((short)value);
    }

    @Override
    public Short deserialize(ObjectMarshal objectMarshal, ValueWrapper<?> value) {
        return (short)value.getValue();
    }
}
