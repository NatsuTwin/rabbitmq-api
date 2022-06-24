package fr.playfull.rmq.connect;

import fr.playfull.rmq.protocol.Protocol;
import fr.playfull.rmq.protocol.server.Server;

public interface Connector {

    void connectAll(Credentials credentials);

    // The ClientProtocols already have their connection closed.
    void disconnectAll();

}
