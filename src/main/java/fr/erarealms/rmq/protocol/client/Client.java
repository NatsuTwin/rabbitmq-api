package fr.erarealms.rmq.protocol.client;

import fr.erarealms.rmq.RabbitMQRegistration;
import fr.erarealms.rmq.query.Request;
import fr.erarealms.rmq.protocol.Protocol;

public abstract class Client extends Protocol {

    public Client(RabbitMQRegistration mediator) {
        super(mediator);
    }

    public abstract void send(Request request);

}
