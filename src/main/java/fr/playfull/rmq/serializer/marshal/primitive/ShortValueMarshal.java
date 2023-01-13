package fr.playfull.rmq.serializer.marshal.primitive;

import fr.playfull.rmq.serializer.ObjectMarshal;
import fr.playfull.rmq.serializer.entity.ShortValueWrapper;
import fr.playfull.rmq.serializer.entity.ValueWrapper;
import fr.playfull.rmq.serializer.marshal.ValueWrapperMarshal;

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
