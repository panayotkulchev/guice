package adapter.http;

import adapter.http.validator.RegexValidator;
import com.google.inject.Inject;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Post;
import core.FundsRepository;
import core.UserRepository;

import java.util.List;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

@At("/register")
@Show("register.html")
public class RegisterPage {

    public String email;
    public String password;
    public String message;

    private final FundsRepository fundsRepository;
    private final RegexValidator validator;
    private final UserRepository userRepository;


    @Inject
    public RegisterPage(UserRepository userRepository,
                        FundsRepository fundsRepository,
                        RegexValidator validator) {

        this.userRepository = userRepository;
        this.fundsRepository = fundsRepository;
        this.validator = validator;
    }


    @Post
    private String register() {

        List<String> errorList = validator.validate();

        if (errorList.size() != 0) {
            return "/register?message=" + errorList.get(0);
        }

        if (!userRepository.isExisting(email)) {
            userRepository.registerIfNotRegistered(email, password);
        } else {
            return "/register?message=Email is already occupied!";
        }

        fundsRepository.createAccount(userRepository.getByEmail(email).id);

        return "/login?message=Congratulation! Now you can log in to the system.";
    }

}
