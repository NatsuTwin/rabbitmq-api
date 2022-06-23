package fr.playfull.rmq.serializer;

import fr.playfull.rmq.serializer.factory.SerializableFactory;

/**
 * This class is a simple empty interface to allow any object to be serialized and transferred through RabbitMQ.
 * It must have an attached {@link SerializableFactory<RMQSerializable>}.
 */
public interface RMQSerializable {
}
