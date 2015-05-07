package core;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public interface SessionRepository {

  boolean refresh(String sid, Long expirationTime);

  boolean isExisting(String sid);

  void create(Integer userId, String sid);

  void delete(String sid);

  Integer count();

}
