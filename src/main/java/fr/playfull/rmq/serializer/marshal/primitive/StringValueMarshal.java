package fr.playfull.rmq.serializer.marshal.primitive;

import fr.playfull.rmq.serializer.ObjectMarshal;
import fr.playfull.rmq.serializer.entity.StringValueWrapper;
import fr.playfull.rmq.serializer.entity.ValueWrapper;
import fr.playfull.rmq.serializer.marshal.ValueWrapperMarshal;

public class StringValueMarshal implements ValueWrapperMarshal<String> {

    @Override
    public ValueWrapper<String> serialize(ObjectMarshal objectMarshal, Object value) {
        return new StringValueWrapper((String)value);
    }

    @Override
    public String deserialize(ObjectMarshal objectMarshal, ValueWrapper<?> value) {
        return (String)value.getValue();
    }
}
