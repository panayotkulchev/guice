package adapter.shedule;

import adapter.db.ConnectionProvider;
import adapter.db.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created on 15-1-23.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public class SchedulerConnectionProvider implements ConnectionProvider {

  public Connection getConnection() {
    Connection connection = null;
    try {

      connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "root");

    } catch (Exception e) {
      e.printStackTrace();
    }
    return connection;
//    return ConnectionPool.getInstance().acquire();


  }
}

