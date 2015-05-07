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

@At("/deposit")
@Show("deposit.html")
public class DepositPage {

  public String message;
  public String amount;

  private final Provider<CurrentUser> currentUser;
  private final FundsRepository fundsRepository;


  @Inject
  public DepositPage(Provider<CurrentUser> currentUser,
                     FundsRepository fundsRepository) {

    this.currentUser = currentUser;
    this.fundsRepository = fundsRepository;
  }


  @Post
  public String deposit() {

    Integer amountToDeposit = Integer.parseInt(amount);

    fundsRepository.deposit(currentUser.get().id, amountToDeposit);

    return "/deposit?message=Deposit successful!";
  }

}
