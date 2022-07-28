package fr.playfull.rmq.protocol.client;

import fr.playfull.rmq.RabbitMQAPI;
import fr.playfull.rmq.RabbitMQMediator;
import fr.playfull.rmq.query.Request;

import java.io.IOException;
import java.util.UUID;

public class TCPClient extends Client {

    @Override
    public void send(Request request) {
        getThreadPool().execute(() -> {
            Thread.currentThread().setName("tcp-client-thread" + UUID.randomUUID());
            try {
                getChannel().queueDeclare(request.getQueue(), false, false, false, null);
                getChannel().basicPublish("", request.getQueue(), null, RabbitMQAPI.getBufferManager().serialize(request.getPayload()));
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
    }
}
