package fr.natsu.rmq.serializer.entity;

import fr.natsu.rmq.serializer.SerializableType;

public class StringValueWrapper extends AbstractValueWrapper<String> {

    public StringValueWrapper(String value) {
        super(value);
    }

    @Override
    public SerializableType getType() {
        return SerializableType.STRING;
    }
}
