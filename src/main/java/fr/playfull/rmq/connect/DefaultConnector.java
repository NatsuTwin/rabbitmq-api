package fr.playfull.rmq.connect;

import fr.playfull.rmq.protocol.ProtocolBucket;
import fr.playfull.rmq.protocol.ProtocolClientServerPair;
import fr.playfull.rmq.protocol.ProtocolType;
import fr.playfull.rmq.protocol.Side;
import fr.playfull.rmq.protocol.client.Client;
import fr.playfull.rmq.protocol.server.Server;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
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
            Client client = entry.getValue().client();

            try {
                if(client.getConnection().isOpen())
                    client.getConnection().close();
                if(client.getChannel().isOpen())
                    client.getChannel().close();

                if(server.getConnection().isOpen())
                    server.getConnection().close();
                if(server.getChannel().isOpen())
                    server.getChannel().close();
            } catch(IOException | TimeoutException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    @Override
    public void overrideThreadPool(ProtocolType type, Side side, ExecutorService newThreadPool) {
        ProtocolClientServerPair pair = protocolBucket.getClientServerPairOf(type);
        if(side == Side.SERVER)
            pair.server().setThreadPool(newThreadPool);
        else
            pair.client().setThreadPool(newThreadPool);
    }
}
