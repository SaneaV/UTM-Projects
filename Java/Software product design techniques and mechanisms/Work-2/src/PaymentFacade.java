import adapter.BankTransferAdapter;
import adapter.PayPalAdapter;
import adapter.PaymentSystem;
import adapter.StripeAdapter;
import decorator.PaymentCacheDecorator;
import decorator.PaymentLoggerDecorator;
import decorator.PaymentSecurityDecorator;
import java.util.HashMap;
import java.util.Map;
import paymentsystem.BankTransfer;
import paymentsystem.PayPal;
import paymentsystem.Stripe;

public class PaymentFacade {

  private final Map<String, PaymentSystem> paymentMethods;

  public PaymentFacade() {
    this.paymentMethods = new HashMap<>();
    initializePaymentMethods();
  }

  private void initializePaymentMethods() {
    paymentMethods.put("paypal", new PaymentSecurityDecorator(
        new PaymentLoggerDecorator(
            new PaymentCacheDecorator(
                new PayPalAdapter(new PayPal())
            )
        )
    ));
    paymentMethods.put("stripe", new PaymentSecurityDecorator(
        new PaymentLoggerDecorator(
            new StripeAdapter(new Stripe())
        )
    ));
    paymentMethods.put("banktransfer", new PaymentSecurityDecorator(
        new PaymentLoggerDecorator(
            new BankTransferAdapter(new BankTransfer())
        )
    ));
  }

  public void pay(double amount, String method) {
    PaymentSystem paymentSystem = paymentMethods.get(method.toLowerCase());
    if (paymentSystem == null) {
      throw new IllegalArgumentException("Unsupported payment method: " + method);
    }
    paymentSystem.pay(amount);
  }
}