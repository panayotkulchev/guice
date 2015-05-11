package adapter.db;

import com.google.inject.Singleton;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

@Singleton
public class DatabaseMetadata {

  Properties prop = new Properties();

  public String get(String propertyName) {

    try {
      prop.load(new FileInputStream(new File("src/main/resources/configuration.properties")));

    } catch (Exception e) {
      e.printStackTrace();
    }
    return prop.getProperty(propertyName);
  }
}
