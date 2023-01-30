package fr.natsu.rmq.event;

import fr.natsu.rmq.event.protocol.ProtocolListener;
import fr.natsu.rmq.event.protocol.ProtocolEvent;

public record Subscriber<T extends ProtocolEvent>(Class<T> eventClass, ProtocolListener<T> protocolListener) {
}
