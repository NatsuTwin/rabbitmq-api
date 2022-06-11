package fr.playfull.rmq;

import fr.playfull.rmq.connect.Credentials;
import fr.playfull.rmq.event.EventBus;
import fr.playfull.rmq.forward.Forwarder;
import fr.playfull.rmq.io.DefaultFileReader;
import fr.playfull.rmq.io.DefaultFileWriter;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class RabbitMQAPI {

    private static Logger logger = Logger.getLogger("rmq-logger");

    private static final Forwarder forwarder = new Forwarder();
    private static final EventBus eventBus = new EventBus();

    private RabbitMQAPI(String path) throws IOException {

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

    public static Logger getLogger() {
        return logger;
    }

    public static Forwarder getForwarder() {
        return forwarder;
    }

    public static EventBus getEventBus() {
        return eventBus;
    }

}
