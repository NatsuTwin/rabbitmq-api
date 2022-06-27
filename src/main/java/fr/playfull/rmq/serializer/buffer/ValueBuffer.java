package fr.playfull.rmq.serializer.buffer;

import com.google.gson.Gson;
import fr.playfull.rmq.serializer.ByteSerializableBufferManager;
import fr.playfull.rmq.serializer.ObjectMarshal;
import fr.playfull.rmq.serializer.entity.ValueWrapper;
import fr.playfull.rmq.serializer.factory.SerializableFactory;

import java.util.Arrays;

public interface ValueBuffer<T> {

    Gson GSON = new Gson();

    /**
     * This method allows the {@link ByteSerializableBufferManager} to know how to transform a specific type into bytes.
     * @param id the optional id that will be added into the bytes array.
     * @param valueWrapper - The {@link ValueWrapper<T>} that contains the data.
     * @return an array of bytes
     */
    byte[] transform(int id, ValueWrapper<T> valueWrapper);

    /**
     * This method allows the {@link ByteSerializableBufferManager} to read a specific object type from an array of bytes
     * @param serializableFactory the optional factory that will be used.
     * @param objectMarshal the marshal that will optionally be used.
     * @param bytes the array of bytes
     * @return a ValueWrapper
     */
    T read(SerializableFactory serializableFactory, ObjectMarshal objectMarshal, byte[] bytes);

    default byte[] getBytesAndId(byte[] bytes, int id) {
        byte[] joinedArray = Arrays.copyOf(new byte[] {(byte)id}, bytes.length + 1);
        System.arraycopy(bytes, 0, joinedArray, 1, bytes.length);
        return joinedArray;
    }

    default byte[] getBytesAndId(byte[] bytes, ValueWrapper<T> valueWrapper) {
        return getBytesAndId(bytes, valueWrapper.getType().getId());
    }

    default int getId(byte[] bytes) {
        return bytes[0];
    }

    default byte[] getWithoutId(byte[] bytes) {
        return Arrays.copyOfRange(bytes, 1, bytes.length);
    }
}
