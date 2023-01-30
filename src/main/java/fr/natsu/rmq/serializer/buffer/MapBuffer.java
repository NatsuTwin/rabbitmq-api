package fr.natsu.rmq.serializer.buffer;

import fr.natsu.rmq.serializer.entity.ValueWrapper;
import fr.natsu.rmq.serializer.factory.SerializableFactory;
import fr.natsu.rmq.serializer.ObjectMarshal;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class MapBuffer implements ValueBuffer<Map<String, Object>> {


    @Override
    public byte[] transform(String id, ValueWrapper<Map<String, Object>> valueWrapper) {
        // We transform our map into a json string, then into a byte array
        return getBytesAndId(GSON.toJson(valueWrapper.getValue()).getBytes(StandardCharsets.UTF_8), valueWrapper);
    }

    @Override
    public Map<String, Object> read(SerializableFactory serializableFactory, ObjectMarshal objectMarshal, byte[] bytes) {
        // We get the deserialized map from json
        return GSON.fromJson(new String(bytes, StandardCharsets.UTF_8), Map.class);
    }
}
