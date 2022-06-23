package fr.playfull.rmq.serializer.buffer.primitive;

import fr.playfull.rmq.serializer.buffer.ValueBuffer;
import fr.playfull.rmq.serializer.entity.ValueWrapper;
import fr.playfull.rmq.serializer.ObjectMarshal;
import fr.playfull.rmq.serializer.factory.SerializableFactory;

import java.nio.charset.StandardCharsets;

public class StringBuffer implements ValueBuffer<String> {

    @Override
    public byte[] transform(int id, ValueWrapper<String> valueWrapper) {
        byte[] bytes = valueWrapper.getValue().getBytes(StandardCharsets.UTF_8);

        return getBytesAndId(bytes, valueWrapper);
    }

    @Override
    public String read(SerializableFactory serializableFactory, ObjectMarshal objectMarshal, byte[] bytes) {
        return new String(getWithoutId(bytes), StandardCharsets.UTF_8);
    }
}
