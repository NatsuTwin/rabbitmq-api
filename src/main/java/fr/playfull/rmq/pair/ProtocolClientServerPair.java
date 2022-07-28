package fr.playfull.rmq.pair;

import fr.playfull.rmq.protocol.client.Client;
import fr.playfull.rmq.protocol.server.Server;

public class ProtocolClientServerPair extends KeyValuePair<Client, Server> {

    public ProtocolClientServerPair(Client client, Server server) {
        super(client, server);
    }

    public Client client() {
        return super.getKey();
    }

    public Server server() {
        return super.getValue();
    }
}
