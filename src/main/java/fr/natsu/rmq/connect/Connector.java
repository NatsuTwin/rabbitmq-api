package fr.natsu.rmq.connect;

public interface Connector {

    void connectAll(Credentials credentials);

    // The ClientProtocols already have their connection closed.
    void disconnectAll();
}
