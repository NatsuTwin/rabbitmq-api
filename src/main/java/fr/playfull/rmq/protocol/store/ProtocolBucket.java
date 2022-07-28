package fr.playfull.rmq.protocol.store;

import fr.playfull.rmq.RabbitMQRegistration;
import fr.playfull.rmq.pair.ProtocolClientServerPair;
import fr.playfull.rmq.protocol.ProtocolType;
import fr.playfull.rmq.protocol.client.RPCClient;
import fr.playfull.rmq.protocol.client.TCPClient;
import fr.playfull.rmq.protocol.server.RPCServer;
import fr.playfull.rmq.protocol.server.TCPServer;

import java.util.HashMap;
import java.util.Map;

public class ProtocolBucket {

    private final Map<ProtocolType, ProtocolClientServerPair> protocolsMap = new HashMap<>();

    public ProtocolBucket(RabbitMQRegistration mediator) {
        this.protocolsMap.put(ProtocolType.RPC, new ProtocolClientServerPair(new RPCClient(mediator), new RPCServer(mediator)));
        this.protocolsMap.put(ProtocolType.TCP, new ProtocolClientServerPair(new TCPClient(mediator), new TCPServer(mediator)));
    }

    public ProtocolClientServerPair getClientServerPairOf(ProtocolType protocolType) {
        if(this.protocolsMap.containsKey(protocolType))
            return this.protocolsMap.get(protocolType);
        throw new NullPointerException("None client/server pair found for this type : " + protocolType.name());
    }

    public Map<ProtocolType, ProtocolClientServerPair> getProtocols() {
        return this.protocolsMap;
    }

}
