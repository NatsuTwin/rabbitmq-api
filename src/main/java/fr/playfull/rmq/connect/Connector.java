package fr.playfull.rmq.connect;

import fr.playfull.rmq.protocol.ProtocolType;
import fr.playfull.rmq.protocol.Side;

import java.util.concurrent.ExecutorService;

public interface Connector {

    void connectAll(Credentials credentials);

    // The ClientProtocols already have their connection closed.
    void disconnectAll();

    void overrideThreadPool(ProtocolType type, Side side, ExecutorService newThreadPool);
}
