package fr.natsu.rmq.connect;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import fr.natsu.rmq.RabbitMQRegistration;
import fr.natsu.rmq.pair.ProtocolClientServerPair;
import fr.natsu.rmq.protocol.Protocol;
import fr.natsu.rmq.protocol.ProtocolType;
import fr.natsu.rmq.protocol.client.Client;
import fr.natsu.rmq.protocol.server.Server;
import fr.natsu.rmq.protocol.store.ProtocolBucket;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

public final class DefaultConnector implements Connector {

    private final ProtocolBucket protocolBucket;
    public DefaultConnector(ProtocolBucket protocolBucket) {
        this.protocolBucket = protocolBucket;
    }

    @Override
    public void connectAll(Credentials credentials) {
        // We build a factory.
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setRequestedHeartbeat(2);
        connectionFactory.setHost(credentials.getHost());
        connectionFactory.setPort(credentials.getPort());
        connectionFactory.setShutdownTimeout(Integer.MAX_VALUE);
        connectionFactory.setUsername(credentials.getUserName());
        connectionFactory.setPassword(credentials.getPassword());
        connectionFactory.setChannelRpcTimeout(Integer.MAX_VALUE);
        // And then define a protocol.
        Consumer<Protocol> protocolConsumer = protocol -> {
            try {
                Connection connection = connectionFactory.newConnection(protocol.getThreadPool());
                Channel channel = connection.createChannel();

                // We first do some elementary checks to avoid a Too Many Connections error.
                if(protocol.getConnection() != null && protocol.getChannel() != null) {
                    if(protocol.getConnection().isOpen())
                        protocol.getConnection().close();
                    if(protocol.getChannel().isOpen())
                        protocol.getChannel().close();
                }

                // We then set the connection and channel for the protocol.
                protocol.setConnection(connection).setChannel(channel);
            } catch (IOException | TimeoutException exception) {
                RabbitMQRegistration.getLogger().error("There was an error while trying to connect to RMQ ");
            }
        };
        // We finally connect all the protocols, one by one.
        for(Map.Entry<ProtocolType, ProtocolClientServerPair> entry : protocolBucket.getProtocols().entrySet()) {
            protocolConsumer.accept(entry.getValue().client());
            protocolConsumer.accept(entry.getValue().server());
        }
    }

    @Override
    public void disconnectAll() {
        for(Map.Entry<ProtocolType, ProtocolClientServerPair> entry : protocolBucket.getProtocols().entrySet()) {
            Server server = entry.getValue().server();
            Client client = entry.getValue().client();

            try {
                if(client.getConnection().isOpen())
                    client.getConnection().close();
                if(client.getChannel().isOpen())
                    client.getChannel().close();

                if(server.getConnection().isOpen())
                    server.getConnection().close();
                if(server.getChannel().isOpen())
                    server.getChannel().close();
            } catch(IOException | TimeoutException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
