package fr.playfull.rmq.serializer.buffer.primitive;

import fr.playfull.rmq.serializer.ObjectMarshal;
import fr.playfull.rmq.serializer.buffer.ValueBuffer;
import fr.playfull.rmq.serializer.entity.ValueWrapper;
import fr.playfull.rmq.serializer.factory.SerializableFactory;

import java.util.Arrays;

public class BooleanBuffer implements ValueBuffer<Boolean> {

    @Override
    public byte[] transform(String id, ValueWrapper<Boolean> valueWrapper) {
        byte boolByte = (byte) (valueWrapper.getValue() ? 1 : 0);
        return getBytesAndId(new byte[] {boolByte}, valueWrapper);
    }

    @Override
    public Boolean read(SerializableFactory serializableFactory, ObjectMarshal objectMarshal, byte[] bytes) {
        // We know that our byte list is composed of two bytes, first is id, second is our value
        return bytes[0] == 1;
    }
}
