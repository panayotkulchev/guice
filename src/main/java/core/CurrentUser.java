package core;

/**
 * Created by Panayot Kulchev on 15-4-30.
 * e-mail: panayotkulchev@gmail.com
 * happy coding ...
 */
public class CurrentUser {

  public final String email;
  public final Integer amount;
  public final Integer id;

  public CurrentUser(Integer id, String email, Integer amount) {
    this.id = id;
    this.email = email;
    this.amount = amount;
  }
}
