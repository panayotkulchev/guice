package adapter.db;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created on 15-2-20.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */
public interface RowFetcher<T> {

  T fetchRow(ResultSet rs) throws SQLException;

}
