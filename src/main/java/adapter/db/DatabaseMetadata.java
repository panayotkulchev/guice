package adapter.db;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public class DatabaseMetadata {

    public String get(String propertyName) {

        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(new File("src/main/resources/configuration.properties")));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  prop.getProperty(propertyName);
    }
}
