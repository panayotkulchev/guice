package adapter.http;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.sitebricks.Show;
import com.google.sitebricks.rendering.EmbedAs;
import core.*;

/**
 * Created by Panayot Kulchev on 15-4-29.
 * e-mail: panayotkulchev@gmail.com
 * happy coding ...
 */

@Show("menu.html")
@EmbedAs("Menu")
public class Menu {

  private Integer userAmount;
  private String userEmail;
  private final CurrentUser currentUser;


  @Inject
  public Menu(CurrentUser currentUser) {
    this.currentUser = currentUser;
  }

  public Integer getUserAmount() {
    return currentUser.amount;
  }

  public String getUserEmail() {
    return currentUser.email;
  }
}
