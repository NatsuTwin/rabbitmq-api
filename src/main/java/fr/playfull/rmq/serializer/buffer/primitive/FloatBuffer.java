package fr.playfull.rmq.serializer.buffer.primitive;

import fr.playfull.rmq.serializer.ObjectMarshal;
import fr.playfull.rmq.serializer.buffer.ValueBuffer;
import fr.playfull.rmq.serializer.entity.ValueWrapper;
import fr.playfull.rmq.serializer.factory.SerializableFactory;

import java.nio.ByteBuffer;

public class FloatBuffer implements ValueBuffer<Float> {

    @Override
    public byte[] transform(int id, ValueWrapper<Float> valueWrapper) {
        return getBytesAndId(ByteBuffer.allocate(4).putFloat(valueWrapper.getValue()).array(), id);
    }

    @Override
    public Float read(SerializableFactory serializableFactory, ObjectMarshal objectMarshal, byte[] bytes) {
        return ByteBuffer.wrap(getWithoutId(bytes)).getFloat();
    }
}
