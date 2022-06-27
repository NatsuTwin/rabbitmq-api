package fr.playfull.rmq.protocol.client;

import fr.playfull.rmq.protocol.Protocol;
import fr.playfull.rmq.query.Request;

public abstract class Client extends Protocol {

    public abstract void send(Request request);

}
