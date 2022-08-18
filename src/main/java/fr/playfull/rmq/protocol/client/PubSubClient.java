package fr.playfull.rmq.protocol.client;

import com.rabbitmq.client.MessageProperties;
import fr.playfull.rmq.RabbitMQRegistration;
import fr.playfull.rmq.query.Request;

import java.io.IOException;

public class PubSubClient extends Client {

    public PubSubClient(RabbitMQRegistration mediator) {
        super(mediator);
    }

    @Override
    public void send(Request request) {
        try {
            getChannel().exchangeDeclare(request.getQueue(), "fanout");
            getChannel().basicPublish(request.getQueue(), "", null, getRegistration().getBufferManager().serialize(request.getPayload()));
            RabbitMQRegistration.getLogger().info(" [PUBSUB Client] Sent " + request.getPayload() + " in queue : " + request.getQueue() + ".");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
