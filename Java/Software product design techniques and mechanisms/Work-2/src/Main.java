public class Main {
  public static void main(String[] args) {
    PaymentFacade facade = new PaymentFacade();

    // Оплата через PayPal
    facade.pay(1100, "paypal");
    // Вывод:
    // Logging payment of 100.0
    // Security check: Large payment detected. Verifying...
    // Paid 100.0 via paymentsystem.PayPal

    // Оплата через Stripe
    facade.pay(200, "stripe");
    // Вывод:
    // Logging payment of 200.0
    // Security check: Large payment detected. Verifying...
    // Paid 200.0 via paymentsystem.Stripe

    // Оплата через Bank Transfer
    facade.pay(300, "banktransfer");
    // Вывод:
    // Logging payment of 300.0
    // Security check: Large payment detected. Verifying...
    // Transferred 300.0 via Bank Transfer

    // Повторная оплата через PayPal (используется кэш)
    facade.pay(1100, "paypal");
    // Вывод:
    // Retrieving from cache: Payment of 100.0 processed
  }
}