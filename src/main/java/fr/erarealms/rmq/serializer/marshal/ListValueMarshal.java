package fr.erarealms.rmq.serializer.marshal;

import fr.erarealms.rmq.serializer.ObjectMarshal;
import fr.erarealms.rmq.serializer.entity.ListValueWrapper;
import fr.erarealms.rmq.serializer.entity.ValueWrapper;

import java.util.List;

public class ListValueMarshal implements ValueWrapperMarshal<List<Object>> {

    @Override
    public ValueWrapper<?> serialize(ObjectMarshal objectMarshal, Object value) {
        return new ListValueWrapper((List<Object>) value);
    }

    @Override
    public List<Object> deserialize(ObjectMarshal objectMarshal, ValueWrapper<?> value) {
        return (List<Object>) value.getValue();
    }
}
