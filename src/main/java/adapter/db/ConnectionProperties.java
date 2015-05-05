package adapter.db;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by Panayot Kulchev on 15-4-15.
 * e-mail: panayotkulchev@gmail.com
 * happy coding ...
 */
public class ConnectionProperties {

  public static Integer get(String propertyName) {

    Properties prop = new Properties();
    try {

      prop.load(new FileInputStream(new File("src/main/resources/connection.properties")));

    } catch (Exception e) {
      e.printStackTrace();
    }

    return  Integer.parseInt(prop.getProperty(propertyName));
  }

}
