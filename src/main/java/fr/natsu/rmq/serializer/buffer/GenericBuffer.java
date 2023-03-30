package fr.natsu.rmq.serializer.buffer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import fr.natsu.rmq.RabbitMQRegistration;
import fr.natsu.rmq.serializer.entity.ValueWrapper;
import fr.natsu.rmq.serializer.factory.SerializableFactory;
import fr.natsu.rmq.serializer.ObjectMarshal;
import fr.natsu.rmq.serializer.RMQSerializable;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class GenericBuffer implements ValueBuffer<RMQSerializable> {

    @Override
    public byte[] transform(String id, ValueWrapper<RMQSerializable> valueWrapper) {
        try {
            return getBytesAndId(OBJECT_MAPPER.writeValueAsString(valueWrapper.getValue()).getBytes(StandardCharsets.UTF_8), id);
        } catch (JsonProcessingException exception) {
            RabbitMQRegistration.getLogger().error("Une erreur est survenue lors de l'écriture de la valeur : " + exception.getMessage());
            return new byte[0];
        }
    }

    @Override
    public RMQSerializable read(SerializableFactory serializableFactory, ObjectMarshal objectMarshal, byte[] bytes) {
        try {
            Map<String, Object> deserializedMap = OBJECT_MAPPER.readValue(new String(bytes), Map.class);
            return serializableFactory.create(deserializedMap);
        } catch (JsonProcessingException e) {
            RabbitMQRegistration.getLogger().error("Une erreur est survenue lors de la lecture de la valeur : " + e.getMessage());
            return null;
        }
    }
}
