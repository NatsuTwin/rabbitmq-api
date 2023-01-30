package fr.natsu.rmq.serializer.marshal;

import fr.natsu.rmq.serializer.ObjectMarshal;
import fr.natsu.rmq.serializer.entity.UUIDValueWrapper;
import fr.natsu.rmq.serializer.entity.ValueWrapper;

import java.util.UUID;

public class UUIDValueMarshal implements ValueWrapperMarshal<UUID> {

    @Override
    public ValueWrapper<?> serialize(ObjectMarshal objectMarshal, Object value) {
        return new UUIDValueWrapper((UUID) value);
    }

    @Override
    public UUID deserialize(ObjectMarshal objectMarshal, ValueWrapper<?> value) {
        return (UUID) value.getValue();
    }
}
