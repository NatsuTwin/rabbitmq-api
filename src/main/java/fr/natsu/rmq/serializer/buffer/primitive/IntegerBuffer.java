package fr.natsu.rmq.serializer.buffer.primitive;

import fr.natsu.rmq.serializer.entity.ValueWrapper;
import fr.natsu.rmq.serializer.factory.SerializableFactory;
import fr.natsu.rmq.serializer.buffer.ValueBuffer;
import fr.natsu.rmq.serializer.ObjectMarshal;

import java.nio.ByteBuffer;

public class IntegerBuffer implements ValueBuffer<Integer> {

    @Override
    public byte[] transform(String id, ValueWrapper<Integer> valueWrapper) {
        return getBytesAndId(ByteBuffer.allocate(4).putInt(valueWrapper.getValue()).array(), valueWrapper);
    }

    @Override
    public Integer read(SerializableFactory serializableFactory, ObjectMarshal objectMarshal, byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt();
    }
}
