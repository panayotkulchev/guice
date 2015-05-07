package adapter.http.validator;

import java.util.Map;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
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
