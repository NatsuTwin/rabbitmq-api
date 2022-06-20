package fr.playfull.rmq.protocol;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import fr.playfull.rmq.RabbitMQAPI;
import fr.playfull.rmq.connect.Credentials;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeoutException;

public abstract class Protocol {

    private Connection connection;
    private final ForkJoinPool threadPool = ForkJoinPool.commonPool();

    public void connect(Credentials credentials) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setChannelRpcTimeout(Integer.MAX_VALUE);
        connectionFactory.setHost(credentials.getHost());
        connectionFactory.setPort(credentials.getPort());
        connectionFactory.setUsername(credentials.getUserName());
        connectionFactory.setPassword(credentials.getPassword());
        connectionFactory.setRequestedHeartbeat(2);

        try {
            this.connection = connectionFactory.newConnection();
            RabbitMQAPI.getLogger().info("Established connection with RabbitMQ !");
        } catch (IOException | TimeoutException exception) {
            RabbitMQAPI.getLogger().severe(exception.getMessage());
        }
    }

    protected Connection getConnection() {
        return this.connection;
    }

    protected ExecutorService getThreadPool() { return this.threadPool; }
}
