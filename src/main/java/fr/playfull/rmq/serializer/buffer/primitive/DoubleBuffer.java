package fr.playfull.rmq.serializer.buffer.primitive;

import fr.playfull.rmq.serializer.ObjectMarshal;
import fr.playfull.rmq.serializer.buffer.ValueBuffer;
import fr.playfull.rmq.serializer.entity.ValueWrapper;
import fr.playfull.rmq.serializer.factory.SerializableFactory;

import java.nio.ByteBuffer;

public class DoubleBuffer implements ValueBuffer<Double> {

    @Override
    public byte[] transform(int id, ValueWrapper<Double> valueWrapper) {
        return getBytesAndId(ByteBuffer.allocate(4).putDouble(valueWrapper.getValue()).array(), valueWrapper);
    }

    @Override
    public Double read(SerializableFactory serializableFactory, ObjectMarshal objectMarshal, byte[] bytes) {
        return ByteBuffer.wrap(getWithoutId(bytes)).getDouble();
    }
}
