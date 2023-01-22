package fr.erarealms.rmq.serializer.buffer;

import fr.erarealms.rmq.serializer.entity.ValueWrapper;
import fr.erarealms.rmq.serializer.factory.SerializableFactory;
import fr.erarealms.rmq.serializer.ObjectMarshal;
import fr.erarealms.rmq.serializer.RMQSerializable;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class GenericBuffer implements ValueBuffer<RMQSerializable> {

    @Override
    public byte[] transform(String id, ValueWrapper<RMQSerializable> valueWrapper) {
        return getBytesAndId(GSON.toJson(valueWrapper.getValue()).getBytes(StandardCharsets.UTF_8), id);
    }

    @Override
    public RMQSerializable read(SerializableFactory serializableFactory, ObjectMarshal objectMarshal, byte[] bytes) {
        Map<String, Object> deserializedMap = GSON.fromJson(new String(bytes), Map.class);
        return serializableFactory.create(deserializedMap);
    }
}
