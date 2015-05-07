package adapter.http;

import adapter.db.ConfigurationProperties;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import core.SessionRepository;
import core.SidProvider;

import javax.servlet.*;
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
public class SecurityFilter implements Filter {

    private final SessionRepository sessionRepository;

    @Inject
    public SecurityFilter(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public void init(FilterConfig config) throws ServletException {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String sid = SidProvider.getSid(request);

        if (sid == null || !sessionRepository.isExisting(sid)) {
            response.sendRedirect("/login?message=Session expired. Please login!");

        } else {
            Cookie cookie = new Cookie("sid", sid);
            cookie.setMaxAge(ConfigurationProperties.get("sessionRefreshRate") / 1000);
            response.addCookie(cookie);

            sessionRepository.refresh(sid, System.currentTimeMillis() + ConfigurationProperties.get("sessionRefreshRate"));

            chain.doFilter(req, resp);
        }
    }

    public void destroy() {
    }
}
