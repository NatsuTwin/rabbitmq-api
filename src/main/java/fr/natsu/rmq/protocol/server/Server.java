package fr.natsu.rmq.protocol.server;

import fr.natsu.rmq.RabbitMQRegistration;
import fr.natsu.rmq.protocol.Protocol;

public abstract class Server extends Protocol {

    public Server(RabbitMQRegistration mediator) {
        super(mediator);
    }

    public abstract void listen(String queue);
}
