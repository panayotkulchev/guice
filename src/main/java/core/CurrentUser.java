package core;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
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
