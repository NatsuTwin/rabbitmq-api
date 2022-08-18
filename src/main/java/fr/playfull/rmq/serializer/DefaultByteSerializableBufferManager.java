package fr.playfull.rmq.serializer;

import fr.playfull.rmq.serializer.buffer.*;
import fr.playfull.rmq.serializer.buffer.primitive.*;
import fr.playfull.rmq.serializer.buffer.primitive.StringBuffer;
import fr.playfull.rmq.serializer.entity.ValueWrapper;
import fr.playfull.rmq.serializer.factory.SerializableFactory;
import fr.playfull.rmq.serializer.marshal.GenericValueMarshal;
import fr.playfull.rmq.serializer.marshal.ValueWrapperMarshal;

import java.util.*;

public class DefaultByteSerializableBufferManager implements ByteSerializableBufferManager {

    private final Map<Class<?>, ValueBuffer<?>> bufferMap = new HashMap<>();
    private final Map<Integer, ValueBuffer<?>> byIdMap = new HashMap<>();

    private final RMQSerializableFactoryBucket factoryBucket = new RMQSerializableFactoryBucket();
    // We allocate an unique instance to avoid multiple instances pending.
    private final GenericBuffer genericBuffer = new GenericBuffer();
    private final ObjectMarshal objectMarshal;
    public DefaultByteSerializableBufferManager() {
        this.objectMarshal = new DefaultObjectMarshal();
        // We load by types
        bufferMap.put(String.class, new StringBuffer());
        bufferMap.put(Integer.class, new IntegerBuffer());
        bufferMap.put(Map.class, new MapBuffer());
        bufferMap.put(HashMap.class, new MapBuffer());
        bufferMap.put(LinkedHashMap.class, new MapBuffer());
        bufferMap.put(Double.class, new DoubleBuffer());
        bufferMap.put(Long.class, new LongBuffer());
        bufferMap.put(Float.class, new FloatBuffer());
        bufferMap.put(ArrayList.class, new ListBuffer());
        bufferMap.put(Boolean.class, new BooleanBuffer());
        bufferMap.put(RMQSerializable.class, genericBuffer);

        // We load by IDS (useful for deserialization)
        byIdMap.put(SerializableType.CUSTOM.getId(), genericBuffer);
        byIdMap.put(SerializableType.BOOLEAN.getId(), new BooleanBuffer());
        byIdMap.put(SerializableType.LONG.getId(), new LongBuffer());
        byIdMap.put(SerializableType.FLOAT.getId(), new FloatBuffer());
        byIdMap.put(SerializableType.MAP.getId(), new MapBuffer());
        byIdMap.put(SerializableType.INTEGER.getId(), new IntegerBuffer());
        byIdMap.put(SerializableType.STRING.getId(), new StringBuffer());
        byIdMap.put(SerializableType.DOUBLE.getId(), new DoubleBuffer());
        byIdMap.put(SerializableType.LIST.getId(), new ListBuffer());
    }

    @Override
    public byte[] serialize(Object object) {
        return this.serialize(object, object.getClass());
    }

    @Override
    public byte[] serialize(Object object, Class<?> clazz) {
        ValueWrapper valueWrapper = objectMarshal.serialize(object, clazz);
        int id = valueWrapper.getType().getId();

        if(RMQSerializable.class.isAssignableFrom(clazz)) {
            id = factoryBucket.retrieveIndex((Class<? extends RMQSerializable>) clazz);
        }
        return bufferMap.get(clazz).transform(id, valueWrapper);
    }

    @Override
    public <T> T deserialize(byte[] bytes) {
        int id = bytes[0];
        return (T) this.byIdMap.get(id).read(factoryBucket.getFactory(id), objectMarshal, bytes);
    }

    @Override
    public <T extends RMQSerializable> void addFactory(Class<T> tClass, SerializableFactory<T> serializableFactory) {
        // We add the factory into the bucket
        factoryBucket.addFactory(tClass, serializableFactory);
        // We get the id
        int index = factoryBucket.addRMQSerializableItem(tClass);
        // We add a generic buffer for both id and class to avoid NPE's
        this.byIdMap.put(index, genericBuffer);
        this.bufferMap.put(tClass, genericBuffer);
        // We add a generic marshal
        objectMarshal.addMarshal(tClass, (ValueWrapperMarshal<T>) new GenericValueMarshal());
    }

    @Override
    public ObjectMarshal getMarshal() {
        return objectMarshal;
    }
}
