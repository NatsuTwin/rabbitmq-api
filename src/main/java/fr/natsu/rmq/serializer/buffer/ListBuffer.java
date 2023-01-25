package fr.natsu.rmq.serializer.buffer;

import fr.natsu.rmq.serializer.entity.ValueWrapper;
import fr.natsu.rmq.serializer.factory.SerializableFactory;
import fr.natsu.rmq.serializer.ObjectMarshal;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ListBuffer implements ValueBuffer<List<Object>> {

    private final String separator = "%-%";

    @Override
    public byte[] transform(String id, ValueWrapper<List<Object>> valueWrapper) {
        StringBuilder stringBuilder = new StringBuilder();

        for(Object object : valueWrapper.getValue()) {
            stringBuilder.append(GSON.toJson(object)).append(separator);
        }

        return getBytesAndId(stringBuilder.toString().getBytes(StandardCharsets.UTF_8), valueWrapper);
    }

    @Override
    public List<Object> read(SerializableFactory serializableFactory, ObjectMarshal objectMarshal, byte[] bytes) {
        String strArray = new String(bytes, StandardCharsets.UTF_8);
        List<Object> list = new ArrayList<>();

        for(String element : strArray.split(this.separator)) {
            list.add(GSON.fromJson(element, Object.class));
        }

        return list;
    }
}
