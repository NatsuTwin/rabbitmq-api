package fr.playfull.rmq.serializer.factory;

import fr.playfull.rmq.serializer.RMQSerializable;

import java.util.Map;

public interface SerializableFactory<T extends RMQSerializable> {

    /**
     * Allows the creation of a {@link RMQSerializable} instance.
     * @param data the data map.
     * @return {@link T}
     */
    T create(Map<String, Object> data);
}
