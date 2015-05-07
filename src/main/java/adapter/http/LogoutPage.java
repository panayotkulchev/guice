package adapter.http;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Get;
import core.SessionRepository;
import core.SidProvider;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Panayot Kulchev on 15-4-28.
 * e-mail: panayotkulchev@gmail.com
 * happy coding ...
 */

@At("/logout")
@Show("logout.html")
public class LogoutPage {

  private final Provider<HttpServletRequest> requestProvider;
  private final Provider<HttpServletResponse> responseProvider;
  private final SessionRepository sessionRepository;

  @Inject
  public LogoutPage(Provider<HttpServletRequest> requestProvider,
                    Provider<HttpServletResponse> responseProvider,
                    SessionRepository sessionRepository) {

    this.requestProvider = requestProvider;
    this.responseProvider = responseProvider;
    this.sessionRepository = sessionRepository;
  }

  @Get
  private String logOut() {

    HttpServletRequest request = requestProvider.get();
    HttpServletResponse response = responseProvider.get();
    String sid = SidProvider.getSid(request);

    if (sid != null) {

      sessionRepository.delete(sid);

      for (Cookie each : request.getCookies()) {
        if (each.getName().equalsIgnoreCase("sid")) {
          each.setMaxAge(0);
          response.addCookie(each);
        }
      }
    }

    return "/login?message=You are logged out!";

  }
}
