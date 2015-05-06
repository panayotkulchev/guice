package adapter.http;

import adapter.db.DataStore;
import adapter.db.JdbcConnectionProvider;
import adapter.db.PersistentSessionRepository;
import adapter.db.SessionProperties;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import core.SessionRepository;
import core.SidProvider;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Panayot Kulchev on 15-4-17.
 * e-mail: panayotkulchev@gmail.com
 * happy coding ...
 */

@Singleton
public class LoginFilter implements Filter {

  private final SessionRepository sessionRepository;

  @Inject
  public LoginFilter(SessionRepository sessionRepository) {
    this.sessionRepository = sessionRepository;
  }

  public void init(FilterConfig config) throws ServletException {
  }

  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) resp;

    String sid = SidProvider.getSid(request);

    if (sid == null || !sessionRepository.isExisting(sid)) {
      chain.doFilter(req, resp);


    } else {
      Cookie cookie = new Cookie("sid", sid);
      cookie.setMaxAge(SessionProperties.get("sessionRefreshRate") / 1000);
      response.addCookie(cookie);

      sessionRepository.refresh(sid, System.currentTimeMillis() + SessionProperties.get("sessionRefreshRate"));
      response.sendRedirect("/welcome");

    }

  }

  public void destroy() {
  }

}
