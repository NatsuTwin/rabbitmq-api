package fr.playfull.rmq.allocate;

import fr.playfull.rmq.pair.ProtocolClientServerPair;
import fr.playfull.rmq.protocol.*;
import fr.playfull.rmq.protocol.store.ProtocolBucket;

import java.util.concurrent.ExecutorService;

public class DefaultThreadPoolAllocator implements ThreadPoolAllocator {

    private final ProtocolBucket bucket;
    public DefaultThreadPoolAllocator(ProtocolBucket bucket) {
        this.bucket = bucket;
    }


    @Override
    public void allocate(ProtocolType protocolType, Side side, ExecutorService threadPool) {
        // We get the equivalent pair
        ProtocolClientServerPair pair = bucket.getClientServerPairOf(protocolType);
        // And finally set the new thread pool.
        if(side == Side.SERVER)
            pair.server().setThreadPool(threadPool);
        else
            pair.client().setThreadPool(threadPool);
    }
}
