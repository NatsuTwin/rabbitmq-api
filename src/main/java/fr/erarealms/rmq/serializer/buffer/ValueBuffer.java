package fr.erarealms.rmq.serializer.buffer;

import com.google.gson.Gson;
import fr.erarealms.rmq.serializer.entity.ValueWrapper;
import fr.erarealms.rmq.serializer.factory.SerializableFactory;
import fr.erarealms.rmq.serializer.ByteSerializableBufferManager;
import fr.erarealms.rmq.serializer.ObjectMarshal;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public interface ValueBuffer<T> {

    Gson GSON = new Gson();

    /**
     * This method allows the {@link ByteSerializableBufferManager} to know how to transform a specific type into bytes.
     * @param id the optional id that will be added into the bytes array.
     * @param valueWrapper - The {@link ValueWrapper <T>} that contains the data.
     * @return an array of bytes
     */
    byte[] transform(String id, ValueWrapper<T> valueWrapper);

    /**
     * This method allows the {@link ByteSerializableBufferManager} to read a specific object type from an array of bytes
     * @param serializableFactory the optional factory that will be used.
     * @param objectMarshal the marshal that will optionally be used.
     * @param bytes the array of bytes
     * @return a ValueWrapper
     */
    T read(SerializableFactory serializableFactory, ObjectMarshal objectMarshal, byte[] bytes);

    default byte[] getBytesAndId(byte[] bytes, String id) {
        // add a coma to separate the id from the data.
        id = id.concat(",");
        byte[] joinedArray = Arrays.copyOf(id.getBytes(StandardCharsets.UTF_8), bytes.length + id.length());
        System.arraycopy(bytes, 0, joinedArray, id.length(), bytes.length);
        return joinedArray;
    }

    default byte[] getBytesAndId(byte[] bytes, ValueWrapper<T> valueWrapper) {
        return getBytesAndId(bytes, valueWrapper.getType().name());
    }

    default int getId(byte[] bytes) {
        return bytes[0];
    }

    default byte[] getWithoutId(byte[] bytes) {
        return Arrays.copyOfRange(bytes, 1, bytes.length);
    }
}
