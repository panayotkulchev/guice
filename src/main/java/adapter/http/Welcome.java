package adapter.http;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Get;
import com.google.sitebricks.http.Post;
import core.FundsRepository;
import core.SessionRepository;
import core.SidProvider;
import core.UserRepository;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Panayot Kulchev on 15-4-27.
 * e-mail: panayotkulchev@gmail.com
 * happy coding ...
 */

@Show("welcome.html")
public class Welcome {

  public Integer onlineUsersNumber = 0;

  @Inject
  public Welcome(SessionRepository sessionRepository
) {

    onlineUsersNumber=sessionRepository.count();

  }
}
