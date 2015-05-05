package adapter.http.validator;

/**
 * Created by Panayot Kulchev on 15-4-2.
 * e-mail: panayotkulchev@gmail.com
 * happy coding ...
 */

public interface Rule {

    String name();

    boolean isValid(String value);

    String errorMessage();

}
