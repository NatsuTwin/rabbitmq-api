package fr.playfull.rmq.serializer.entity;

import fr.playfull.rmq.serializer.SerializableType;

public interface ValueWrapper<T> {

    SerializableType getType();

    T getValue();

}
