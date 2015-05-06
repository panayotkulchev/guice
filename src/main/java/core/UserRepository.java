package core;

/**
 * Created by Panayot Kulchev on 15-3-31.
 * e-mail: panayotkulchev@gmail.com
 * happy coding ...
 */

public interface UserRepository {

  boolean register(String email, String password);

  boolean isExisting(String email);

  boolean isExisting(String email, String password);

  User getByEmail(String email);

  AuthorizationResult authorize(String email, String password);

  CurrentUser getBySid(String sid);
}
