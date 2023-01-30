package fr.natsu.rmq.serializer.buffer.primitive;

import fr.natsu.rmq.serializer.entity.ValueWrapper;
import fr.natsu.rmq.serializer.factory.SerializableFactory;
import fr.natsu.rmq.serializer.ObjectMarshal;
import fr.natsu.rmq.serializer.buffer.ValueBuffer;

import java.nio.ByteBuffer;

public class ShortBuffer implements ValueBuffer<Short> {

    @Override
    public byte[] transform(String id, ValueWrapper<Short> valueWrapper) {
        return getBytesAndId(ByteBuffer.allocate(2).putShort(valueWrapper.getValue()).array(), valueWrapper);
    }

    @Override
    public Short read(SerializableFactory serializableFactory, ObjectMarshal objectMarshal, byte[] bytes) {
        return ByteBuffer.wrap(bytes).getShort();
    }
}
