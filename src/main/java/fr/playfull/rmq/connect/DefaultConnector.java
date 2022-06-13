package fr.playfull.rmq.connect;

import fr.playfull.rmq.protocol.Protocol;
import fr.playfull.rmq.protocol.server.ServerProtocol;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public final class DefaultConnector implements Connector {

    @Override
    public void connect(Protocol protocol, Credentials credentials) {
        protocol.connect(credentials);
    }

    @Override
    public void disconnect(ServerProtocol protocol) {
        try {
            protocol.getChannel().close();
        } catch (IOException | TimeoutException exception) {
            exception.printStackTrace();
        }
    }
}
