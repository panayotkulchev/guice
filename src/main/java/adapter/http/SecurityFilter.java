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
public class SecurityFilter implements Filter {

    private final SessionRepository sessionRepository;
    private final SidFetcher sidFetcher;
    private final SessionManager sessionManager;

    @Inject
    public SecurityFilter(SessionRepository sessionRepository,
                          SidFetcher sidFetcher,
                          SessionManager sessionManager) {

        this.sessionRepository = sessionRepository;
        this.sidFetcher = sidFetcher;
        this.sessionManager = sessionManager;
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

        sessionManager.refresh();
        chain.doFilter(req, resp);
    }

    public void destroy() {
    }
}
