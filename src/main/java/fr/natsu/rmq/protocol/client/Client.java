package fr.natsu.rmq.protocol.client;

import fr.natsu.rmq.RabbitMQRegistration;
import fr.natsu.rmq.query.Request;
import fr.natsu.rmq.protocol.Protocol;

public abstract class Client extends Protocol {

    public Client(RabbitMQRegistration mediator) {
        super(mediator);
    }

    public abstract void send(Request request);

}
