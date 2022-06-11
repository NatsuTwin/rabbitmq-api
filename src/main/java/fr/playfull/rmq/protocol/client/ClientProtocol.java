package fr.playfull.rmq.protocol.client;

import fr.playfull.rmq.adapter.TypeAdapter;
import fr.playfull.rmq.protocol.Protocol;
import fr.playfull.rmq.query.Request;

public abstract class ClientProtocol extends Protocol {

    protected final TypeAdapter typeAdapter = new TypeAdapter();

    public abstract void send(Request<?> request);

}
