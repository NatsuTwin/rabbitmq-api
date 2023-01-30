package fr.natsu.rmq.serializer.entity;

import fr.natsu.rmq.serializer.SerializableType;

import java.util.Map;

public class MapValueWrapper extends AbstractValueWrapper<Map<String, Object>> {

    public MapValueWrapper(Map<String, Object> value) {
        super(value);
    }

    @Override
    public SerializableType getType() {
        return SerializableType.MAP;
    }
}
