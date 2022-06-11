package fr.playfull.rmq.event;

import fr.playfull.rmq.event.protocol.ProtocolEvent;
import fr.playfull.rmq.event.protocol.ProtocolListener;

public record Subscriber<T extends ProtocolEvent>(Class<T> eventClass, ProtocolListener<T> protocolListener) {
}
