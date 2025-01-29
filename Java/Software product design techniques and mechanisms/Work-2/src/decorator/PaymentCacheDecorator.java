package decorator;

import adapter.PaymentSystem;
import java.util.HashMap;
import java.util.Map;

public class PaymentCacheDecorator implements PaymentSystem {

  private final PaymentSystem paymentSystem;
  private final Map<Double, String> cache = new HashMap<>();

  public PaymentCacheDecorator(PaymentSystem paymentSystem) {
    this.paymentSystem = paymentSystem;
  }

  @Override
  public void pay(double amount) {
    if (cache.containsKey(amount)) {
      System.out.println("Retrieving from cache: " + cache.get(amount));
    } else {
      paymentSystem.pay(amount);
      cache.put(amount, "Payment of " + amount + " processed");
    }
  }
}
