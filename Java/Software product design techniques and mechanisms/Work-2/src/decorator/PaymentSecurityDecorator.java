package decorator;

import adapter.PaymentSystem;

public class PaymentSecurityDecorator implements PaymentSystem {

  private final PaymentSystem paymentSystem;

  public PaymentSecurityDecorator(PaymentSystem paymentSystem) {
    this.paymentSystem = paymentSystem;
  }

  @Override
  public void pay(double amount) {
    if (amount > 1000) {
      System.out.println("Security check: Large payment detected. Verifying...");
    }
    paymentSystem.pay(amount);
  }
}
