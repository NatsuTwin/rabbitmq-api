package fr.playfull.rmq.connect;

import fr.playfull.rmq.protocol.Protocol;
import fr.playfull.rmq.protocol.server.ServerProtocol;

public interface Connector {

    void connect(Protocol protocol, Credentials credentials);

    // The ClientProtocols already have their connection closed.
    void disconnect(ServerProtocol protocol);

}
