package fr.erarealms.rmq.serializer.marshal.primitive;

import fr.erarealms.rmq.serializer.ObjectMarshal;
import fr.erarealms.rmq.serializer.entity.StringValueWrapper;
import fr.erarealms.rmq.serializer.entity.ValueWrapper;
import fr.erarealms.rmq.serializer.marshal.ValueWrapperMarshal;

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
