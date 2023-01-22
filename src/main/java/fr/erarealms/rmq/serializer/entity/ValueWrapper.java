package fr.erarealms.rmq.serializer.entity;

import fr.erarealms.rmq.serializer.SerializableType;

public interface ValueWrapper<T> {

    SerializableType getType();

    T getValue();

}
