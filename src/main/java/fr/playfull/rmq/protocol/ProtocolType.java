package fr.playfull.rmq.protocol;

import fr.playfull.rmq.protocol.client.ClientProtocol;
import fr.playfull.rmq.protocol.client.RPCClientProtocol;
import fr.playfull.rmq.protocol.client.TCPClientProtocol;
import fr.playfull.rmq.protocol.server.RPCServerProtocol;
import fr.playfull.rmq.protocol.server.ServerProtocol;
import fr.playfull.rmq.protocol.server.TCPServerProtocol;

public enum ProtocolType {

    RPC(new RPCClientProtocol(), new RPCServerProtocol()),
    TCP(new TCPClientProtocol(), new TCPServerProtocol());

    private final ClientProtocol clientProtocol;
    private final ServerProtocol serverProtocol;
    ProtocolType(ClientProtocol clientProtocol, ServerProtocol serverProtocol) {
        this.clientProtocol = clientProtocol;
        this.serverProtocol = serverProtocol;
    }

    public ClientProtocol getClientProtocol() {
        return clientProtocol;
    }

    public ServerProtocol getServerProtocol() {
        return serverProtocol;
    }
}
