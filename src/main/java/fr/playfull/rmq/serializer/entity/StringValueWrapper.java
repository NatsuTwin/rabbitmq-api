package fr.playfull.rmq.serializer.entity;

import fr.playfull.rmq.serializer.SerializableType;

public class StringValueWrapper extends AbstractValueWrapper<String> {

    public StringValueWrapper(String value) {
        super(value);
    }

    @Override
    public SerializableType getType() {
        return SerializableType.STRING;
    }
}
