package adapter;

import paymentsystem.BankTransfer;

public class BankTransferAdapter implements PaymentSystem {

  private final BankTransfer bankTransfer;

  public BankTransferAdapter(BankTransfer bankTransfer) {
    this.bankTransfer = bankTransfer;
  }

  @Override
  public void pay(double amount) {
    bankTransfer.transfer(amount);
  }
}
