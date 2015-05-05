package core;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Panayot Kulchev on 15-4-17.
 * e-mail: panayotkulchev@gmail.com
 * happy coding ...
 */
public class SidProvider {

  public static String getSid(HttpServletRequest request) {

    Cookie[] cookies = request.getCookies();
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
