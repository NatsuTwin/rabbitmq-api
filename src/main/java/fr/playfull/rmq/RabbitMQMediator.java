package fr.playfull.rmq;

import fr.playfull.rmq.connect.Connector;
import fr.playfull.rmq.connect.Credentials;
import fr.playfull.rmq.connect.DefaultConnector;
import fr.playfull.rmq.event.EventBus;
import fr.playfull.rmq.forward.Forwarder;
import fr.playfull.rmq.io.DefaultFileEditor;
import fr.playfull.rmq.io.FileEditor;
import fr.playfull.rmq.protocol.ProtocolBucket;
import fr.playfull.rmq.serializer.ByteSerializableBufferManager;
import fr.playfull.rmq.serializer.DefaultByteSerializableBufferManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;

public class RabbitMQMediator {

    private static final Logger LOGGER = LoggerFactory.getLogger("rmq-logger");

    private final Connector connector;
    private final Forwarder forwarder;
    private final EventBus eventBus = new EventBus();
    private final ByteSerializableBufferManager bufferManager = new DefaultByteSerializableBufferManager();

    protected RabbitMQMediator(String path) throws IOException {
        // We manage the forwarder loading.
        ProtocolBucket protocolBucket = new ProtocolBucket();
        connector = new DefaultConnector(protocolBucket);
        forwarder = new Forwarder(protocolBucket);

        // We manage the file creation.
        FileEditor fileEditor = new DefaultFileEditor();
        File createdFile = fileEditor.create(path, "credentials.yml");
        Credentials credentials = fileEditor.read(createdFile);


        // We manage the connection.
        connector.connectAll(credentials);

        // We set the shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(connector::disconnectAll));
    }

    public static RabbitMQMediator hook(String path) throws IOException {
        return new RabbitMQMediator(path);
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    public ByteSerializableBufferManager getBufferManager() {
        return bufferManager;
    }

    public Forwarder getForwarder() {
        return forwarder;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    @Nullable
    public Connector getConnector() {
        return connector;
    }
}
