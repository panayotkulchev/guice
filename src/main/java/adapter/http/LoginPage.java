package adapter.http;

import adapter.db.ConfigurationProperites;
import adapter.http.validator.RegexValidator;
import adapter.http.validator.RequestImpl;
import adapter.http.validator.Rule;
import adapter.http.validator.ValidationRule;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Post;
import core.AuthorizationResult;
import core.SessionRepository;
import core.SidProvider;
import core.UserRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

@At("/login")
@Show("login.html")
public class LoginPage {

    public String email;
    public String password;
    public String message;

    private final Provider<HttpServletRequest> requestProvider;
    private final Provider<HttpServletResponse> responseProvider;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    @Inject
    public LoginPage(Provider<HttpServletRequest> requestProvider,
                     Provider<HttpServletResponse> responseProvider,
                     UserRepository userRepository,
                     SessionRepository sessionRepository) {

        this.requestProvider = requestProvider;
        this.responseProvider = responseProvider;
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    @Post
    public String login() {

        List<Rule> rules = Lists.newArrayList();

        rules.add(new ValidationRule("email", "Email is not valid", "^[a-z]{3,30}+$"));
        rules.add(new ValidationRule("password", "Password is not valid", "^[a-z]{3,10}+$"));

        HttpServletRequest request = requestProvider.get();
        HttpServletResponse response = responseProvider.get();

        RegexValidator regexValidator = new RegexValidator(rules);
        RequestImpl req = new RequestImpl(request.getParameterMap());

        List<String> errorList = regexValidator.validate(req);

        if (errorList.size() != 0) {
            return "/login?message=" + errorList.get(0);
        }

        AuthorizationResult authorizationResult = userRepository.authorize(email, password);
        if (!authorizationResult.isSuccessful()) {
            return "/login?message=User do not exist!";
        }


        String sid = SidProvider.getSid(request);

        if (sid == null || !sessionRepository.isExisting(sid)) {
            UUID uuid = new UUID(10, 5);
            String randomValue = "panayotkulchev@gmail.com" + uuid.randomUUID().toString() + "abc";
            sid = sha1(randomValue);
            Cookie cookie = new Cookie("sid", sid);
            cookie.setMaxAge(ConfigurationProperites.get("sessionRefreshRate"));
            response.addCookie(cookie);
            sessionRepository.create(authorizationResult.getUser().id, sid);
        }

        return "/welcome";

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
