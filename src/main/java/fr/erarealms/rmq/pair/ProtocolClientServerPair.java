package fr.erarealms.rmq.pair;

import fr.erarealms.rmq.protocol.server.Server;
import fr.erarealms.rmq.protocol.client.Client;

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
