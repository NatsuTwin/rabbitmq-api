package fr.erarealms.rmq.serializer.marshal;

import fr.erarealms.rmq.serializer.ObjectMarshal;
import fr.erarealms.rmq.serializer.entity.MapValueWrapper;
import fr.erarealms.rmq.serializer.entity.ValueWrapper;

import java.util.Map;

public class MapValueMarshal implements ValueWrapperMarshal<Map<String, Object>> {

    @Override
    public ValueWrapper<?> serialize(ObjectMarshal objectMarshal, Object value) {
        return new MapValueWrapper((Map<String, Object>) value);
    }

    @Override
    public Map<String, Object> deserialize(ObjectMarshal objectMarshal, ValueWrapper<?> value) {
        return (Map<String, Object>) value.getValue();
    }
}
