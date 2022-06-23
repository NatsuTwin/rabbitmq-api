package fr.playfull.rmq.serializer.marshal.primitive;

import fr.playfull.rmq.serializer.ObjectMarshal;
import fr.playfull.rmq.serializer.entity.BooleanValueWrapper;
import fr.playfull.rmq.serializer.entity.ValueWrapper;
import fr.playfull.rmq.serializer.marshal.ValueWrapperMarshal;

public class BooleanValueMarshal implements ValueWrapperMarshal<Boolean> {

    @Override
    public ValueWrapper<?> serialize(ObjectMarshal objectMarshal, Object value) {
        return new BooleanValueWrapper((boolean)value);
    }

    @Override
    public Boolean deserialize(ObjectMarshal objectMarshal, ValueWrapper<?> value) {
        return (boolean)value.getValue();
    }
}
