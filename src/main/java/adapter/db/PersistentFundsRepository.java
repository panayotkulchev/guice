package adapter.db;

import com.google.inject.Inject;
import core.FundsRepository;
import core.OperationHistory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Panayot Kulchev on 15-4-6.
 * e-mail: panayotkulchev@gmail.com
 * happy coding ...
 */
public class PersistentFundsRepository implements FundsRepository {

  private final DataStore dataStore;

  @Inject
  public PersistentFundsRepository(DataStore dataStore) {
    this.dataStore = dataStore;
  }

  @Override
  public Integer getAmount(Integer userPk) {
    return dataStore.executeCount("SELECT amount from bank.account WHERE user_fk='" + userPk + "'");
  }


  @Override
  public void deposit(Integer userPk, Integer amount) {

    Integer currentAmount = getAmount(userPk);
    dataStore.executeQery("UPDATE bank.account SET amount=? WHERE user_fk=?", currentAmount + amount, userPk);

  }

  @Override
  public boolean withdraw(Integer userPk, Integer amount) {
    boolean hasSuccess = false;
    Integer currentAmount = getAmount(userPk);
    if (currentAmount > amount) {
      dataStore.executeQery("UPDATE bank.account SET amount=? WHERE user_fk=?", currentAmount - amount, userPk);
      hasSuccess = true;
    }
    return hasSuccess;
  }

  @Override
  public void createAccount(Integer id) {
    dataStore.executeQery("INSERT INTO bank.account (amount, user_fk) VALUES (0, ?);", id);
  }

  @Override
  public List<OperationHistory> getWithdrawHistory(Integer userPk) {
    return dataStore.fetchRows("SELECT transaction_date,amount_before,amount_after FROM bank.account_history " +
            "WHERE user_fk='" + userPk + "'" +
            "AND amount_before>amount_after " +
            "ORDER BY transaction_date DESC " +
            "LIMIT 5;", new RowFetcher<OperationHistory>() {
      @Override
      public OperationHistory fetchRow(ResultSet rs) throws SQLException {
        return new OperationHistory(rs.getInt("amount_before"), rs.getInt("amount_after"), rs.getTimestamp("transaction_date"));
      }
    });
  }

  @Override
  public List<OperationHistory> getDepositHistory(Integer userPk) {
    return dataStore.fetchRows("SELECT transaction_date,amount_before,amount_after FROM bank.account_history " +
            "WHERE user_fk='" + userPk + "'" +
            "AND amount_before<amount_after " +
            "ORDER BY transaction_date DESC " +
            "LIMIT 5;", new RowFetcher<OperationHistory>() {
      @Override
      public OperationHistory fetchRow(ResultSet rs) throws SQLException {
        return new OperationHistory(rs.getInt("amount_before"), rs.getInt("amount_after"), rs.getTimestamp("transaction_date"));
      }
    });
  }

  @Override
  public List<OperationHistory> getAllHistory(Integer userPk) {
    return dataStore.fetchRows("SELECT transaction_date,amount_before,amount_after FROM bank.account_history " +
            "WHERE user_fk='" + userPk + "' " +
            "ORDER BY transaction_date DESC " +
            "LIMIT 100;", new RowFetcher<OperationHistory>() {
      @Override
      public OperationHistory fetchRow(ResultSet rs) throws SQLException {
        return new OperationHistory(rs.getInt("amount_before"), rs.getInt("amount_after"), rs.getTimestamp("transaction_date"));
      }
    });
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
    return dataStore.executeCount("SELECT count(*) FROM bank.account_history WHERE user_fk = "+userId+";");
  }

}
