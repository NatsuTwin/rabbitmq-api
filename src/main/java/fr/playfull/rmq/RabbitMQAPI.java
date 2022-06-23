package fr.playfull.rmq;

import fr.playfull.rmq.connect.Connector;
import fr.playfull.rmq.connect.Credentials;
import fr.playfull.rmq.connect.DefaultConnector;
import fr.playfull.rmq.event.EventBus;
import fr.playfull.rmq.forward.Forwarder;
import fr.playfull.rmq.io.DefaultFileEditor;
import fr.playfull.rmq.io.FileEditor;
import fr.playfull.rmq.protocol.ProtocolBucket;
import fr.playfull.rmq.protocol.ProtocolClientServerPair;
import fr.playfull.rmq.protocol.ProtocolType;
import fr.playfull.rmq.serializer.ByteSerializableBufferManager;
import fr.playfull.rmq.serializer.DefaultByteSerializableBufferManager;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

public class RabbitMQAPI {

    private static final Logger LOGGER = Logger.getLogger("rmq-logger");

    private static Forwarder forwarder;
    private static final EventBus eventBus = new EventBus();
    private static final ByteSerializableBufferManager bufferManager = new DefaultByteSerializableBufferManager();

    private RabbitMQAPI(String path) throws IOException {
        // We manage the forwarder loading.
        ProtocolBucket protocolBucket = new ProtocolBucket();
        forwarder = new Forwarder(protocolBucket);

        // We manage the file creation.
        FileEditor fileEditor = new DefaultFileEditor();
        File createdFile = fileEditor.create(path, "credentials.yml");
        Credentials credentials = fileEditor.read(createdFile);

        // We manage the connection.
        Connector connector = new DefaultConnector();
        for(Map.Entry<ProtocolType, ProtocolClientServerPair> entry : protocolBucket.getProtocols().entrySet()) {
            connector.connect(entry.getValue().server(), credentials);
            connector.connect(entry.getValue().client(), credentials);
        }

        // We set the shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            for(Map.Entry<ProtocolType, ProtocolClientServerPair> entry : protocolBucket.getProtocols().entrySet()) {
                connector.disconnect(entry.getValue().server());
            }
        }));
    }

    public static RabbitMQAPI hook(String path) throws IOException {
        return new RabbitMQAPI(path);
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    public static ByteSerializableBufferManager getBufferManager() {
        return bufferManager;
    }

    public static Forwarder getForwarder() {
        return forwarder;
    }

    public static EventBus getEventBus() {
        return eventBus;
    }

}
