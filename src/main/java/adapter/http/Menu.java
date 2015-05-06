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

  public Integer onlineUsersNumber;
  public Integer userAmount;
  public String userEmail;


  @Inject
  public Menu(SessionRepository sessionRepository,
              CurrentUser currentUser) {

    onlineUsersNumber = sessionRepository.count();
    userEmail=currentUser.email;
    userAmount=currentUser.amount;

  }
}
