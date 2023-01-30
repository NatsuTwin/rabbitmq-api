package fr.natsu.rmq.serializer.marshal.primitive;

import fr.natsu.rmq.serializer.ObjectMarshal;
import fr.natsu.rmq.serializer.entity.StringValueWrapper;
import fr.natsu.rmq.serializer.entity.ValueWrapper;
import fr.natsu.rmq.serializer.marshal.ValueWrapperMarshal;

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
