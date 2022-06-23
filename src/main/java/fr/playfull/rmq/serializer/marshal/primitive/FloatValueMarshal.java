package fr.playfull.rmq.serializer.marshal.primitive;

import fr.playfull.rmq.serializer.ObjectMarshal;
import fr.playfull.rmq.serializer.entity.FloatValueWrapper;
import fr.playfull.rmq.serializer.entity.ValueWrapper;
import fr.playfull.rmq.serializer.marshal.ValueWrapperMarshal;

public class FloatValueMarshal implements ValueWrapperMarshal<Float> {

    @Override
    public ValueWrapper<?> serialize(ObjectMarshal objectMarshal, Object value) {
        return new FloatValueWrapper((float)value);
    }

    @Override
    public Float deserialize(ObjectMarshal objectMarshal, ValueWrapper<?> value) {
        return (float)value.getValue();
    }
}
