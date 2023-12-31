package fr.natsu.rmq.serializer.buffer.primitive;

import fr.natsu.rmq.serializer.entity.ValueWrapper;
import fr.natsu.rmq.serializer.factory.SerializableFactory;
import fr.natsu.rmq.serializer.ObjectMarshal;
import fr.natsu.rmq.serializer.buffer.ValueBuffer;

import java.nio.ByteBuffer;

public class DoubleBuffer implements ValueBuffer<Double> {

    @Override
    public byte[] transform(String id, ValueWrapper<Double> valueWrapper) {
        return getBytesAndId(ByteBuffer.allocate(8).putDouble(valueWrapper.getValue()).array(), valueWrapper);
    }

    @Override
    public Double read(SerializableFactory serializableFactory, ObjectMarshal objectMarshal, byte[] bytes) {
        return ByteBuffer.wrap(bytes).getDouble();
    }
}
