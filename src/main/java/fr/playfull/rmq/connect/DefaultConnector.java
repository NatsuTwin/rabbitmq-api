package fr.playfull.rmq.connect;

import fr.playfull.rmq.protocol.ProtocolBucket;
import fr.playfull.rmq.protocol.ProtocolClientServerPair;
import fr.playfull.rmq.protocol.ProtocolType;
import fr.playfull.rmq.protocol.server.Server;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public final class DefaultConnector implements Connector {

    private final ProtocolBucket protocolBucket;
    public DefaultConnector(ProtocolBucket protocolBucket) {
        this.protocolBucket = protocolBucket;
    }

    @Override
    public void connectAll(Credentials credentials) {
        for(Map.Entry<ProtocolType, ProtocolClientServerPair> entry : protocolBucket.getProtocols().entrySet()) {
            entry.getValue().server().connect(credentials);
            entry.getValue().client().connect(credentials);
        }
    }

    @Override
    public void disconnectAll() {
        for(Map.Entry<ProtocolType, ProtocolClientServerPair> entry : protocolBucket.getProtocols().entrySet()) {
            Server server = entry.getValue().server();

            try {
                if(server.getChannel().isOpen()) {
                    server.getChannel().close();
                }
            } catch(IOException | TimeoutException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
