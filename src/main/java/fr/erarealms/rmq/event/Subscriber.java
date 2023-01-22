package fr.erarealms.rmq.event;

import fr.erarealms.rmq.event.protocol.ProtocolListener;
import fr.erarealms.rmq.event.protocol.ProtocolEvent;

public record Subscriber<T extends ProtocolEvent>(Class<T> eventClass, ProtocolListener<T> protocolListener) {
}
