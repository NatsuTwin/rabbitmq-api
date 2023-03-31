package fr.natsu.rmq.serializer.buffer;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.natsu.rmq.RabbitMQRegistration;
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
        try {
            StringBuilder stringBuilder = new StringBuilder();

            for(Object object : valueWrapper.getValue()) {
                stringBuilder.append(OBJECT_MAPPER.writeValueAsString(object)).append(separator);
            }

            return getBytesAndId(stringBuilder.toString().getBytes(StandardCharsets.UTF_8), valueWrapper);
        } catch(JsonProcessingException exception) {
            RabbitMQRegistration.getLogger().error("Une erreur est survenue lors de l'écriture de la valeur : " + exception.getMessage());
            return new byte[0];
        }
    }

    @Override
    public List<Object> read(SerializableFactory serializableFactory, ObjectMarshal objectMarshal, byte[] bytes) {
        try {
            String strArray = new String(bytes, StandardCharsets.UTF_8);
            List<Object> list = new ArrayList<>();

            for(String element : strArray.split(this.separator)) {
                list.add(OBJECT_MAPPER.readValue(element, Object.class));
            }

            return list;
        } catch(JsonProcessingException exception) {
            RabbitMQRegistration.getLogger().error("Une erreur est survenue lors de la lecture de la valeur : " + exception.getMessage());
            return null;
        }
    }
}
