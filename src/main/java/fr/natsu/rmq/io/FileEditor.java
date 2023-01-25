package fr.natsu.rmq.io;

import fr.natsu.rmq.connect.Credentials;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface FileEditor {

    File create(String path, String name) throws IOException;

    void write(File file, Map<String, Object> content) throws IOException;

    Credentials read(File file);

}
