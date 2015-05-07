package core;

import java.sql.Timestamp;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public class OperationHistory {

  private Integer amountBefore;
  private Integer amountAfter;
  private Timestamp transactionDate;

  public OperationHistory(Integer amountBefore, Integer amountAfter, Timestamp transactionDate) {

    this.amountBefore = amountBefore;
    this.amountAfter = amountAfter;
    this.transactionDate = transactionDate;
  }

  public Integer getAmountBefore() {
    return amountBefore;
  }

  public Integer getAmountAfter() {
    return amountAfter;
  }

  public Timestamp getTransactionDate() {
    return transactionDate;
  }

  @Override
  public String toString() {
    return transactionDate.toString()+" "+amountBefore+" "+amountAfter;

  }
}
