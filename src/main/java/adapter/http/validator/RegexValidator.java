package adapter.http.validator;

import com.google.inject.Inject;
import core.ValidationRules;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public class RegexValidator {

    private List<Rule> rules;
    private final RequestImpl request;

    @Inject
    public RegexValidator(@ValidationRules List<Rule> rules, RequestImpl request) {
        this.rules = rules;
        this.request = request;
    }


    public List<String> validateRequestParams() {
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
