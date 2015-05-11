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
    private final UserSession userSession;

    @Inject
    public LoginPage(UserRepository userRepository,
                     RegexValidator validator,
                     UserSession userSession) {

        this.userRepository = userRepository;
        this.validator = validator;
        this.userSession = userSession;
    }

    @Post
    public String login() {

        List<String> errorList = validator.validateRequestParams();

        if (errorList.size() != 0) {
            return "/login?message=" + errorList.get(0);
        }

        AuthorizationResult authorizationResult = userRepository.authorize(email, password);

        if (!authorizationResult.isSuccessful()) {
            return "/login?message=User do not exist!";
        }

        userSession.create(authorizationResult.user.id);
        return "/welcome";

    }
}
