package fr.playfull.rmq.event;

import fr.playfull.rmq.event.protocol.ProtocolEvent;
import fr.playfull.rmq.event.protocol.ProtocolListener;

import java.util.concurrent.ConcurrentLinkedQueue;

public class EventBus {

    private final ConcurrentLinkedQueue<Subscriber<?>> subscribers = new ConcurrentLinkedQueue<>();

    public <E extends ProtocolEvent> void subscribe(Class<E> eventClass, ProtocolListener<E> listener){
        Subscriber<E> subscriber = new Subscriber<>(eventClass, listener);
        register(subscriber);
    }

    public <E extends ProtocolEvent> void publish(E event){
        this.subscribers.forEach(subscriber -> {
            // If the subscriber is not listening to the correct event
            if(subscriber.eventClass() != event.getClass())
                return;

            // We call the event for the subscriber
            ((Subscriber<E>)subscriber).protocolListener().on(event);
        });
    }

    private <E extends ProtocolEvent> void register(Subscriber<E> eventSubscriber){
        this.subscribers.add(eventSubscriber);
    }
}
