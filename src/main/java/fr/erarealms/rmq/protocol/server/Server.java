package fr.erarealms.rmq.protocol.server;

import fr.erarealms.rmq.RabbitMQRegistration;
import fr.erarealms.rmq.protocol.Protocol;

public abstract class Server extends Protocol {

    public Server(RabbitMQRegistration mediator) {
        super(mediator);
    }

    public abstract void listen(String queue);
}
