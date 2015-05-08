package adapter.http;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Get;
import core.SessionRepository;
import core.SidFetcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

@At("/logout")
@Show("logout.html")
public class LogoutPage {

    private final SessionManager sessionManager;

    @Inject
    public LogoutPage(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Get
    private String logOut() {

        sessionManager.delete();

        return "/login?message=You are logged out!";

    }
}
