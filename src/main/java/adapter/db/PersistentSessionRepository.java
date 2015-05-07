package adapter.db;

import com.google.inject.Inject;
import core.Session;
import core.SessionRepository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */
public class PersistentSessionRepository implements SessionRepository {

  private final DataStore dataStore;

  @Inject
  public PersistentSessionRepository(DataStore dataStore) {
    this.dataStore = dataStore;
  }


  @Override
  public boolean refresh(String sid, Long expirationTime) {
    if (isExisting(sid)) {
      dataStore.executeQery("UPDATE bank.session SET expiration_time=? " +
              "WHERE sid=?", expirationTime, sid);
      return true;
    }
    return false;
  }

  @Override
  public boolean isExisting(String sid) {
    Integer count;
    count = dataStore.executeCount("SELECT count(*) FROM bank.session where sid = '" + sid + "';");
    return count != 0;
  }

  @Override
  public void create(Integer userId, String sid) {
    dataStore.executeQery("INSERT into bank.session(user_fk,sid,expiration_time) values (?,?,?);",
            userId, sid, System.currentTimeMillis()+ConfigurationProperties.get("sessionRefreshRate")); //TODO think about adding sesTime so can escape refresh after create
  }

  @Override
  public void delete(String sid) {
    dataStore.executeQery("DELETE FROM bank.session WHERE sid=?;", sid);
  }

  @Override
  public Integer count() {
    return dataStore.executeCount("SELECT count(*) FROM bank.session;");
  }


}
