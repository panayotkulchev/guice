package adapter.db.pool;

import adapter.db.ConnectionProvider;
import com.google.inject.Singleton;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by Panayot Kulchev on 15-4-27.
 * e-mail: panayotkulchev@gmail.com
 * happy coding ...
 */

@Singleton
public class FakeJdbcConnectionProvider implements ConnectionProvider {
  public FakeJdbcConnectionProvider() {
    System.out.println("fake provider was instantiated");
  }

  @Override
  public Connection getConnection() {

    Connection connection = null;
    try {
      connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "root");
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println("new connection was init");
    return connection;
  }
}
