package fr.natsu.rmq.serializer.entity;

import fr.natsu.rmq.serializer.SerializableType;

import java.util.UUID;

public class UUIDValueWrapper extends AbstractValueWrapper<UUID> {

    public UUIDValueWrapper(UUID value) {
        super(value);
    }

    @Override
    public SerializableType getType() {
        return SerializableType.UUID;
    }
}
