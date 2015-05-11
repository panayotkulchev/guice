package adapter.db;

import com.google.inject.Inject;
import core.FundsRepository;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public class PersistentFundsRepository implements FundsRepository {

  private final DataStore dataStore;

  @Inject
  public PersistentFundsRepository(DataStore dataStore) {
    this.dataStore = dataStore;
  }

  @Override
  public Integer getAmount(Integer userId) {
    return dataStore.executeCount("SELECT amount from bank.account WHERE user_fk='" + userId + "'");
  }


  @Override
  public void deposit(Integer userId, Integer amount) {

    Integer currentAmount = getAmount(userId);
    dataStore.executeQuery("UPDATE bank.account SET amount=? WHERE user_fk=?", currentAmount + amount, userId);

  }

  @Override
  public boolean withdraw(Integer userId, Integer amount) {
    boolean hasSuccess = false;
    Integer currentAmount = getAmount(userId);
    if (currentAmount > amount) {
      dataStore.executeQuery("UPDATE bank.account SET amount=? WHERE user_fk=?", currentAmount - amount, userId);
      hasSuccess = true;
    }
    return hasSuccess;
  }

  @Override
  public void createAccount(Integer userId) {
    dataStore.executeQuery("INSERT INTO bank.account (amount, user_fk) VALUES (0, ?);", userId);
  }

}