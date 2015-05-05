package adapter.db.pool;

import adapter.db.ConnectionProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Panayot Kulchev on 15-4-15.
 * e-mail: panayotkulchev@gmail.com
 * happy coding ...
 */
public class ConnectionPool implements Pool<Connection> {

  private Logger logger = LoggerFactory.getLogger(ConnectionPool.class);


  private ConcurrentLinkedQueue<Connection> availableConnections;


  private static ConnectionPool instance = new ConnectionPool();

  private ConnectionPool() {

    availableConnections = new ConcurrentLinkedQueue<Connection>();

    Integer numbOfConnections = ConnectionProperties.get("numberConnectionsInPool");

    for (int i = 0; i < numbOfConnections; i++) {
      availableConnections.add(getConnection());
    }
  }

  public static ConnectionPool getInstance() {
    return instance;
  }

  @Override
  public Connection acquire() {

      Connection connection = availableConnections.poll();

      logger.info("Connection ACQUIRED " + connection);

      return connection;

  }

  @Override
  public void release(Connection connection) {

    if (connection!=null){
      availableConnections.add(connection);
      logger.info("Connection RELEASED " + connection);
    }
  }

  public Connection getConnection() {
    Connection connection = null;
    try {
      connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "root");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return connection;
  }
}
