package adapter.http.validator;

/**
 * Created by Panayot Kulchev on 15-4-2.
 * e-mail: panayotkulchev@gmail.com
 * happy coding ...
 */


public class ValidationRule implements Rule {

    private String name;
    private String regex;
    private String errorMessage;


    public ValidationRule(String name, String errorMessage, String regex) {
        this.name = name;
        this.regex = regex;
        this.errorMessage = errorMessage;
    }

    @Override
    public String name() {
        return name;
    }


    @Override
    public boolean isValid(String value) {
        if (value.matches(regex)) return true;
        return false;
    }

    @Override
    public String errorMessage() {
        return errorMessage;
    }

}