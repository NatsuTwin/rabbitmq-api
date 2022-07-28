package fr.playfull.rmq.protocol.server;

import fr.playfull.rmq.protocol.Protocol;

public abstract class Server extends Protocol {

    public abstract void listen(String queue);
}
