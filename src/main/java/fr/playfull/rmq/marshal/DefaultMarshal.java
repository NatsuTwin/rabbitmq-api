package fr.playfull.rmq.marshal;

import java.io.*;

public class DefaultMarshal implements RMQMarshal<Object> {

    @Override
    public byte[] serialize(Object object) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try(ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            outputStream.writeObject(object);
        }

        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
             ObjectInputStream in = new ObjectInputStream(byteArrayInputStream)) {
            return in.readObject();
        }
    }
}
