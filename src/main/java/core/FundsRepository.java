package core;

import java.util.List;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public interface FundsRepository {

    void createAccount(Integer id);

    Integer getAmount(Integer userId);

    void deposit(Integer userId, Integer amount);

    boolean withdraw(Integer userId, Integer amount);


}
