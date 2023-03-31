package fr.natsu.rmq.serializer.buffer;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.natsu.rmq.RabbitMQRegistration;
import fr.natsu.rmq.serializer.entity.ValueWrapper;
import fr.natsu.rmq.serializer.factory.SerializableFactory;
import fr.natsu.rmq.serializer.ObjectMarshal;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class MapBuffer implements ValueBuffer<Map<String, Object>> {


    @Override
    public byte[] transform(String id, ValueWrapper<Map<String, Object>> valueWrapper) {
        try {
            // We transform our map into a json string, then into a byte array
            return getBytesAndId(OBJECT_MAPPER.writeValueAsString(valueWrapper.getValue()).getBytes(StandardCharsets.UTF_8), valueWrapper);
        } catch(JsonProcessingException exception) {
            RabbitMQRegistration.getLogger().error("Une erreur est survenue lors de l'écriture de la valeur : " + exception.getMessage());
            return new byte[0];
        }
    }

    @Override
    public Map<String, Object> read(SerializableFactory serializableFactory, ObjectMarshal objectMarshal, byte[] bytes) {
        try {
            // We get the deserialized map from json
            return OBJECT_MAPPER.readValue(new String(bytes, StandardCharsets.UTF_8), Map.class);
        } catch(JsonProcessingException exception) {
            RabbitMQRegistration.getLogger().error("Une erreur est survenue lors de la lecture de la valeur : " + exception.getMessage());
            return null;
        }
    }
}
