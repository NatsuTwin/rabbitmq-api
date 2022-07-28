package fr.playfull.rmq.protocol.server;

import fr.playfull.rmq.RabbitMQRegistration;
import fr.playfull.rmq.protocol.Protocol;

public abstract class Server extends Protocol {

    public Server(RabbitMQRegistration mediator) {
        super(mediator);
    }

    public abstract void listen(String queue);
}
