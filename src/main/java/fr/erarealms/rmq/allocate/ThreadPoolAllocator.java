package fr.erarealms.rmq.allocate;

import fr.erarealms.rmq.protocol.ProtocolType;
import fr.erarealms.rmq.protocol.Side;

import java.util.concurrent.ExecutorService;

public interface ThreadPoolAllocator {

    void allocate(ProtocolType protocolType, Side side, ExecutorService threadPool);

}
