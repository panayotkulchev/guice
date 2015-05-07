package adapter.http.validator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public class RegexValidator {

    private List<Rule> rules;

    public RegexValidator(List<Rule> rules) {
        this.rules = rules;
    }


    public List<String> validate(Request request) {
        List<String> errorList = new ArrayList<String>() {{
        }};

        for (Rule each : rules) {

            String value = request.param(each.name());

            if (!each.isValid(value)) {
                errorList.add(each.errorMessage());
            }
        }
        return errorList;
    }

}
