package adapter.db.pool;

import adapter.db.ConfigurationProperites;
import adapter.db.DatabaseMetadata;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */
public class ConnectionPool implements Pool<Connection> {

//    private final DatabaseMetadata databaseMetadata;
    private Logger logger = LoggerFactory.getLogger(ConnectionPool.class);


    private ConcurrentLinkedQueue<Connection> availableConnections;


    private static ConnectionPool instance = new ConnectionPool();

//    @Inject
    private ConnectionPool(/*DatabaseMetadata databaseMetadata*/) {
//        this.databaseMetadata = databaseMetadata;

        availableConnections = new ConcurrentLinkedQueue<Connection>();

        Integer numbOfConnections = ConfigurationProperites.get("numberConnectionsInPool");

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

        if (connection != null) {
            availableConnections.add(connection);
            logger.info("Connection RELEASED " + connection);
        }
    }

    public Connection getConnection() {
        Connection connection = null;

//        String dbHost = databaseMetadata.get("db.host");
//        String dbUsername = databaseMetadata.get("db.username");
//        String dbPassword = databaseMetadata.get("db.password");

        try {
//            connection = DriverManager.getConnection(dbHost, dbUsername, dbPassword);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
