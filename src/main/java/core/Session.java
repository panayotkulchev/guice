package core;

/**
 * Created by Panayot Kulchev on 15-4-7.
 * e-mail: panayotkulchev@gmail.com
 * happy coding ...
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
