package fr.natsu.rmq.allocate;

import fr.natsu.rmq.protocol.ProtocolType;
import fr.natsu.rmq.protocol.Side;

import java.util.concurrent.ExecutorService;

public interface ThreadPoolAllocator {

    void allocate(ProtocolType protocolType, Side side, ExecutorService threadPool);

}
