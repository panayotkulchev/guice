package adapter.http;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import core.SessionRepository;
import core.SidFetcher;

import javax.servlet.*;
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
    private final SidFetcher sidFetcher;
    private final SessionManager sessionManager;

    @Inject
    public LoginFilter(SessionRepository sessionRepository, SidFetcher sidFetcher, SessionManager sessionManager) {
        this.sessionRepository = sessionRepository;
        this.sidFetcher = sidFetcher;
        this.sessionManager = sessionManager;
    }

    public void init(FilterConfig config) throws ServletException {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletResponse response = (HttpServletResponse) resp;

        String sid = sidFetcher.fetch();

        if (sid == null || !sessionRepository.isExisting(sid)) {
            chain.doFilter(req, resp);

        } else {

            sessionManager.refresh();
            response.sendRedirect("/welcome");
        }
    }

    public void destroy() {
    }

}
