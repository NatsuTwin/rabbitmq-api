package fr.erarealms.rmq.serializer.buffer.primitive;

import fr.erarealms.rmq.serializer.entity.ValueWrapper;
import fr.erarealms.rmq.serializer.factory.SerializableFactory;
import fr.erarealms.rmq.serializer.ObjectMarshal;
import fr.erarealms.rmq.serializer.buffer.ValueBuffer;

import java.nio.ByteBuffer;

public class FloatBuffer implements ValueBuffer<Float> {

    @Override
    public byte[] transform(String id, ValueWrapper<Float> valueWrapper) {
        return getBytesAndId(ByteBuffer.allocate(4).putFloat(valueWrapper.getValue()).array(), id);
    }

    @Override
    public Float read(SerializableFactory serializableFactory, ObjectMarshal objectMarshal, byte[] bytes) {
        return ByteBuffer.wrap(bytes).getFloat();
    }
}
