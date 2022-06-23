package fr.playfull.rmq.serializer.buffer;

import com.google.gson.Gson;
import fr.playfull.rmq.serializer.ObjectMarshal;
import fr.playfull.rmq.serializer.entity.ListValueWrapper;
import fr.playfull.rmq.serializer.entity.ValueWrapper;
import fr.playfull.rmq.serializer.factory.SerializableFactory;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ListBuffer implements ValueBuffer<List<Object>> {

    private final String separator = "%-%";

    @Override
    public byte[] transform(int id, ValueWrapper<List<Object>> valueWrapper) {
        StringBuilder stringBuilder = new StringBuilder();

        for(Object object : valueWrapper.getValue()) {
            stringBuilder.append(GSON.toJson(object)).append(separator);
        }

        return getBytesAndId(stringBuilder.toString().getBytes(StandardCharsets.UTF_8), valueWrapper);
    }

    @Override
    public List<Object> read(SerializableFactory serializableFactory, ObjectMarshal objectMarshal, byte[] bytes) {
        String strArray = new String(getWithoutId(bytes), StandardCharsets.UTF_8);
        List<Object> list = new ArrayList<>();

        for(String element : strArray.split(this.separator)) {
            list.add(GSON.fromJson(element, Object.class));
        }

        return list;
    }
}
