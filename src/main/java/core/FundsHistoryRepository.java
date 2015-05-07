package core;

import java.util.List;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public interface FundsHistoryRepository {

    List<OperationHistory> getHistoryByPages(Integer userId, Integer start, Integer end);

    Integer countRecords(Integer userId);

}
