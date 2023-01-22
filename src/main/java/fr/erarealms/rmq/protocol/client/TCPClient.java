package fr.erarealms.rmq.protocol.client;

import fr.erarealms.rmq.RabbitMQRegistration;
import fr.erarealms.rmq.query.Request;

import java.io.IOException;
import java.util.UUID;

public class TCPClient extends Client {

    public TCPClient(RabbitMQRegistration mediator) {
        super(mediator);
    }

    @Override
    public void send(Request request) {
        getThreadPool().execute(() -> {
            Thread.currentThread().setName("tcp-client-thread" + UUID.randomUUID());
            try {
                getChannel().queueDeclare(request.getQueue(), false, false, false, null);
                getChannel().basicPublish("", request.getQueue(), null, getRegistration().getBufferManager().serialize(request.getPayload()));
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
    }
}
