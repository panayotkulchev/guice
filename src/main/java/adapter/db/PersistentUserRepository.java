package adapter.db;

import com.google.inject.Inject;
import core.AuthorizationResult;
import core.User;
import core.UserRepository;
import core.CurrentUser;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public class PersistentUserRepository implements UserRepository {

  private final DataStore dataStore;

  @Inject
  public PersistentUserRepository(DataStore dataStore) {

    this.dataStore = dataStore;
  }

  @Override
  public boolean register(String email, String password) {
    if (!isExisting(email)){
      dataStore.executeQery("INSERT INTO user (user_email,user_password) values(?,?);", email, password);
      return true;
    }
    return false;
  }

  @Override
  public boolean isExisting(String email) {
    Integer count;
    count = dataStore.executeCount("SELECT count(*) FROM bank.user where user_email = '" + email + "';");
    return count != 0;
  }

  @Override
  public boolean isExisting(String email, String password) {
    Integer count;
    count = dataStore.executeCount("SELECT count(*) FROM bank.user where user_email = '" + email + "'"+"and user_password = '"+password+"' ;");
    return count != 0;
  }

  @Override
  public AuthorizationResult authorize(String email, String password) {
    if (isExisting(email,password)){
      return new AuthorizationResult(getByEmail(email),true);
    }
    else {
      return new AuthorizationResult(null,false);
    }
  }

  @Override
  public User getByEmail(String email) {
    String emailToInject = "'" + email + "'";
    return dataStore.fetchRow("SELECT * FROM user WHERE user_email =" + emailToInject, new RowFetcher<User>() {
      @Override
      public User fetchRow(ResultSet rs) throws SQLException {
        return new User(rs.getInt("user_pk"), rs.getString("user_email"));
      }
    });
  }

  @Override
  public CurrentUser getBySid(String sid) {
    String query = "select user_pk, user_email, amount from bank.user " +
            "join bank.session on bank.user.user_pk=bank.session.user_fk " +
            "join bank.account on bank.user.user_pk=bank.account.user_fk " +
            "where sid= '"+sid+"' ;";
    return dataStore.fetchRow(query, new RowFetcher<CurrentUser>() {
      @Override
      public CurrentUser fetchRow(ResultSet rs) throws SQLException {
        return new CurrentUser(rs.getInt("user_pk"),rs.getString("user_email"),rs.getInt("amount"));
      }
    });
  }

}
