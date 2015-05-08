package adapter.http;

import com.google.inject.Inject;
import com.google.sitebricks.Show;
import com.google.sitebricks.rendering.EmbedAs;
import core.*;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

@Show("menu.html")
@EmbedAs("Menu")
public class Menu {

//  private Integer userAmount;
//  private String userEmail;

  public CurrentUser user;


  @Inject
  public Menu(CurrentUser user) {
    this.user = user;
  }
//
//  public Integer getUserAmount() {
//    return user.amount;
//  }
//
//  public String getUserEmail() {
//    return user.email;
//  }
}

//@Show("menu.html")
//@EmbedAs("Menu")
//public class Menu {
//
//  public CurrentUser user;
//
//  @Inject
//  public Menu(CurrentUser user) {
//    this.user = user;
//  }
//}