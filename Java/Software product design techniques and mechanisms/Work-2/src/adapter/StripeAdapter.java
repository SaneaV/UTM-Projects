package adapter;

import paymentsystem.Stripe;

public class StripeAdapter implements PaymentSystem {

  private final Stripe stripe;

  public StripeAdapter(Stripe stripe) {
    this.stripe = stripe;
  }

  @Override
  public void pay(double amount) {
    stripe.makePayment(amount);
  }
}
