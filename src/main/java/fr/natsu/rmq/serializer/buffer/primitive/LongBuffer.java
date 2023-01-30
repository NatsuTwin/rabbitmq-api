package fr.natsu.rmq.serializer.buffer.primitive;

import fr.natsu.rmq.serializer.entity.ValueWrapper;
import fr.natsu.rmq.serializer.factory.SerializableFactory;
import fr.natsu.rmq.serializer.ObjectMarshal;
import fr.natsu.rmq.serializer.buffer.ValueBuffer;

import java.nio.ByteBuffer;

public class LongBuffer implements ValueBuffer<Long> {

    @Override
    public byte[] transform(String id, ValueWrapper<Long> valueWrapper) {
        return getBytesAndId(ByteBuffer.allocate(8).putLong(valueWrapper.getValue()).array(), valueWrapper);
    }

    @Override
    public Long read(SerializableFactory serializableFactory, ObjectMarshal objectMarshal, byte[] bytes) {
        return ByteBuffer.wrap(bytes).getLong();
    }
}
