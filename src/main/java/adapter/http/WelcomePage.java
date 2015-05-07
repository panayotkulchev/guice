package adapter.http;

import com.google.inject.Inject;
import com.google.sitebricks.Show;
import core.SessionRepository;

/**
 * Created by Panayot Kulchev on 15-4-27.
 * e-mail: panayotkulchev@gmail.com
 * happy coding ...
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
