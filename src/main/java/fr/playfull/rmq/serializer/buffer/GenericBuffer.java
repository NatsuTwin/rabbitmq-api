package fr.playfull.rmq.serializer.buffer;

import com.google.gson.Gson;
import fr.playfull.rmq.serializer.ObjectMarshal;
import fr.playfull.rmq.serializer.RMQSerializable;
import fr.playfull.rmq.serializer.entity.ValueWrapper;
import fr.playfull.rmq.serializer.factory.SerializableFactory;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class GenericBuffer implements ValueBuffer<RMQSerializable> {


    @Override
    public byte[] transform(int id, ValueWrapper<RMQSerializable> valueWrapper) {
        return getBytesAndId(GSON.toJson(valueWrapper.getValue()).getBytes(StandardCharsets.UTF_8), id);
    }

    @Override
    public RMQSerializable read(SerializableFactory serializableFactory, ObjectMarshal objectMarshal, byte[] bytes) {
        Map<String, Object> deserializedMap = GSON.fromJson(new String(getWithoutId(bytes)), Map.class);
        return serializableFactory.create(deserializedMap);
    }
}
