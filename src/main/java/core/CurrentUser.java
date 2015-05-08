package core;

import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public class CurrentUser {

  public final String email;
  public final Integer amount;
  private final Provider<HttpServletRequest> requestProvider;
  public final Integer id;

  @Inject
  public CurrentUser(Integer id, String email, Integer amount, Provider<HttpServletRequest> requestProvider) {
    this.id = id;
    this.email = email;
    this.amount = amount;
    this.requestProvider = requestProvider;
  }

  public String getSid() {

    HttpServletRequest httpServletRequest = requestProvider.get();
    Cookie[] cookies = httpServletRequest.getCookies();
    if (cookies == null) {
      return null;
    }

    for (Cookie each : cookies) {
      if (each.getName().equalsIgnoreCase("sid")) {
        return each.getValue();
      }
    }

    return null;
  }
}
