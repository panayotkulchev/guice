package adapter.db;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by pepo on 15-5-2.
 */
public class PageProperties {

    public static Integer get(String propertyName) {

        Properties prop = new Properties();
        try {

            prop.load(new FileInputStream(new File("src/main/resources/pagination.properties")));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return  Integer.parseInt(prop.getProperty(propertyName));
    }

}
