package fr.playfull.rmq.protocol.server;

import com.rabbitmq.client.Channel;
import fr.playfull.rmq.RabbitMQAPI;
import fr.playfull.rmq.connect.Credentials;
import fr.playfull.rmq.marshal.RMQMarshal;
import fr.playfull.rmq.protocol.Protocol;

import java.io.IOException;

public abstract class ServerProtocol extends Protocol {

    private Channel loadedChannel;

    public abstract void listen(String queue, RMQMarshal marshal);

    public Channel getChannel() {
        return this.loadedChannel;
    }

    @Override
    public void connect(Credentials credentials) {
        super.connect(credentials);

        try {
            this.loadedChannel = getConnection().createChannel();
        } catch (IOException exception) {
            RabbitMQAPI.getLogger().severe(exception.getMessage());
        }
    }
}
