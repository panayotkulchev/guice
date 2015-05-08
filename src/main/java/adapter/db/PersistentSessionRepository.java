package adapter.db;

import com.google.inject.Inject;
import core.SessionRepository;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */
public class PersistentSessionRepository implements SessionRepository {

  private final DataStore dataStore;
  private final ConfigurationProperties properties;

  @Inject
  public PersistentSessionRepository(DataStore dataStore, ConfigurationProperties properties) {
    this.dataStore = dataStore;
    this.properties = properties;
  }


  @Override
  public void refresh(String sid, Long expirationTime) {
      dataStore.executeQuery("UPDATE bank.session SET expiration_time=? " +
              "WHERE sid=?", expirationTime, sid);
    }

  @Override
  public boolean isExisting(String sid) {
    Integer count;
    count = dataStore.executeCount("SELECT count(*) FROM bank.session where sid = '" + sid + "';");
    return count != 0;
  }

  @Override
  public void create(Integer userId, String sid) {
    dataStore.executeQuery("INSERT into bank.session(user_fk,sid,expiration_time) values (?,?,?);",
            userId, sid, System.currentTimeMillis() + properties.get("sessionRefreshRate"));
  }

  @Override
  public void cleanExpired() {
    dataStore.executeQuery("DELETE FROM bank.session WHERE expiration_time<?;", System.currentTimeMillis());
  }

  @Override
  public void delete(String sid) {
    dataStore.executeQuery("DELETE FROM bank.session WHERE sid=?;", sid);
  }

  @Override
  public Integer count() {
    return dataStore.executeCount("SELECT count(*) FROM bank.session;");
  }


}
