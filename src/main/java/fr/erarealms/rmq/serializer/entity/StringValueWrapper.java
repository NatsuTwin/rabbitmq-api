package fr.erarealms.rmq.serializer.entity;

import fr.erarealms.rmq.serializer.SerializableType;

public class StringValueWrapper extends AbstractValueWrapper<String> {

    public StringValueWrapper(String value) {
        super(value);
    }

    @Override
    public SerializableType getType() {
        return SerializableType.STRING;
    }
}
