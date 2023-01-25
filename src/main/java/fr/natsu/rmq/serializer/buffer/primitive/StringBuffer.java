package fr.natsu.rmq.serializer.buffer.primitive;

import fr.natsu.rmq.serializer.entity.ValueWrapper;
import fr.natsu.rmq.serializer.factory.SerializableFactory;
import fr.natsu.rmq.serializer.buffer.ValueBuffer;
import fr.natsu.rmq.serializer.ObjectMarshal;

import java.nio.charset.StandardCharsets;

public class StringBuffer implements ValueBuffer<String> {

    @Override
    public byte[] transform(String id, ValueWrapper<String> valueWrapper) {
        byte[] bytes = valueWrapper.getValue().getBytes(StandardCharsets.UTF_8);
        return getBytesAndId(bytes, valueWrapper);
    }

    @Override
    public String read(SerializableFactory serializableFactory, ObjectMarshal objectMarshal, byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
