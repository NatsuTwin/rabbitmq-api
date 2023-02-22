package fr.natsu.rmq.serializer.buffer;

import fr.natsu.rmq.serializer.ObjectMarshal;
import fr.natsu.rmq.serializer.entity.ValueWrapper;
import fr.natsu.rmq.serializer.factory.SerializableFactory;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class UUIDBuffer implements ValueBuffer<UUID> {

    @Override
    public byte[] transform(String id, ValueWrapper<UUID> valueWrapper) {
        byte[] bytes = valueWrapper.getValue().toString().getBytes(StandardCharsets.UTF_8);
        return getBytesAndId(bytes, valueWrapper);
    }

    @Override
    public UUID read(SerializableFactory serializableFactory, ObjectMarshal objectMarshal, byte[] bytes) {
        return UUID.fromString(new String(bytes, StandardCharsets.UTF_8));
    }
}
