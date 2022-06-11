package fr.playfull.rmq.protocol.client;

import com.rabbitmq.client.Channel;
import fr.playfull.rmq.query.Request;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

public class TCPClientProtocol extends ClientProtocol {

    @Override
    public void send(Request<?> request) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                Channel channel = getConnection().createChannel();
                String totalMessage = (request.getRequestMessage().getExtra().isEmpty())
                        ? request.getRequestMessage().getMessage()
                        : request.getRequestMessage().getMessage() + ":" + request.getRequestMessage().getExtra();

                channel.queueDeclare(request.getQueue(), false, false, false, null);
                channel.basicPublish("", request.getQueue(), null, totalMessage.getBytes());
                channel.close();
            } catch (IOException | TimeoutException exception) {
                exception.printStackTrace();
            }
        });
    }
}
