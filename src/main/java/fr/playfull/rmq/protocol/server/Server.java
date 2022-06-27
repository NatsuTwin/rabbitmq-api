package fr.playfull.rmq.protocol.server;

import com.rabbitmq.client.Channel;
import fr.playfull.rmq.RabbitMQAPI;
import fr.playfull.rmq.connect.Credentials;
import fr.playfull.rmq.protocol.Protocol;

import java.io.IOException;

public abstract class Server extends Protocol {

    public abstract void listen(String queue);
}
