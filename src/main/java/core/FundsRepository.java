package core;

import java.util.List;

/**
 * Created by Panayot Kulchev on 15-4-6.
 * e-mail: panayotkulchev@gmail.com
 * happy coding ...
 */
public interface FundsRepository {

  Integer getAmount(Integer userPk);

  void deposit(Integer userPk, Integer amount);

  boolean withdraw(Integer userPk, Integer amount);

  void createAccount(Integer id);

  List<OperationHistory> getWithdrawHistory(Integer userPk);

  List<OperationHistory> getDepositHistory(Integer userPk);

  List<OperationHistory> getAllHistory(Integer userPk);

  List<OperationHistory> getHistoryByPages(Integer userPk, Integer start, Integer end);

  Integer countRecords(Integer userId);
}
