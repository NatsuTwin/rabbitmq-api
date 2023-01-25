package fr.natsu.rmq.serializer.entity;

import fr.natsu.rmq.serializer.SerializableType;

public interface ValueWrapper<T> {

    SerializableType getType();

    T getValue();

}
