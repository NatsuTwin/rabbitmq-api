package fr.playfull.rmq.serializer.marshal.primitive;

import fr.playfull.rmq.serializer.ObjectMarshal;
import fr.playfull.rmq.serializer.entity.IntegerValueWrapper;
import fr.playfull.rmq.serializer.entity.ValueWrapper;
import fr.playfull.rmq.serializer.marshal.ValueWrapperMarshal;

public class IntegerValueMarshal implements ValueWrapperMarshal<Integer> {

    @Override
    public ValueWrapper<Integer> serialize(ObjectMarshal objectMarshal, Object value) {
        return new IntegerValueWrapper((int)value);
    }

    @Override
    public Integer deserialize(ObjectMarshal objectMarshal, ValueWrapper<?> value) {
        return (int)value.getValue();
    }
}
