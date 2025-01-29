package adapter;

import paymentsystem.PayPal;

public class PayPalAdapter implements PaymentSystem {

  private final PayPal paypal;

  public PayPalAdapter(PayPal paypal) {
    this.paypal = paypal;
  }

  @Override
  public void pay(double amount) {
    paypal.sendPayment(amount);
  }
}
