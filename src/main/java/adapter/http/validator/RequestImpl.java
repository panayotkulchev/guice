package adapter.http.validator;

import java.util.Map;

/**
 * Created by Panayot Kulchev on 15-4-2.
 * e-mail: panayotkulchev@gmail.com
 * happy coding ...
 */

public class RequestImpl implements Request {

  private Map<String, String[]> map;

  public RequestImpl(Map<String, String[]> map) {
    this.map = map;
  }

  @Override
  public String param(String name) {
    return map.get(name)[0];
  }

}
