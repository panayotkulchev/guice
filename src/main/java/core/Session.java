package core;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public class Session {

  public final String sid;
  public final Integer userId;
  public final Long expirationTime;

  public Session(String sid,Integer userId, Long expirationTime) {
    this.sid = sid;
    this.userId = userId;
    this.expirationTime = expirationTime;
  }

}
