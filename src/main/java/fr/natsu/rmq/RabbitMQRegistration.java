package fr.natsu.rmq;

import fr.natsu.rmq.event.EventBus;
import fr.natsu.rmq.io.DefaultFileEditor;
import fr.natsu.rmq.io.FileEditor;
import fr.natsu.rmq.protocol.ProtocolMediator;
import fr.natsu.rmq.protocol.store.ProtocolBucket;
import fr.natsu.rmq.serializer.ByteSerializableBufferManager;
import fr.natsu.rmq.serializer.DefaultByteSerializableBufferManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class RabbitMQRegistration {

    private static final Logger LOGGER = LoggerFactory.getLogger("rmq-logger");

    private final ProtocolMediator protocolMediator;
    private final EventBus eventBus = new EventBus();
    private final ByteSerializableBufferManager bufferManager = new DefaultByteSerializableBufferManager(LOGGER);

    protected RabbitMQRegistration(String path) throws IOException {
        // We manage the forwarder loading.
        ProtocolBucket protocolBucket = new ProtocolBucket(this);
        // We also read the data.
        FileEditor fileEditor = new DefaultFileEditor();
        File createdFile = fileEditor.create(path, "credentials.yml");

        this.protocolMediator = ProtocolMediator.of(fileEditor.read(createdFile), protocolBucket);

        // We set the shutdown hook
        //Runtime.getRuntime().addShutdownHook(new Thread(protocolMediator::disconnectAll));
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
