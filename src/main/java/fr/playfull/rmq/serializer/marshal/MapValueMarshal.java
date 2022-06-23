package fr.playfull.rmq.serializer.marshal;

import fr.playfull.rmq.serializer.ObjectMarshal;
import fr.playfull.rmq.serializer.entity.MapValueWrapper;
import fr.playfull.rmq.serializer.entity.ValueWrapper;

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
