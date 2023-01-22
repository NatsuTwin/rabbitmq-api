package fr.erarealms.rmq.serializer;

import fr.erarealms.rmq.serializer.factory.SerializableFactory;

import java.util.HashMap;
import java.util.Map;

public class RMQSerializableFactoryBucket {

    private final HashMap<String, Class<? extends RMQSerializable>> map = new HashMap<>();
    private final HashMap<Class<? extends RMQSerializable>, SerializableFactory<?>> factoryMap = new HashMap<>();

    /**
     * This method allows the {@link ByteSerializableBufferManager} to add a RMQSerializable item and behavior.
     * @param clazz the class.
     */
    public void addRMQSerializableItem(Class<? extends RMQSerializable> clazz) {
        this.map.put(clazz.getSimpleName(), clazz);
    }

    /**
     * This method allows the {@link ByteSerializableBufferManager} to add a factory.
     * @param clazz the added RMQSerializable class.
     * @param serializableFactory the {@link SerializableFactory}.
     */
    public void addFactory(Class<? extends RMQSerializable> clazz, SerializableFactory<?> serializableFactory) {
        factoryMap.put(clazz, serializableFactory);
    }

    /**
     * Allows the {@link ByteSerializableBufferManager} to get a factory by the RMQSerializable ID.
     * @param id the id got from the bytes.
     * @return an instance of {@link SerializableFactory}
     */
    public SerializableFactory<?> getFactory(String id) {
        return factoryMap.get(map.get(id));
    }

    /**
     * Allows the {@link ByteSerializableBufferManager} to get a factory by the RMQSerializable class.
     * @param clazz the class of the RMQSerializable
     * @return an instance of {@link SerializableFactory}
     */
    public <T extends RMQSerializable> SerializableFactory<T> getFactory(Class<T> clazz) {
        return (SerializableFactory<T>) factoryMap.get(clazz);
    }

    /**
     * Allows the {@link ByteSerializableBufferManager} to retrieve class's index from its class.
     * @param clazz the class of the RMQSerializable
     * @return {@link Integer} the index
     */
    public String retrieveId(Class<? extends RMQSerializable> clazz) {
        for(Map.Entry<String, Class<? extends RMQSerializable>> entry : map.entrySet()) {
            if(entry.getValue() == clazz)
                return entry.getKey();
        }
        throw new NullPointerException("No index could be found for this class !");
    }
}
