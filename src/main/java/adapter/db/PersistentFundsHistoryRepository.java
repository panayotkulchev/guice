package adapter.db;

import com.google.inject.Inject;
import core.FundsHistoryRepository;
import core.OperationHistory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public class PersistentFundsHistoryRepository implements FundsHistoryRepository {

  private final DataStore dataStore;

  @Inject
  public PersistentFundsHistoryRepository(DataStore dataStore) {
    this.dataStore = dataStore;
  }

  @Override
  public List<OperationHistory> getHistoryByPages(Integer userPk, Integer start, Integer end) {
    return dataStore.fetchRows("SELECT transaction_date,amount_before,amount_after FROM bank.account_history " +
            "WHERE user_fk=" + userPk +
            " ORDER BY transaction_date DESC " +
            "LIMIT " + start + "  ,  " + end + " ; ", new RowFetcher<OperationHistory>() {
      @Override
      public OperationHistory fetchRow(ResultSet rs) throws SQLException {
        return new OperationHistory(rs.getInt("amount_before"), rs.getInt("amount_after"), rs.getTimestamp("transaction_date"));
      }
    });
  }

  @Override
  public Integer countRecords(Integer userId) {
    return dataStore.executeCount("SELECT count(*) FROM bank.account_history WHERE user_fk = " + userId + ";");
  }
}
