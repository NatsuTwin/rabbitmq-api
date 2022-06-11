package fr.playfull.rmq.io;

import fr.playfull.rmq.RabbitMQAPI;
import fr.playfull.rmq.connect.Credentials;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public class DefaultFileReader {

    public Credentials read(File file) {
        Yaml yaml = new Yaml();

        try(FileInputStream fileInputStream = new FileInputStream(file)){
            Map<String, Object> map = yaml.load(fileInputStream);
            return new Credentials((String)map.get("host"), (int)map.get("port"), (String)map.get("user"), (String)map.get("password"));
        } catch (IOException exception) {
            RabbitMQAPI.getLogger().severe("Error occurred whilst trying to read the file : " + exception.getMessage());
        }

        return null;
    }

}
