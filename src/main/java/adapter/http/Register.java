package adapter.http;

import adapter.http.validator.RegexValidator;
import adapter.http.validator.RequestImpl;
import adapter.http.validator.Rule;
import adapter.http.validator.ValidationRule;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Post;
import core.FundsRepository;
import core.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Panayot Kulchev on 15-4-27.
 * e-mail: panayotkulchev@gmail.com
 * happy coding ...
 */

@At("/register")
@Show("register.html")

public class Register {

  public String email = "";
  public String password = "";
  public String message = "";
  private final FundsRepository fundsRepository;
  private final Provider<HttpServletRequest> requestProvider;

  private List<String> errorList;
  private final UserRepository userRepository;

  @Inject
  public Register(UserRepository userRepository,
                  FundsRepository fundsRepository,
                  Provider<HttpServletRequest> requestProvider) {

    this.userRepository = userRepository;
    this.fundsRepository = fundsRepository;
    this.requestProvider = requestProvider;
  }


  @Post
  private Object doPost() {

    List<Rule> rules = new ArrayList<Rule>();
    rules.add(new ValidationRule("email", "Email is not valid", "^[a-z]{3,30}+$"));
    rules.add(new ValidationRule("password", "Password is not valid", "^[a-z]{3,10}+$"));

    HttpServletRequest request = requestProvider.get();
    RegexValidator regexValidator = new RegexValidator(rules);
    RequestImpl req = new RequestImpl(request.getParameterMap());

    errorList = regexValidator.validate(req);

    if (errorList.size() != 0) {
      return "/register?message=" + errorList.get(0);
    }

    boolean hasSuccessRegistration = userRepository.register(email, password);

    if (!hasSuccessRegistration) {
      return "/register?message=Email is already occupied!";
    }

    fundsRepository.createAccount(userRepository.getByEmail(email).id);

    return "/login?message=Congratulation! Now you can log in to the system.";
  }

}
