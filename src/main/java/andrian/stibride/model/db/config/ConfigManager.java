package andrian.stibride.model.db.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Properties;

/**
 * This singleton gives access to all properties from config.properties.
 *
 */
public class ConfigManager {

    private ConfigManager() {
        prop = new Properties();
        url = Objects.requireNonNull(getClass().getClassLoader().getResource(FILE)).getFile();
        try {
            load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final String FILE = "./config/config.properties";

    private final Properties prop;

    private final String url;

    /**
     * Loads the properties from this url.
     *
     * @throws IOException if no file is found.
     */
    public void load() throws IOException {
        URI uri = null;
        try {
            uri = new URI(url);
        } catch (URISyntaxException ex) {
            throw new IOException("Chargement configuration impossible " + ex.getMessage());
        }
        try (InputStream input = new FileInputStream(uri.getPath())) {
            prop.load(input);
        } catch (IOException ex) {
            throw new IOException("Chargement configuration impossible " + ex.getMessage());
        }

    }

    /**
     * Returns the value from the key name.
     *
     * @param name key to found.
     * @return the value from the key-value pair.
     */
    public String getProperties(String name) {
        return prop.getProperty(name);
    }

    /**
     * Returns the instance of the singleton.
     * @return the instance of the singleton.
     */
    public static ConfigManager getInstance() {
        return ConfigManagerHolder.INSTANCE;
    }

    private static class ConfigManagerHolder {

        private static final ConfigManager INSTANCE = new ConfigManager();
    }
}
