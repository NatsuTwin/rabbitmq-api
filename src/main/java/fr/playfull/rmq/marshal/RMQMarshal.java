package fr.playfull.rmq.marshal;

import java.io.IOException;

public interface RMQMarshal<T> {

    RMQMarshal<Object> DEFAULT_MARSHAL = new DefaultMarshal();

    byte[] serialize(T object) throws IOException;

    T deserialize(byte[] bytes) throws IOException, ClassNotFoundException;

}
