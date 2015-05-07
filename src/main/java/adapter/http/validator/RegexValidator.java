package adapter.http.validator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Panayot Kulchev on 15-4-2.
 * e-mail: panayotkulchev@gmail.com
 * happy coding ...
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
