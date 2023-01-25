package fr.natsu.rmq.event.protocol;

public interface ProtocolListener<T extends ProtocolEvent> {

    void on(T event);

}
