import static java.lang.System.in;

import java.util.Arrays;
import java.util.Scanner;

public class Application {

  // Максимальное число итераций, которые выполнит метод.
  public static final int MAX_ITERATIONS = 20;
  private static final Scanner scanner = new Scanner(in);

  private static final double[][] equation = new double[30][30];
  private static int rows;
  private static int columns;

  public static void main(String[] args) {
    boolean exitProgram = false;
    int choice;

    do {
      System.out.println("1. Выйти из программы");
      System.out.println("2. Ввести матрицу с клавиатуры.");
      System.out.println("3. Вывести матрицу на экран.");
      System.out.println("4. Решение системы линейных алгебраических уравнений (Метод Гаусса — Жордана).");
      System.out.println("5. Решение системы линейных алгебраических уравнений (Якоби).");

      System.out.print("Сделайте ваш выбор: ");
      choice = scanner.nextInt();

      switch (choice) {
        case 1: {
          exitProgram = true;
          break;
        }
        case 2: {
          inputArray();
          break;
        }
        case 3: {
          printArray();
          break;
        }
        case 4: {
          double[] solution = findRoot();
          System.out.println("Решение СЛАУ");
          arrayPrint(solution, "X", 1);
          break;
        }
        case 5: {
          System.out.print("Введите значение эпсилон: ");
          double eps = scanner.nextDouble();
          double[] solution = findRoot(eps);
          System.out.println("Решение СЛАУ");
          arrayPrint(solution, "X", 1);
          break;
        }
        default: {
          System.out.println("Такого элемента в меню не найдено.");
        }
      }

    } while (!exitProgram);
  }

  /**
   * Пример матрицы с единственным решением (совместна):
   * 2.000    -1.000   5.000    4.000
   * 3.000    -1.000   5.000    0.000
   * 5.000    -2.000   3.000    2.000
   *
   * Пример матрицы без решения (несовместная):
   * 4.000    -3.000   2.000    -1.000   8.000
   * 3.000    -2.000   1.000    -3.000   7.000
   * 5.000    -3.000   1.000    -8.000   1.000
   *
   * Пример матрицы с множеством решений (неопределённой)
   * 1.000    2.000    4.000    -1.000   3.000
   * 2.000    7.000    10.000   3.000    10.000
   * 3.000    11.000   15.000   1.000    11.000
   * 2.000    9.000    11.000   2.000    8.000
   */

  private static double[] findRoot() {
    double k;
    final double[][] tempEquation = new double[rows][columns];
    for (int i = 0; i < rows; i++) {
      tempEquation[i] = equation[i].clone();
    }

    //прямой ход
    for (int diagonal = 0; diagonal < rows - 1; diagonal++) {
      for (int row = diagonal + 1; row < rows; row++) {
        k = tempEquation[row][diagonal] / tempEquation[diagonal][diagonal];
        for (int column = 0; column < columns; column++) {
          tempEquation[row][column] = tempEquation[row][column] - tempEquation[diagonal][column] * k;
        }
      }
    }

    //обратный ход
    for (int diagonal = rows - 1; diagonal > 0; diagonal--) {
      for (int row = 0; row < diagonal; row++) {
        k = tempEquation[row][diagonal] / tempEquation[diagonal][diagonal];
        for (int column = 0; column < rows + 1; column++) {
          tempEquation[row][column] = tempEquation[row][column] - tempEquation[diagonal][column] * k;
        }
      }
    }

    //привести главную диагональ к 1
    for (int i = 0; i < rows; i++) {
      k = 1 / tempEquation[i][i];
      for (int j = 0; j <= rows; j++) {
        tempEquation[i][j] = tempEquation[i][j] * k;
      }
    }

    //вернуть результат Х
    double[] x = new double[rows];
    for (int i = 0; i < rows; i++) {
      x[i] = tempEquation[i][rows];
    }
    printArray(tempEquation);
    return x;
  }

  private static void inputArray() {
    System.out.print("\n\nВведите количество строк: ");
    rows = scanner.nextInt();
    System.out.print("Введите количество столбцов: ");
    columns = scanner.nextInt();
    System.out.println("\n\n");

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        if (j < columns - 1) {
          System.out.print("Введите коэффициент при x" + (j + 1) + ": ");
        } else {
          System.out.print("Введите свободный член уравнения b" + (j + 1) + ": ");
        }
        equation[i][j] = scanner.nextDouble();
      }
      System.out.println();
    }
    space();
  }

  private static void printArray() {
    System.out.println("\n\n");
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        System.out.printf("%-8.3f ", equation[i][j]);
      }
      System.out.println();
    }
    space();
  }

  private static void printArray(double[][] equation) {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        System.out.printf("%-8.3f ", equation[i][j]);
      }
      System.out.println();
    }
    space();
  }

  private static void arrayPrint(double[] slau, String name, int colCount) {
    if (!systemCompatible(slau)) {
      System.out.println("Система несовместна (не имеет ни одного решения) или неопределенна (имеет множество решений)\n\n");
    } else {
      int currentCol = 0;
      for (int i = 0; i < slau.length; i++) {
        System.out.printf("%s[% -3d]= %-8.3f ", name, i, slau[i]);
        currentCol++;
        if (currentCol % colCount == 0) {
          System.out.println();
        }
      }
      space();
    }
  }

  /**
   * Пример правильной матрицы для проверки.
   * 10.000   1.000    -1.000   11.000
   * 1.000    10.000   -1.000   10.000
   * -1.000   1.000    10.000   10.000
   */

  //Решение СЛАУ методом простых итераций (методом Якоби).
  public static double[] findRoot(double epsilon) {
    System.out.println("Метод Якоби");
    final double[][] tempEquation = new double[rows][columns];
    for (int i = 0; i < rows; i++) {
      tempEquation[i] = equation[i].clone();
    }

    int iterations = 0;

    System.out.println("Исходная матрица");
    printArray();

    if (!makeDominant(tempEquation)) {
      System.out.println("Метод не гарантирует сходимость этой СЛАУ");
    }

    System.out.println("Трансформированная матрица");
    printArray(tempEquation);

    double[] currX = new double[rows]; // текущее приближение
    double[] prevX = new double[rows]; // предыдущее приближение
    Arrays.fill(currX, 0);
    Arrays.fill(prevX, 0);

    while (true) {
      for (int i = 0; i < rows; i++) {
        double sum = tempEquation[i][rows];

        for (int j = 0; j < rows; j++) {
          if (j != i) {
            sum -= tempEquation[i][j] * prevX[j];
          }
        }

        // Обновить текущие значения Х для использования на следующей итерации.
        currX[i] = 1 / tempEquation[i][i] * sum;
      }

      System.out.print("X_" + iterations + " = {");
      for (int i = 0; i < rows; i++) {
        System.out.print(currX[i] + " ");
      }
      System.out.println("}");

      iterations++;
      if (iterations == 1) {
        continue;
      }

      boolean stop = true;
      for (int i = 0; i < rows; i++) {
        if (Math.abs(currX[i] - prevX[i]) > epsilon) {
          stop = false;
          break;
        }
      }

      if (stop || iterations >= MAX_ITERATIONS) {
        break;
      }
      prevX = currX.clone();
    }

    return currX;
  }

  // Рекурсивный метод для приведения матрицы к диагональному преобладанию.
  private static boolean transformToDominant(double[][] A, int row, boolean[] visited,
      int[] rows) {

    int n = A.length;

    if (row == A.length) {
      double[][] T = new double[n][n + 1];
      for (int i = 0; i < rows.length; i++) {
        System.arraycopy(A[rows[i]], 0, T[i], 0, n + 1);
      }

      return true;
    }

    for (int i = 0; i < n; i++) {
      if (visited[i]) {
        continue;
      }

      double sum = 0;

      for (int j = 0; j < n; j++) {
        sum += Math.abs(A[i][j]);
      }

      if (2 * Math.abs(A[i][row]) > sum) { // есть ли диагональное преобладание?
        visited[i] = true;
        rows[row] = i;

        if (transformToDominant(A, row + 1, visited, rows)) {
          return true;
        }

        visited[i] = false;
      }
    }

    return false;
  }

  //Возвращает true, если можно преобразовать матрицу matrix в состояние диагонального
  //преобладания, false в противном случае.
  private static boolean makeDominant(double[][] matrix) {

    boolean[] visited = new boolean[matrix.length];
    int[] rows = new int[matrix.length];

    Arrays.fill(visited, false);

    return transformToDominant(matrix, 0, visited, rows);
  }

  private static void space() {
    System.out.println("\n\n");
  }

  private static boolean systemCompatible(double[] slau) {
    long count = Arrays.stream(slau)
        .filter(Double::isNaN)
        .count();

    return count == 0;
  }
}