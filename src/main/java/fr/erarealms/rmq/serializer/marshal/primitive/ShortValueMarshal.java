package fr.erarealms.rmq.serializer.marshal.primitive;

import fr.erarealms.rmq.serializer.ObjectMarshal;
import fr.erarealms.rmq.serializer.entity.ShortValueWrapper;
import fr.erarealms.rmq.serializer.entity.ValueWrapper;
import fr.erarealms.rmq.serializer.marshal.ValueWrapperMarshal;

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
