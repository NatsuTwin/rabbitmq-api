package fr.playfull.rmq;

import fr.playfull.rmq.connect.Connector;
import fr.playfull.rmq.connect.Credentials;
import fr.playfull.rmq.connect.DefaultConnector;
import fr.playfull.rmq.event.EventBus;
import fr.playfull.rmq.forward.Forwarder;
import fr.playfull.rmq.io.DefaultFileEditor;
import fr.playfull.rmq.io.FileEditor;
import fr.playfull.rmq.protocol.ProtocolMediator;
import fr.playfull.rmq.protocol.store.ProtocolBucket;
import fr.playfull.rmq.serializer.ByteSerializableBufferManager;
import fr.playfull.rmq.serializer.DefaultByteSerializableBufferManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;

public class RabbitMQRegistration {

    private static final Logger LOGGER = LoggerFactory.getLogger("rmq-logger");

    private final ProtocolMediator protocolMediator;
    private final EventBus eventBus = new EventBus();
    private final ByteSerializableBufferManager bufferManager = new DefaultByteSerializableBufferManager();

    protected RabbitMQRegistration(String path) throws IOException {
        // We manage the forwarder loading.
        ProtocolBucket protocolBucket = new ProtocolBucket(this);
        // We also read the data.
        FileEditor fileEditor = new DefaultFileEditor();
        File createdFile = fileEditor.create(path, "credentials.yml");

        this.protocolMediator = ProtocolMediator.of(fileEditor.read(createdFile), protocolBucket);

        // We set the shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(protocolMediator::disconnectAll));
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    public ByteSerializableBufferManager getBufferManager() {
        return bufferManager;
    }

    public ProtocolMediator getMediator() {
        return this.protocolMediator;
    }

    public EventBus getEventBus() {
        return eventBus;
    }
}
