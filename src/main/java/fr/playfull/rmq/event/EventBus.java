package fr.playfull.rmq.event;

import fr.playfull.rmq.event.protocol.ProtocolEvent;
import fr.playfull.rmq.event.protocol.ProtocolListener;

import java.util.concurrent.ConcurrentLinkedQueue;

public class EventBus {

    private final ConcurrentLinkedQueue<Subscriber<?>> subscribers = new ConcurrentLinkedQueue<>();

    public <E extends ProtocolEvent> void subscribe(Class<E> eventClass, ProtocolListener<E> listener){
        Subscriber<?> subscriber = new Subscriber<>(eventClass, listener);
        this.subscribers.add(subscriber);
    }

    public <E extends ProtocolEvent> void publish(E event){
        for(Subscriber<?> subscriber : subscribers) {
            // If the subscriber is not listening to the correct event
            if(subscriber.eventClass() != event.getClass())
                return;
            // We call the event for the subscriber
            ((Subscriber<E>)subscriber).protocolListener().on(event);
        }
    }
}
