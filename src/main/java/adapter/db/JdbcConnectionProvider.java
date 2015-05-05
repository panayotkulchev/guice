package adapter.db;

import adapter.http.ConnectionFilter;

import java.sql.Connection;

/**
 * Created on 15-1-23.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public class JdbcConnectionProvider implements ConnectionProvider {

  public Connection getConnection() {

    return ConnectionFilter.connectionThreadLocal.get();
  }

}

