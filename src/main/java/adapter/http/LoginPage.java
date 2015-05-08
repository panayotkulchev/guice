package adapter.http;

import adapter.http.validator.RegexValidator;
import com.google.inject.Inject;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Post;
import core.AuthorizationResult;
import core.UserRepository;

import java.util.List;

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

    private final UserRepository userRepository;
    private final RegexValidator validator;
    private final SessionManager sessionManager;

    @Inject
    public LoginPage(UserRepository userRepository,
                     RegexValidator validator,
                     SessionManager sessionManager) {

        this.userRepository = userRepository;
        this.validator = validator;
        this.sessionManager = sessionManager;
    }

    @Post
    public String login() {

        List<String> errorList = validator.validate();

        if (errorList.size() != 0) {
            return "/login?message=" + errorList.get(0);
        }

        AuthorizationResult authorizationResult = userRepository.authorize(email, password);

        if (!authorizationResult.isSuccessful()) {
            return "/login?message=User do not exist!";
        }

        sessionManager.createSession(authorizationResult.user.id);
        return "/welcome";

    }
}
