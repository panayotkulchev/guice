package core;

import java.util.List;

/**
 * Created by Panayot Kulchev on 15-4-6.
 * e-mail: panayotkulchev@gmail.com
 * happy coding ...
 */
public interface FundsRepository {

    void createAccount(Integer id);

    Integer getAmount(Integer userId);

    void deposit(Integer userId, Integer amount);

    boolean withdraw(Integer userId, Integer amount);


}
