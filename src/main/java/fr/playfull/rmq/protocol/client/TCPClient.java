package fr.playfull.rmq.protocol.client;

import com.rabbitmq.client.Channel;
import fr.playfull.rmq.RabbitMQAPI;
import fr.playfull.rmq.query.Request;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TCPClient extends Client {

    @Override
    public void send(Request request) {
        getThreadPool().execute(() -> {
            try {
                Channel channel = getConnection().createChannel();

                channel.queueDeclare(request.getQueue(), false, false, false, null);
                channel.basicPublish("", request.getQueue(), null, RabbitMQAPI.getBufferManager().serialize(request.getPayload()));
                channel.close();
            } catch (IOException | TimeoutException exception) {
                exception.printStackTrace();
            }
        });
    }
}
