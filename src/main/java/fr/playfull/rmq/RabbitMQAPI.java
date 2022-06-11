package fr.playfull.rmq;

import fr.playfull.rmq.connect.Credentials;
import fr.playfull.rmq.event.EventBus;
import fr.playfull.rmq.forward.Forwarder;
import fr.playfull.rmq.io.DefaultFileReader;
import fr.playfull.rmq.io.DefaultFileWriter;
import fr.playfull.rmq.marshal.RMQMarshal;
import fr.playfull.rmq.protocol.ProtocolType;
import fr.playfull.rmq.query.Request;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class RabbitMQAPI {

    private static Logger logger = Logger.getLogger("rmq-logger");
    private static RabbitMQAPI rabbitMQAPI;

    private final Forwarder forwarder = new Forwarder();
    private final EventBus eventBus = new EventBus();

    private RabbitMQAPI(String path) throws IOException {
        rabbitMQAPI = this;

        File file = new File(path, "credentials.yml");

        // If the file does not exist
        if(!file.exists()) {
            // We build the directory.
            new File(path).mkdirs();
            // We build the file.
            file.createNewFile();
            // We write the default information.
            new DefaultFileWriter().write(file);
        }

        // We read the file.
        Credentials credentials = new DefaultFileReader().read(file);
        forwarder.connectAll(credentials);

        // We set the shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(forwarder::closeAll));
    }

    public static void hook(String path) throws IOException {
        new RabbitMQAPI(path);
    }

    public static void main(String[] args) {
        try {
            hook(System.getProperty("user.dir") + "/");

            Forwarder forwarder = RabbitMQAPI.get().forwarder;
            forwarder.listen(ProtocolType.TCP, "salam", RMQMarshal.DEFAULT_MARSHAL);

            //get().getEventBus().subscribe(TCPMessageReceivedEvent.class, new TestReceivedEvent());

            Request<String> request = new Request.Builder<String>()
                .message("successfull_registed")
                .extra("NatsuTwin")
                .queueName("salam").build();

            forwarder.send(ProtocolType.TCP, request);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static Logger getLogger() {
        return logger;
    }

    public static RabbitMQAPI get() {
        if(rabbitMQAPI == null)
            throw new IllegalStateException("Tried to access the API before initializing it.");
        return rabbitMQAPI;
    }

    public Forwarder getForwarder() {
        return this.forwarder;
    }

    public EventBus getEventBus() {
        return this.eventBus;
    }

}
