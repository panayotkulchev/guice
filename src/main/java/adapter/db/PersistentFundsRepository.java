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
  public Integer getAmount(Integer userPk) {
    return dataStore.executeCount("SELECT amount from bank.account WHERE user_fk='" + userPk + "'");
  }


  @Override
  public void deposit(Integer userPk, Integer amount) {

    Integer currentAmount = getAmount(userPk);
    dataStore.executeQuery("UPDATE bank.account SET amount=? WHERE user_fk=?", currentAmount + amount, userPk);

  }

  @Override
  public boolean withdraw(Integer userPk, Integer amount) {
    boolean hasSuccess = false;
    Integer currentAmount = getAmount(userPk);
    if (currentAmount > amount) {
      dataStore.executeQuery("UPDATE bank.account SET amount=? WHERE user_fk=?", currentAmount - amount, userPk);
      hasSuccess = true;
    }
    return hasSuccess;
  }

  @Override
  public void createAccount(Integer id) {
    dataStore.executeQuery("INSERT INTO bank.account (amount, user_fk) VALUES (0, ?);", id);
  }

}