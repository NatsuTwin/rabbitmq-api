package fr.playfull.rmq.serializer.marshal;


import fr.playfull.rmq.serializer.ObjectMarshal;
import fr.playfull.rmq.serializer.entity.ValueWrapper;

public interface ValueWrapperMarshal<T> {

    /**
     * Permits the serialization of any object into a {@link ValueWrapper}.
     * @param objectMarshal the object marshal that will potentially be needed in specific cases.
     * @param value the value that needs to be wrapped.
     * @return an instance of {@link ValueWrapper}
     */
    ValueWrapper<?> serialize(ObjectMarshal objectMarshal, Object value);

    /** Permits the deserialization of any {@link ValueWrapper} into an object.
     * @param objectMarshal the object marshal that will optionally be needed.
     * @param value the value.
     * @return {@link T}
     */
    T deserialize(ObjectMarshal objectMarshal, ValueWrapper<?> value);
}
