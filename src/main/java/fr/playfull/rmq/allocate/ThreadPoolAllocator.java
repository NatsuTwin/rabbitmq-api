package fr.playfull.rmq.allocate;

import fr.playfull.rmq.protocol.ProtocolType;
import fr.playfull.rmq.protocol.Side;

import java.util.concurrent.ExecutorService;

public interface ThreadPoolAllocator {

    void allocate(ProtocolType protocolType, Side side, ExecutorService threadPool);

}
