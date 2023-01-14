import static java.math.BigDecimal.ROUND_HALF_EVEN;
import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;

public class Application {

  private static long COMPLEXITY;

  public static void main(String[] args) {
    boolean stop = false;
    final Scanner scanner = new Scanner(System.in);
    int numberToFind = 0, choice;

    while (!stop) {
      System.out.println("Choose a way to search for the fibonacci number");
      System.out.println("1. Input number");
      System.out.println("2. Iterative");
      System.out.println("3. Recursive");
      System.out.println("4. Exponentiation of matrices");
      System.out.println("5. Bine formula");
      System.out.println("6. Exit");
      System.out.print("Input your choice: ");

      choice = scanner.nextInt();
      restartComplexity();

      switch (choice) {
        case 1: {
          numberToFind = inputNumber(scanner);
          break;
        }
        case 2: {
          System.out.println("\n\n");
          System.out.println("You have chosen the iterative method");
          System.out.println("Fibbonaci number is : " + iterative(numberToFind));
          System.out.println("Complexity: " + COMPLEXITY);
          break;
        }
        case 3: {
          System.out.println("\n\n");
          System.out.println("You have chosen the recursive method");
          System.out.println("Fibbonaci number is : " + recursive(numberToFind));
          System.out.println("Complexity: " + COMPLEXITY);
          break;
        }
        case 4: {
          System.out.println("\n\n");
          System.out.println("You have chosen the exponentiation of matrices method");
          System.out.println("Fibbonaci number is : " + matrixMethod(numberToFind));
          System.out.println("Complexity: " + COMPLEXITY);
          break;
        }
        case 5: {
          System.out.println("\n\n");
          System.out.println("You have chosen the Bine formula");
          System.out.println("Fibbonaci number is : " + binesFormula(numberToFind));
          System.out.println("Complexity: " + COMPLEXITY);
          break;
        }
        case 6: {
          stop = true;
          break;
        }
      }
      System.out.println("\n\n");
    }

    System.out.println("The program has completed its execution.");
  }

  private static BigInteger iterative(int n) {
    BigInteger a = new BigInteger(String.valueOf(0));
    BigInteger b = new BigInteger(String.valueOf(1));
    BigInteger c;
    increaseComplexity(3);

    if (n == 0) {
      return a;
    }
    increaseComplexity();

    increaseComplexity(2);
    for (int i = 2; i <= n; i++) {
      c = (a.add(b));
      a = b;
      b = c;
      increaseComplexity(6);
    }
    return b;
  }

  public static BigInteger recursive(int n) {
    increaseComplexity();
    if (n <= 1) {
      return BigInteger.valueOf(n);
    } else {
      increaseComplexity(3);
      return recursive(n - 1).add(recursive(n - 2));
    }
  }

  public static BigInteger matrixMethod(int n) {
    BigInteger[][] matrix = {{ONE, ONE}, {ONE, ZERO}};
    if (n == 0) return ZERO;
    matrix = power(matrix, n - 1);
    return matrix[0][0];
  }

  public static BigInteger[][] power(BigInteger[][] matrix, int n) {
    increaseComplexity(4);
    BigInteger[][] result = {{ONE, ZERO}, {ZERO, ONE}};
    while (n > 0) {
      if (n % 2 == 1){
        result = multiply(result, matrix);
        increaseComplexity();
      }
      matrix = multiply(matrix, matrix);
      n /= 2;
    }
    return result;
  }

  public static BigInteger[][] multiply(BigInteger[][] a, BigInteger[][] b) {
    increaseComplexity(16);
    BigInteger[][] result = new BigInteger[2][2];
    result[0][0] = a[0][0].multiply(b[0][0]).add(a[0][1].multiply(b[1][0]));
    result[0][1] = a[0][0].multiply(b[0][1]).add(a[0][1].multiply(b[1][1]));
    result[1][0] = a[1][0].multiply(b[0][0]).add(a[1][1].multiply(b[1][0]));
    result[1][1] = a[1][0].multiply(b[0][1]).add(a[1][1].multiply(b[1][1]));
    return result;
  }

  public static BigInteger binesFormula(int n) {
    increaseComplexity(17);
    double phi = (1 + Math.sqrt(5)) / 2;
    BigDecimal phi_n = BigDecimal.valueOf(Math.pow(phi, n));
    double neg_phi = (1 - Math.sqrt(5)) / 2;
    BigDecimal neg_phi_n = BigDecimal.valueOf(Math.pow(neg_phi, n));
    BigDecimal sqrt5 = BigDecimal.valueOf(Math.sqrt(5));
    BigDecimal result = phi_n.subtract(neg_phi_n);
    return result.divide(sqrt5, ROUND_HALF_EVEN).toBigInteger();
  }

  private static int inputNumber(Scanner scanner) {
    System.out.print("What fibbonacci number do you want to find: ");
    return scanner.nextInt();
  }

  private static void restartComplexity() {
    COMPLEXITY = 0;
  }

  private static void increaseComplexity() {
    increaseComplexity(1);
  }

  private static void increaseComplexity(int i) {
    COMPLEXITY += i;
  }
}
