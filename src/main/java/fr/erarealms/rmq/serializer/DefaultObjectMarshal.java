package fr.erarealms.rmq.serializer;

import fr.erarealms.rmq.serializer.entity.ValueWrapper;
import fr.erarealms.rmq.serializer.marshal.GenericValueMarshal;
import fr.erarealms.rmq.serializer.marshal.ListValueMarshal;
import fr.erarealms.rmq.serializer.marshal.MapValueMarshal;
import fr.erarealms.rmq.serializer.marshal.ValueWrapperMarshal;
import fr.erarealms.rmq.serializer.marshal.primitive.*;

import java.math.BigDecimal;
import java.util.*;

public class DefaultObjectMarshal implements ObjectMarshal {

    private final Map<Class<?>, ValueWrapperMarshal<?>> marshalMap = new HashMap<>();
    public DefaultObjectMarshal() {
        this.marshalMap.put(Integer.class, new IntegerValueMarshal());
        this.marshalMap.put(HashMap.class, new MapValueMarshal());
        this.marshalMap.put(LinkedHashMap.class, new MapValueMarshal());
        this.marshalMap.put(BigDecimal.class, new DoubleValueMarshal());
        this.marshalMap.put(Map.class, new MapValueMarshal());
        this.marshalMap.put(String.class, new StringValueMarshal());
        this.marshalMap.put(RMQSerializable.class, new GenericValueMarshal());
        this.marshalMap.put(Double.class, new DoubleValueMarshal());
        this.marshalMap.put(Float.class, new FloatValueMarshal());
        this.marshalMap.put(Long.class, new LongValueMarshal());
        this.marshalMap.put(ArrayList.class, new ListValueMarshal());
        this.marshalMap.put(Boolean.class, new BooleanValueMarshal());
        this.marshalMap.put(Short.class, new ShortValueMarshal());
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
