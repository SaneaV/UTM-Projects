import java.util.concurrent.Semaphore;

/*
[1]->|
     |->[3]
[2]->|
 */

public class Variant_0 {

  private static final String RESET = "\u001B[0m";

  private static final String RED = "\u001B[31m";
  private static final String GREEN = "\u001B[32m";
  private static final String BLUE = "\u001B[34m";

  private static final Semaphore SEMAPHORE_1 = new Semaphore(0);
  private static final Semaphore SEMAPHORE_2 = new Semaphore(0);

  public static void main(String[] args) {
    Thread t1 = new Thread(() -> {
      System.out.println(RED + "Активность 1 начинается" + RESET);
      try {
        Thread.sleep(1000);
      } catch (InterruptedException ignored) {
      }
      System.out.println(RED + "Активность 1 завершена" + RESET);
      SEMAPHORE_1.release();
    });

    Thread t2 = new Thread(() -> {
      System.out.println(GREEN + "Активность 2 начинается" + RESET);
      try {
        Thread.sleep(1500);
      } catch (InterruptedException ignored) {
      }
      System.out.println(GREEN + "Активность 2 завершена" + RESET);
      SEMAPHORE_2.release();
    });

    Thread t3 = new Thread(() -> {
      try {
        SEMAPHORE_1.acquire();
        SEMAPHORE_2.acquire();
      } catch (InterruptedException ignored) {
      }
      System.out.println(BLUE + "Активность 3 запускается после 1 и 2" + RESET);
    });

    t1.start();
    t2.start();
    t3.start();
  }
}
