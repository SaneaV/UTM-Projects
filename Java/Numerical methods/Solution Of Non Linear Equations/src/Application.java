import static java.lang.System.in;
import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.CEILING;

import java.math.BigDecimal;
import java.util.Scanner;

public class Application {

  private static BigDecimal a;
  private static BigDecimal b;
  private static BigDecimal eps;

  public static void main(String[] args) {
    final Scanner scanner = new Scanner(in);
    boolean exitProgram = false;
    int choice;
    do {
      System.out.println("-----------------------Меню-----------------------");
      System.out.println("1. Ввести значения a, b, eps.");
      System.out.println("2. Показать значения a, b, eps.");
      System.out.println("3. Вычислить методом Хорд.");
      System.out.println("4. Вычислить модифицированным методом Ньютона.");
      System.out.println("5. Вычислить комбинированным методом (Хорд и Касательных).");
      System.out.println("6. Выход из программы.");
      System.out.println("-----------------------Меню-----------------------");

      System.out.print("Ваш выбор: ");
      choice = scanner.nextInt();
      clearScreen();

      switch (choice) {
        case 1: {
          inputVariables();
          break;
        }
        case 2: {
          System.out.println("Значения, введённые в программу:");
          System.out.println("a = " + a);
          System.out.println("b = " + b);
          System.out.println("eps = " + eps);
          System.out.println("\n\n");
          break;
        }
        case 3: {
          chord1(a, b, eps);
          break;
        }
        case 4: {
          modifiedNewtonMethod(a, b, eps);
          break;
        }
        case 5: {
          combinedMethod(a, b, eps);
          break;
        }
        case 6: {
          exitProgram = true;
          break;
        }
        default: {
          System.out.println("В меню нет такого пункта, повторите ввод.");
        }
      }
    } while (!exitProgram);

    System.out.println("Вы завершили работу программы. Удачи!");
  }

  private static void inputVariables() {
    final Scanner scanner = new Scanner(in);
    System.out.println("---------Ввод данных--------");
    System.out.print("Введите значение a: ");
    a = scanner.nextBigDecimal();
    System.out.print("Введите значение b: ");
    b = scanner.nextBigDecimal();
    System.out.print("Введите эпсилон: ");
    eps = scanner.nextBigDecimal();

    if (!rootExists()) {
      System.out.println("\nНа отрезке не существует корня. Повторите ввод.");
      inputVariables();
      return;
    }

    if (a.compareTo(b) >= 0) {
      System.out.println("\n a >= b. Повторите ввод.");
      inputVariables();
      return;
    }

    System.out.println("\nДанные успешно введены.");
    System.out.println("-----Конец ввода данных-----\n\n");
  }

  //Изначальное уравнение
  private static BigDecimal f(BigDecimal x) {
    final BigDecimal firstPart = valueOf(2).multiply(x.pow(3));
    final BigDecimal secondPart = valueOf(3).multiply(x.pow(2));
    final BigDecimal thirdPart = valueOf(-4).multiply(x);
    final BigDecimal fourthPart = valueOf(2);
    return firstPart.add(secondPart).add(thirdPart).add(fourthPart).setScale(25, CEILING);
  }

  //Дифференциальное уравнение
  private static BigDecimal fp1(BigDecimal x) {
    final BigDecimal firstPart = valueOf(6).multiply(x.pow(2));
    final BigDecimal secondPart = valueOf(6).multiply(x);
    final BigDecimal thirdPart = valueOf(4);
    return firstPart.add(secondPart).subtract(thirdPart).setScale(25, CEILING);
  }

  //Дифференциальное уравнение второго порядка
  private static BigDecimal fp2(BigDecimal x) {
    final BigDecimal firstPart = valueOf(12).multiply(x);
    final BigDecimal secondPart = valueOf(6);
    return firstPart.add(secondPart).setScale(25, CEILING);
  }

  //Проверка на наличие корня на отрезке
  private static boolean rootExists() {
    return f(a).multiply(f(b)).compareTo(valueOf(0)) < 0;
  }

  //Метод касательных (Метод Ньютона)
  private static void modifiedNewtonMethod(BigDecimal a, BigDecimal b, BigDecimal eps) {
    System.out.println("Ищем корень (нуль) заданной функции, используя модифицированный метод Ньютона.");
    BigDecimal x;
    if (f(a).multiply(fp2((a))).compareTo(valueOf(0)) == 1) {
      System.out.println("Так как f(a) * f''(a) > 0, x = a = " + a);
      x = a;
    } else {
      System.out.println("Так как f(b) * f''(b) > 0, x = b = " + b);
      x = b;
    }

    int iteration = 1;
    BigDecimal precision;
    final BigDecimal constantValue = fp1(x);

    do {
      iteration++;
      precision = f(x).divide(constantValue, CEILING);
      x = x.subtract(precision);
    } while (f(x).abs().compareTo(eps) >= 0);
    System.out.println("+------+------------------+----------------------+");
    System.out.println(
        "Финальный ответ: x = " + x.setScale(5, CEILING) + ", f(x) = " + f(x).setScale(5, CEILING) + ", количество итераций = "
            + iteration + "\n\n");
  }

  //Комбинированный метод
  private static void combinedMethod(BigDecimal a, BigDecimal b, BigDecimal eps) {
    System.out.println("Ищем корень (нуль) заданной функции, используя комбинированный метод.");

    int iteration = 0;
    BigDecimal c;
    BigDecimal x;

    do {
      iteration++;
      c = a.subtract(f(a).multiply(b).divide(f(b).subtract(f(a)), CEILING));
      if ((f(a).multiply(fp2(a)).compareTo(valueOf(0)) > 0)) {
        a = a.subtract(f(a).divide(fp1(a), CEILING));
        b = c;
      } else {
        a = c;
        b = b.subtract(f(b).divide(fp1(b), CEILING));
      }

      x = (a.add(b)).divide(valueOf(2), CEILING);
    } while (f(x).abs().compareTo(eps) >= 0);
    System.out.println(
        "Финальный ответ: x = " + x.setScale(5, CEILING) + ", f(x) = " + f(x).setScale(7, CEILING) + ", количество итераций = "
            + iteration + "\n\n");
  }

  private static void chord1(BigDecimal a, BigDecimal b, BigDecimal eps) {
    System.out.println("Ищем корень (нуль) заданной функции, используя метод Хорд (1 формула).");

    int iteration = 0;
    BigDecimal c;
    BigDecimal x;

    do {
      iteration++;
      c = a.subtract(f(a).multiply(b.subtract(a)).divide(f(b).subtract(f(a)), CEILING));
      if (f(a).multiply(f(c)).compareTo(valueOf(0)) <= 0) {
        x = b;
        b = c;
      } else {
        x = a;
        a = c;
      }

    } while (f(c).abs().compareTo(eps) >= 0);

    System.out.println(
        "Финальный ответ: x = " + x.setScale(5, CEILING) + ", f(x) = " + f(x).setScale(5, CEILING) + ", количество итераций = "
            + iteration + "\n\n");
  }

  private static void clearScreen() {
    for (int clear = 0; clear < 1000; clear++) {
      System.out.println("\b");
    }
  }
}
