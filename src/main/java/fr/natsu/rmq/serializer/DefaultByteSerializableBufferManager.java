package fr.natsu.rmq.serializer;

import fr.natsu.rmq.serializer.buffer.*;
import fr.natsu.rmq.serializer.buffer.primitive.StringBuffer;
import fr.natsu.rmq.serializer.buffer.primitive.*;
import fr.natsu.rmq.serializer.entity.ValueWrapper;
import fr.natsu.rmq.serializer.factory.SerializableFactory;
import fr.natsu.rmq.serializer.marshal.GenericValueMarshal;
import fr.natsu.rmq.serializer.marshal.ValueWrapperMarshal;
import org.slf4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class DefaultByteSerializableBufferManager implements ByteSerializableBufferManager {

    private final Logger logger;
    private final Map<Class<?>, ValueBuffer<?>> bufferMap = new HashMap<>();
    private final Map<String, ValueBuffer<?>> byIdMap = new HashMap<>();

    private final RMQSerializableFactoryBucket factoryBucket = new RMQSerializableFactoryBucket();
    // We allocate an unique instance to avoid multiple instances pending.
    private final GenericBuffer genericBuffer = new GenericBuffer();
    private final ObjectMarshal objectMarshal;

    public DefaultByteSerializableBufferManager(Logger logger) {
        this.logger = logger;
        this.objectMarshal = new DefaultObjectMarshal();
        // We load by types
        bufferMap.put(Map.class, new MapBuffer());
        bufferMap.put(Long.class, new LongBuffer());
        bufferMap.put(UUID.class, new UUIDBuffer());
        bufferMap.put(HashMap.class, new MapBuffer());
        bufferMap.put(Float.class, new FloatBuffer());
        bufferMap.put(Short.class, new ShortBuffer());
        bufferMap.put(String.class, new StringBuffer());
        bufferMap.put(Double.class, new DoubleBuffer());
        bufferMap.put(ArrayList.class, new ListBuffer());
        bufferMap.put(Integer.class, new IntegerBuffer());
        bufferMap.put(Boolean.class, new BooleanBuffer());
        bufferMap.put(RMQSerializable.class, genericBuffer);
        bufferMap.put(LinkedHashMap.class, new MapBuffer());

        // We load by IDS (useful for deserialization)
        byIdMap.put(SerializableType.MAP.getIdentifier(), new MapBuffer());
        byIdMap.put(SerializableType.CUSTOM.getIdentifier(), genericBuffer);
        byIdMap.put(SerializableType.LONG.getIdentifier(), new LongBuffer());
        byIdMap.put(SerializableType.LIST.getIdentifier(), new ListBuffer());
        byIdMap.put(SerializableType.UUID.getIdentifier(), new UUIDBuffer());
        byIdMap.put(SerializableType.FLOAT.getIdentifier(), new FloatBuffer());
        byIdMap.put(SerializableType.SHORT.getIdentifier(), new ShortBuffer());
        byIdMap.put(SerializableType.STRING.getIdentifier(), new StringBuffer());
        byIdMap.put(SerializableType.DOUBLE.getIdentifier(), new DoubleBuffer());
        byIdMap.put(SerializableType.INTEGER.getIdentifier(), new IntegerBuffer());
        byIdMap.put(SerializableType.BOOLEAN.getIdentifier(), new BooleanBuffer());
    }

    @Override
    public byte[] serialize(Object object) {
        return this.serialize(object, object.getClass());
    }

    @Override
    public byte[] serialize(Object object, Class<?> clazz) {
        ValueWrapper valueWrapper = objectMarshal.serialize(object, clazz);
        String id = valueWrapper.getType().getIdentifier();

        if(RMQSerializable.class.isAssignableFrom(clazz))
            id = factoryBucket.retrieveId((Class<? extends RMQSerializable>) clazz);

        return  bufferMap.get(clazz).transform(id, valueWrapper);
    }

    @Override
    public <T> T deserialize(byte[] bytes) {
        // retrieve the raw data.
        String[] data = new String(bytes, StandardCharsets.UTF_8).split(",");
        // retrieve the id.
        String id = data[0];
        // check if the id doesn't exist.
        if(byIdMap.get(id) == null) {
            // notify the console that a rabbit-mq-api that is on version 4 or older tried to communicate.
            logger.warn("An unknown consumer tried to communicate with this instance of rabbit-mq-api.");
            logger.warn("Please use a version of rabbit-mq-api that is up-to-date.");
            return null;
        }
        // retrieve the object.
        byte[] object = Arrays.copyOfRange(bytes, id.length() + 1, bytes.length);
        return (T) this.byIdMap.get(id).read(factoryBucket.getFactory(id), objectMarshal,
                object);
    }

    @Override
    public <T extends RMQSerializable> void addFactory(Class<T> tClass, SerializableFactory<T> serializableFactory) {
        // We add the factory into the bucket
        factoryBucket.addFactory(tClass, serializableFactory);
        // We get the id
        factoryBucket.addRMQSerializableItem(tClass);
        // We add a generic buffer for both id and class to avoid NPE's
        this.byIdMap.put(tClass.getSimpleName(), genericBuffer);
        this.bufferMap.put(tClass, genericBuffer);
        // We add a generic marshal
        objectMarshal.addMarshal(tClass, (ValueWrapperMarshal<T>) new GenericValueMarshal());
    }

    @Override
    public ObjectMarshal getMarshal() {
        return objectMarshal;
    }
}
