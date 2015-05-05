package adapter.db;

import java.sql.Connection;

/**
 * Created on 15-2-12.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */
public interface ConnectionProvider {

  Connection getConnection();

}
