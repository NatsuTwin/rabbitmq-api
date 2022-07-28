package fr.playfull.rmq.protocol;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import fr.playfull.rmq.RabbitMQRegistration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class Protocol {

    private final RabbitMQRegistration registration;

    private Connection connection;
    private ExecutorService threadPool;
    private Channel channel;

    public Protocol(RabbitMQRegistration registration) {
        this.threadPool = Executors.newSingleThreadExecutor();
        this.registration = registration;
    }

    public void setThreadPool(ExecutorService threadPool) {
        this.threadPool = threadPool;
    }

    public Protocol setConnection(Connection connection) {
        this.connection = connection;
        return this;
    }

    public Protocol setChannel(Channel channel) {
        this.channel = channel;
        return this;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public Channel getChannel() {
        return this.channel;
    }

    public ExecutorService getThreadPool() {
        return this.threadPool;
    }

    public RabbitMQRegistration getRegistration() {
        return this.registration;
    }
}
