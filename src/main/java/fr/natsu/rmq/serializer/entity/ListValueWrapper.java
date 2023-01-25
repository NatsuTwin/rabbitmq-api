package fr.natsu.rmq.serializer.entity;

import fr.natsu.rmq.serializer.SerializableType;

import java.util.List;

public class ListValueWrapper extends AbstractValueWrapper<List<Object>> {

    public ListValueWrapper(List<Object> value) {
        super(value);
    }

    @Override
    public SerializableType getType() {
        return SerializableType.LIST;
    }
}
