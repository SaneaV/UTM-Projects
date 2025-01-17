import java.util.Random;
import java.util.Scanner;

public class Application {

  private static final Random RANDOM = new Random();
  private static int[][] GRAPH;
  private static int ROWS;

  public static void main(String[] args) {
    boolean stop = false;
    final Scanner scanner = new Scanner(System.in);
    int choice;

    while (!stop) {
      System.out.println("Menu");
      System.out.println("1. Create happy occasion graph.");
      System.out.println("2. Create unhappy occasion graph.");
      System.out.println("3. Create medium occasion graph.");
      System.out.println("4. Print graph.");
      System.out.println("5. Prim's algorithm");
      System.out.println("6. Kruskal's algorithm");
      System.out.println("7. Exit");
      System.out.print("Input your choice: ");

      choice = scanner.nextInt();

      switch (choice) {
        case 1: {
          System.out.println("\n\n");
          System.out.println("You have chosen: Create happy occasion graph");

          inputRowsAndCols(scanner);
          GRAPH = happyOccasionGraph(ROWS, ROWS);
          break;
        }
        case 2: {
          System.out.println("\n\n");
          System.out.println("You have chosen: Create unhappy occasion graph");

          inputRowsAndCols(scanner);
          GRAPH = unhappyOccasionGraph(ROWS, ROWS);
          break;
        }
        case 3: {
          System.out.println("\n\n");
          System.out.println("You have chosen: Create medium occasion graph");

          inputRowsAndCols(scanner);
          GRAPH = mediumOccasionGraph(ROWS, ROWS);
          break;
        }
        case 4: {
          System.out.println("\n\n");
          System.out.println("You have chosen: Print graph");

          printGraph();
          break;
        }
        case 5: {
          System.out.println("\n\n");
          System.out.println("You have chosen: Prim's algorithm");

          final Prim prim = new Prim(GRAPH, ROWS);
          prim.printMST();
          break;
        }
        case 6: {
          System.out.println("\n\n");
          System.out.println("You have chosen: Kruskal's algorithm");

          final Kruskal mst = new Kruskal(GRAPH, ROWS);
          mst.printMST();
          break;
        }
        case 7: {
          stop = true;
          break;
        }
      }
      System.out.println("\n\n");
    }
    System.out.println("The program has completed its execution.");
  }

  private static int[][] happyOccasionGraph(int rows, int cols) {
    int[][] matrix = getMatrix(rows, cols);
    int weight;

    for (int i = 0; i < rows - 1; i++) {
      weight = RANDOM.nextInt(9) + 1;
      matrix[i][i + 1] = weight;
      matrix[i + 1][i] = weight;
    }
    return matrix;
  }

  private static int[][] unhappyOccasionGraph(int rows, int cols) {
    int[][] matrix = getMatrix(rows, cols);
    int weight;

    for (int i = 0; i < rows - 1; i++) {
      for (int j = 0; j < cols; j++) {
        if (i != j) {
          weight = RANDOM.nextInt(9) + 1;
          matrix[i][j] = weight;
          matrix[j][i] = weight;
        }
      }
    }
    return matrix;
  }

  private static int[][] mediumOccasionGraph(int rows, int cols) {
    int[][] matrix = happyOccasionGraph(rows, cols);
    int weight;

    for (int i = 0; i < rows; i++) {
      int j = RANDOM.nextInt(cols);
      int limit = RANDOM.nextInt(cols);
      for (; j < limit; j++) {
        if (i != j) {
          weight = RANDOM.nextInt(9) + 1;
          matrix[i][j] = weight;
          matrix[j][i] = weight;
        }
      }
    }
    return matrix;
  }

  public static void printGraph() {
    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < ROWS; j++) {
        System.out.print(GRAPH[i][j] + " ");
      }
      System.out.println();
    }
  }

  private static int[][] getMatrix(int rows, int cols) {
    final int[][] matrix = new int[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        matrix[i][j] = 0;
      }
    }
    return matrix;
  }

  private static void inputRowsAndCols(Scanner scanner) {
    System.out.print("Number of rows: ");
    ROWS = scanner.nextInt();
  }
}