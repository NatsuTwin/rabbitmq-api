package fr.playfull.rmq.serializer.buffer;

import com.google.gson.Gson;
import fr.playfull.rmq.serializer.entity.ValueWrapper;
import fr.playfull.rmq.serializer.ObjectMarshal;
import fr.playfull.rmq.serializer.factory.SerializableFactory;

import java.nio.charset.StandardCharsets;

import java.util.Map;

public class MapBuffer implements ValueBuffer<Map<String, Object>> {


    @Override
    public byte[] transform(int id, ValueWrapper<Map<String, Object>> valueWrapper) {
        // We transform our map into a json string, then into a byte array
        return getBytesAndId(GSON.toJson(valueWrapper.getValue()).getBytes(StandardCharsets.UTF_8), valueWrapper);
    }

    @Override
    public Map<String, Object> read(SerializableFactory serializableFactory, ObjectMarshal objectMarshal, byte[] bytes) {
        // We get the deserialized map from json
        return GSON.fromJson(new String(getWithoutId(bytes), StandardCharsets.UTF_8), Map.class);
    }
}
