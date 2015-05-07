package adapter.http;

import adapter.db.*;
import com.google.inject.Inject;
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
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
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
      Integer sessionRefreshRate = ConfigurationProperites.get("sessionRefreshRate");
      cookie.setMaxAge(sessionRefreshRate / 1000);
      response.addCookie(cookie);

      sessionRepository.refresh(sid, System.currentTimeMillis() + sessionRefreshRate);
      response.sendRedirect("/welcome");

    }

  }

  public void destroy() {
  }

}
