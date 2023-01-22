package fr.erarealms.rmq.protocol.client;

import fr.erarealms.rmq.RabbitMQRegistration;
import fr.erarealms.rmq.query.Request;

import java.io.IOException;

public class PubSubClient extends Client {

    public PubSubClient(RabbitMQRegistration mediator) {
        super(mediator);
    }

    @Override
    public void send(Request request) {
        this.getThreadPool().execute(() -> {
            try {
                getChannel().exchangeDeclare(request.getQueue(), "fanout");
                getChannel().basicPublish(request.getQueue(), "", null, getRegistration().getBufferManager().serialize(request.getPayload()));
                RabbitMQRegistration.getLogger().info(" [PUBSUB Client] Sent data (type :" + request.getPayload().getClass().getName() + ") in queue : " + request.getQueue() + ".");
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
    }
}
