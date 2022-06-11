package fr.playfull.rmq.io;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DefaultFileWriter {

    public void write(File file) {
        Map<String, Object> data = new HashMap<>();
        data.put("host", "0.0.0.1");
        data.put("port", 5672);
        data.put("user", "playfull");
        data.put("password", "azerty");

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        Yaml yaml = new Yaml(options);
        try {
            FileWriter fileWriter = new FileWriter(file.getPath());
            yaml.dump(data, fileWriter);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
