package fr.playfull.rmq.forward;

import fr.playfull.rmq.RabbitMQAPI;
import fr.playfull.rmq.connect.Credentials;
import fr.playfull.rmq.marshal.RMQMarshal;
import fr.playfull.rmq.protocol.ProtocolType;
import fr.playfull.rmq.query.Request;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Forwarder {

    public void send(ProtocolType protocolType, Request<?> request) {
        protocolType.getClientProtocol().send(request);
    }

    public void listen(ProtocolType protocolType, String queueName, RMQMarshal marshal) {
        protocolType.getServerProtocol().listen(queueName, marshal);
    }

    public void connectAll(Credentials credentials) {
        for(ProtocolType protocolType : ProtocolType.values()) {
            protocolType.getClientProtocol().connect(credentials);
            protocolType.getServerProtocol().connect(credentials);
        }
    }

    public void closeAll() {
        for(ProtocolType protocolType : ProtocolType.values()) {
            try {
                protocolType.getServerProtocol().getChannel().close();
            } catch (IOException | TimeoutException exception) {
                RabbitMQAPI.getLogger().severe("There was an error while trying to close server channels : " + exception.getMessage());
            }
        }
    }

}
