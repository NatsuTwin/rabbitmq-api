package fr.playfull.rmq.serializer.buffer.primitive;

import fr.playfull.rmq.serializer.buffer.ValueBuffer;
import fr.playfull.rmq.serializer.entity.ValueWrapper;
import fr.playfull.rmq.serializer.ObjectMarshal;
import fr.playfull.rmq.serializer.factory.SerializableFactory;

import java.nio.ByteBuffer;

public class IntegerBuffer implements ValueBuffer<Integer> {

    @Override
    public byte[] transform(int id, ValueWrapper<Integer> valueWrapper) {
        return getBytesAndId(ByteBuffer.allocate(4).putInt(valueWrapper.getValue()).array(), valueWrapper);
    }

    @Override
    public Integer read(SerializableFactory serializableFactory, ObjectMarshal objectMarshal, byte[] bytes) {
        return ByteBuffer.wrap(getWithoutId(bytes)).getInt();
    }
}
