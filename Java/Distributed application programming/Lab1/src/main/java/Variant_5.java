import java.util.concurrent.Semaphore;

/*
     |->[2]----|->[4]
[1]->|   ^     |
     |   |     |->[5]
     |->[3]
 */

public class Variant_5 {

  private static final String RESET = "\u001B[0m";

  private static final String RED = "\u001B[31m";
  private static final String GREEN = "\u001B[32m";
  private static final String YELLOW = "\u001B[33m";
  private static final String BLUE = "\u001B[34m";
  private static final String PURPLE = "\u001B[35m";

  private static final Semaphore SEMAPHORE_1 = new Semaphore(0);
  private static final Semaphore SEMAPHORE_2 = new Semaphore(0);
  private static final Semaphore SEMAPHORE_3 = new Semaphore(0);

  public static void main(String[] args) {
    Thread t1 = new Thread(() -> {
      try {
        System.out.println(RED + "Активность 1 запускается" + RESET);
        Thread.sleep(1000);
      } catch (InterruptedException ignored) {
      }
      System.out.println(RED + "Активность 1 завершена" + RESET);
      SEMAPHORE_1.release(2);
    });

    Thread t2 = new Thread(() -> {
      try {
        SEMAPHORE_1.acquire();
        SEMAPHORE_2.acquire();
        System.out.println(GREEN + "Активность 2 запускается после 1 и 3" + RESET);
        Thread.sleep(1000);
      } catch (InterruptedException ignored) {
      }
      System.out.println(GREEN + "Активность 2 завершена после 1 и 3" + RESET);
      SEMAPHORE_3.release(2);
    });

    Thread t3 = new Thread(() -> {
      try {
        SEMAPHORE_1.acquire();
        System.out.println(YELLOW + "Активность 3 запускается после 1" + RESET);
        Thread.sleep(1000);
      } catch (InterruptedException ignored) {
      }
      System.out.println(YELLOW + "Активность 3 завершена после 1" + RESET);
      SEMAPHORE_2.release(2);
    });

    Thread t4 = new Thread(() -> {
      try {
        SEMAPHORE_3.acquire();
        System.out.println(BLUE + "Активность 4 запускается после 2" + RESET);
        Thread.sleep(1000);
      } catch (InterruptedException ignored) {
      }
      System.out.println(BLUE + "Активность 4 завершена после 2" + RESET);
    });

    Thread t5 = new Thread(() -> {
      try {
        SEMAPHORE_3.acquire();
        System.out.println(PURPLE + "Активность 5 запускается после 2" + RESET);
        Thread.sleep(1000);
      } catch (InterruptedException ignored) {
      }
      System.out.println(PURPLE + "Активность 5 завершена после 2" + RESET);
    });

    t1.start();
    t2.start();
    t3.start();
    t4.start();
    t5.start();
  }
}
