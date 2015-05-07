package adapter.http;

import com.google.inject.Inject;
import com.google.sitebricks.Show;
import core.SessionRepository;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

@Show("welcome.html")
public class WelcomePage {

    private Integer onlineUsers;

    private final SessionRepository sessionRepository;


    @Inject
    public WelcomePage(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;

    }

    public Integer getOnlineUsers() {
        return sessionRepository.count();
    }
}
