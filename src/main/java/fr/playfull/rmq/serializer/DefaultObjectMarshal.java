package fr.playfull.rmq.serializer;

import fr.playfull.rmq.serializer.marshal.*;
import fr.playfull.rmq.serializer.entity.ValueWrapper;
import fr.playfull.rmq.serializer.marshal.primitive.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultObjectMarshal implements ObjectMarshal {

    private final Map<Class<?>, ValueWrapperMarshal<?>> marshalMap = new HashMap<>();
    public DefaultObjectMarshal() {
        this.marshalMap.put(Integer.class, new IntegerValueMarshal());
        this.marshalMap.put(Map.class, new MapValueMarshal());
        this.marshalMap.put(String.class, new StringValueMarshal());
        this.marshalMap.put(RMQSerializable.class, new GenericValueMarshal());
        this.marshalMap.put(Double.class, new DoubleValueMarshal());
        this.marshalMap.put(Float.class, new FloatValueMarshal());
        this.marshalMap.put(Long.class, new LongValueMarshal());
        this.marshalMap.put(List.class, new ListValueMarshal());
        this.marshalMap.put(Boolean.class, new BooleanValueMarshal());
    }

    @Override
    public ValueWrapper<?> serialize(Object object) {
        return serialize(object, object.getClass());
    }

    @Override
    public ValueWrapper<?> serialize(Object object, Class<?> type) {
        return marshalMap.get(type).serialize(this, object);
    }

    @Override
    public <T> T deserialize(ValueWrapper<T> valueWrapper, Class<T> type) {
        return (T) marshalMap.get(type).deserialize(this, valueWrapper);
    }

    @Override
    public <T> void addMarshal(Class<T> aClass, ValueWrapperMarshal<T> marshal) {
        this.marshalMap.putIfAbsent(aClass, marshal);
    }

    @Override
    public ValueWrapperMarshal<?> getMarshal(Class<?> type) {
        return null;
    }
}
