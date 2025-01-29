package decorator;

import adapter.PaymentSystem;

public class PaymentLoggerDecorator implements PaymentSystem {

  private final PaymentSystem paymentSystem;

  public PaymentLoggerDecorator(PaymentSystem paymentSystem) {
    this.paymentSystem = paymentSystem;
  }

  @Override
  public void pay(double amount) {
    System.out.println("Logging payment of " + amount);
    paymentSystem.pay(amount);
  }
}
