package core;

import java.util.List;

/**
 * Created by Panayot Kulchev on 15-5-7
 * e-mail: panayotkulchev@gmail.com
 * happy codding ...
 */

public interface FundsHistoryRepository {

    List<OperationHistory> getHistoryByPages(Integer userId, Integer start, Integer end);

    Integer countRecords(Integer userId);

}
