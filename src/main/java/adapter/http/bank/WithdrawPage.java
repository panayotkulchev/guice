package adapter.http.bank;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Post;
import core.CurrentUser;
import core.FundsRepository;

/**
 * Created by Panayot Kulchev on 15-4-28.
 * e-mail: panayotkulchev@gmail.com
 * happy coding ...
 */

@At("/withdraw")
@Show("withdraw.html")
public class WithdrawPage {

  public String message;
  public String amount;

  public final Provider<CurrentUser> currentUser;
  private final FundsRepository fundsRepository;

  @Inject
  public WithdrawPage(Provider<CurrentUser> currentUser,
                      FundsRepository fundsRepository) {
    this.fundsRepository = fundsRepository;
    this.currentUser = currentUser;
  }

  @Post
  private String withdraw() {

    Integer amountToDeposit = Integer.parseInt(amount);

    boolean hasSuccess = fundsRepository.withdraw(currentUser.get().id, amountToDeposit);

    if (hasSuccess) {
      return "/withdraw?message=Withdraw successful!";
    }
    return "/withdraw?message=Not enough funds!";


  }


}
