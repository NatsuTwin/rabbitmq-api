package fr.natsu.rmq.protocol.store;

import fr.natsu.rmq.RabbitMQRegistration;
import fr.natsu.rmq.pair.ProtocolClientServerPair;
import fr.natsu.rmq.protocol.client.PubSubClient;
import fr.natsu.rmq.protocol.client.RPCClient;
import fr.natsu.rmq.protocol.client.TCPClient;
import fr.natsu.rmq.protocol.server.RPCServer;
import fr.natsu.rmq.protocol.server.TCPServer;
import fr.natsu.rmq.protocol.ProtocolType;
import fr.natsu.rmq.protocol.server.PubSubServer;

import java.util.HashMap;
import java.util.Map;

public class ProtocolBucket {

    private final Map<ProtocolType, ProtocolClientServerPair> protocolsMap = new HashMap<>();

    public ProtocolBucket(RabbitMQRegistration mediator) {
        this.protocolsMap.put(ProtocolType.RPC, new ProtocolClientServerPair(new RPCClient(mediator), new RPCServer(mediator)));
        this.protocolsMap.put(ProtocolType.TCP, new ProtocolClientServerPair(new TCPClient(mediator), new TCPServer(mediator)));
        this.protocolsMap.put(ProtocolType.PUB_SUB, new ProtocolClientServerPair(new PubSubClient(mediator), new PubSubServer(mediator)));
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
