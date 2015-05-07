package core;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public interface UserRepository {

  boolean register(String email, String password);

  boolean isExisting(String email);

  boolean isExisting(String email, String password);

  User getByEmail(String email);

  AuthorizationResult authorize(String email, String password);

  CurrentUser getBySid(String sid);
}
