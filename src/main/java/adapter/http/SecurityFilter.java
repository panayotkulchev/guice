package adapter.http;

import adapter.db.ConfigurationProperties;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import core.SessionRepository;
import core.SidFetcher;

import javax.servlet.*;
import javax.servlet.http.Cookie;
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
    private final SidFetcher sidFetcher;
    private final ConfigurationProperties properties;

    @Inject
    public SecurityFilter(SessionRepository sessionRepository, SidFetcher sidFetcher, ConfigurationProperties properties) {
        this.sessionRepository = sessionRepository;
        this.sidFetcher = sidFetcher;
        this.properties = properties;
    }

    public void init(FilterConfig config) throws ServletException {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        sessionRepository.cleanExpired();

        HttpServletResponse response = (HttpServletResponse) resp;

        String sid = sidFetcher.fetch();

        if (sid == null || !sessionRepository.isExisting(sid)) {
            response.sendRedirect("/login?message=Session expired. Please login!");
            return;
        }

        Cookie cookie = new Cookie("sid", sid);

        Integer sessionRefreshRate = properties.get("sessionRefreshRate");
        cookie.setMaxAge(sessionRefreshRate / 1000);
        response.addCookie(cookie);

        sessionRepository.refresh(sid, System.currentTimeMillis() + sessionRefreshRate);

        chain.doFilter(req, resp);

    }

    public void destroy() {
    }
}
