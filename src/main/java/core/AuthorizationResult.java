package core;

/**
 * Created by Panayot Kulchev on 15-4-17.
 * e-mail: panayotkulchev@gmail.com
 * happy coding ...
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
