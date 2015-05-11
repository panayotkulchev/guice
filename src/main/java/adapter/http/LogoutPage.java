package adapter.http;

import com.google.inject.Inject;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Get;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

@At("/logout")
@Show("logout.html")
public class LogoutPage {

    private final UserSession userSession;

    @Inject
    public LogoutPage(UserSession userSession) {
        this.userSession = userSession;
    }

    @Get
    private String logOut() {

        userSession.delete();

        return "/login?message=You are logged out!";

    }
}
