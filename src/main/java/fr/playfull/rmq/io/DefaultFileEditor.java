package fr.playfull.rmq.io;

import fr.playfull.rmq.RabbitMQRegistration;
import fr.playfull.rmq.connect.Credentials;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DefaultFileEditor implements FileEditor {

    @Override
    public File create(String path, String name) throws IOException {
        File file = new File(path, "credentials.yml");
        // If the file does not exist
        if(!file.exists()) {
            // We build the directory.
            new File(path).mkdirs();
            // We build the file.
            file.createNewFile();
            // We write the default information.
            Map<String, Object> data = new HashMap<>();
            data.put("host", "0.0.0.1");
            data.put("port", 5672);
            data.put("user", "playfull");
            data.put("password", "azerty");
            this.write(file, data);
        }
        return file;
    }

    @Override
    public void write(File file, Map<String, Object> content) throws IOException {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        FileWriter fileWriter = new FileWriter(file.getPath());
        new Yaml(options).dump(content, fileWriter);
        fileWriter.close();
    }

    @Override
    public Credentials read(File file) {
        Yaml yaml = new Yaml();

        try{
            FileInputStream fileInputStream = new FileInputStream(file);
            Map<String, Object> map = yaml.load(fileInputStream);

            fileInputStream.close();

            return new Credentials((String)map.get("host"), (int)map.get("port"), (String)map.get("user"), (String)map.get("password"));
        } catch (IOException exception) {
            RabbitMQRegistration.getLogger().error("Error occurred whilst trying to read the file : " + exception.getMessage());
        }

        throw new NullPointerException("Could not read the file !");
    }
}
