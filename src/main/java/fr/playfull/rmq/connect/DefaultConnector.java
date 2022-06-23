package fr.playfull.rmq.connect;

import fr.playfull.rmq.protocol.Protocol;
import fr.playfull.rmq.protocol.server.Server;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public final class DefaultConnector implements Connector {

    @Override
    public void connect(Protocol protocol, Credentials credentials) {
        protocol.connect(credentials);
    }

    @Override
    public void disconnect(Server protocol) {
        try {
            if(protocol.getChannel().isOpen()) {
                protocol.getChannel().close();
            }
        } catch (IOException | TimeoutException exception) {
            exception.printStackTrace();
        }
    }
}
