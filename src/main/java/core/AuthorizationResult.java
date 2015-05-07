package core;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public class AuthorizationResult {

  public final User user;
  public final boolean successful;

  public AuthorizationResult(User user, boolean successful) {
    this.user = user;
    this.successful = successful;
  }

  public User getUser() {
    return user;
  }

  public boolean isSuccessful() {
    return successful;
  }
}
