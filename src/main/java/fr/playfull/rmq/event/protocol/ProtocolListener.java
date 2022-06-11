package fr.playfull.rmq.event.protocol;

import fr.playfull.rmq.event.protocol.ProtocolEvent;

public interface ProtocolListener<T extends ProtocolEvent> {

    void on(T protocolEvent);

}
