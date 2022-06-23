package fr.playfull.rmq.serializer;

import fr.playfull.rmq.serializer.entity.ValueWrapper;
import fr.playfull.rmq.serializer.marshal.ValueWrapperMarshal;

public interface ObjectMarshal {

    /**
     * Allows the serialization of an object into a {@link ValueWrapper}
     * @param object the object that will be wrapped.
     * @return a {@link ValueWrapper}
     */
    ValueWrapper<?> serialize(Object object);

    /**
     * Allows the serialization of an object into a {@link ValueWrapper}
     * @param object the object that will be wrapped.
     * @param type the type of the object.
     * @return a {@link ValueWrapper}
     */
    ValueWrapper<?> serialize(Object object, Class<?> type);

    /**
     * Allows the deserialization of an object into a {@link ValueWrapper}
     * @param valueWrapper the {@link ValueWrapper} that will be deserialized.
     * @return a {@link ValueWrapper}
     */
    <T> T deserialize(ValueWrapper<T> valueWrapper, Class<T> type);

    /**
     * Allows anyone to add a marshal.
     * @param aClass the type class.
     * @param marshal the new {@link ValueWrapperMarshal<T>} that will be added.
     */
    <T> void addMarshal(Class<T> aClass, ValueWrapperMarshal<T> marshal);

    /**
     * Getter to get a specific marshal from a type class.
     * @param type the type class.
     * @return a {@link ValueWrapperMarshal}
     */
    ValueWrapperMarshal<?> getMarshal(Class<?> type);
}
