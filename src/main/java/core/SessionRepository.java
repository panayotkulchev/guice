package core;

/**
 * Created by Panayot Kulchev on 15-4-3.
 * e-mail: panayotkulchev@gmail.com
 * happy coding ...
 */
public interface SessionRepository {

  boolean refresh(String sid, Long expirationTime);

  boolean isExisting(String sid);

  void create(Integer userId, String sid);

  void delete(String sid);

  Integer count();

}
