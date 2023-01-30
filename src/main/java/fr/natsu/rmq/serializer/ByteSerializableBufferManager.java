package fr.natsu.rmq.serializer;

import fr.natsu.rmq.serializer.factory.SerializableFactory;

public interface ByteSerializableBufferManager {


    /**
     * Allows the serialization of any object into bytes.
     * @param object the object that must be deserialized
     * @return an array of bytes
     */
    byte[] serialize(Object object);

    /**
     * Allows the serialization of an object into bytes.
     * @param object the object that will be deserialized
     * @param clazz the specific class
     * @return an array of bytes
     */
    byte[] serialize(Object object, Class<?> clazz);

    /**
     * Allows the deserialization of bytes into an object.
     * @param bytes the array of bytes
     * @param <T> the type
     * @return {@link T}
     */
    <T> T deserialize(byte[] bytes);

    /**
     * Allows anyone to add a factory.
     * @param tClass the RMQSerializable class
     * @param serializableFactory the factory
     */
    <T extends RMQSerializable> void addFactory(Class<T> tClass, SerializableFactory<T> serializableFactory);

    /**
     * Allows anyone to get the marshal class.
     * @return an instance {@link ObjectMarshal}
     */
    ObjectMarshal getMarshal();
}
