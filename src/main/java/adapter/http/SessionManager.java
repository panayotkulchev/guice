package adapter.http;

import adapter.db.ConfigurationProperties;
import com.google.inject.Inject;
import com.google.inject.Provider;
import core.SessionRepository;
import core.SidFetcher;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public class SessionManager {

    String sid;

    private final SessionRepository sessionRepository;
    private final SidFetcher sidFetcher;
    private final ConfigurationProperties properties;
    private final Provider<HttpServletResponse> responseProvider;
    private final Provider<HttpServletRequest> requestProvider;

    @Inject
    public SessionManager(SessionRepository sessionRepository,
                          SidFetcher sidFetcher,
                          ConfigurationProperties properties,
                          Provider<HttpServletResponse> responseProvider,
                          Provider<HttpServletRequest> requestProvider) {

        this.sessionRepository = sessionRepository;
        this.sidFetcher = sidFetcher;
        this.properties = properties;
        this.responseProvider = responseProvider;
        this.requestProvider = requestProvider;
    }

    public void createSession(Integer userId) {

        sid = sidFetcher.fetch();

        HttpServletResponse response = responseProvider.get();

        if (sid == null || !sessionRepository.isExisting(sid)) {
            UUID uuid = new UUID(10, 5);
            String randomValue = "panayotkulchev@gmail.com" + uuid.randomUUID().toString() + "abc";
            sid = sha1(randomValue);
            Cookie cookie = new Cookie("sid", sid);
            cookie.setMaxAge(properties.get("sessionRefreshRate"));
            response.addCookie(cookie);
            sessionRepository.create(userId, sid);
        }
    }

    public void refresh(){

        sid = sidFetcher.fetch();
        Cookie cookie = new Cookie("sid", sid);
        HttpServletResponse response = responseProvider.get();
        Integer sessionRefreshRate = properties.get("sessionRefreshRate");
        cookie.setMaxAge(sessionRefreshRate / 1000);
        response.addCookie(cookie);
        sessionRepository.refresh(sid, System.currentTimeMillis() + sessionRefreshRate);
    }

    public void delete(){

        String sid = sidFetcher.fetch();
        HttpServletResponse response = responseProvider.get();
        HttpServletRequest request = requestProvider.get();

        if (sid != null) {

            sessionRepository.delete(sid);

            for (Cookie each : request.getCookies()) {
                if (each.getName().equalsIgnoreCase("sid")) {
                    each.setMaxAge(0);
                    response.addCookie(each);
                }
            }
        }
    }

    static String sha1(String input) {
        MessageDigest mDigest = null;
        try {
            mDigest = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            return "";
        }

        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
}
