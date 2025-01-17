import static java.lang.Integer.MAX_VALUE;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;

public class Application {

  private static final Random RANDOM = new Random();
  private static final int INF = MAX_VALUE;
  private static int[][] GRAPH;
  private static int VERTEX;
  private static int COMPLEXITY;

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
      System.out.println("5. Dijkstra's algorithm");
      System.out.println("6. Floyd–Warshall algorithm");
      System.out.println("7. Exit");
      System.out.print("Input your choice: ");

      choice = scanner.nextInt();

      switch (choice) {
        case 1: {
          System.out.println("\n\n");
          System.out.println("You have chosen: Create happy occasion graph");

          inputRowsAndCols(scanner);
          GRAPH = happyOccasionGraph(VERTEX, VERTEX);
          break;
        }
        case 2: {
          System.out.println("\n\n");
          System.out.println("You have chosen: Create unhappy occasion graph");

          inputRowsAndCols(scanner);
          GRAPH = unhappyOccasionGraph(VERTEX, VERTEX);
          break;
        }
        case 3: {
          System.out.println("\n\n");
          System.out.println("You have chosen: Create medium occasion graph");

          inputRowsAndCols(scanner);
          GRAPH = mediumOccasionGraph(VERTEX, VERTEX);
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
          System.out.println("You have chosen: Dijkstra's algorithm");

          resetComplexity();
          for (int i = 0; i < VERTEX; i++) {
            System.out.println("The shortest paths between all pairs of graph vertices, starting from: " + i);
            dijkstra(i);
          }
          showComplexity();
          break;
        }
        case 6: {
          System.out.println("\n\n");
          System.out.println("You have chosen: Floyd–Warshall algorithm");

          resetComplexity();
          floydWarshall();
          showComplexity();
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
    for (int i = 0; i < VERTEX; i++) {
      for (int j = 0; j < VERTEX; j++) {
        if (GRAPH[i][j] == INF) {
          System.out.print(0 + " ");

        } else {
          System.out.print(GRAPH[i][j] + " ");
        }
      }
      System.out.println();
    }
  }

  private static int[][] getMatrix(int rows, int cols) {
    final int[][] matrix = new int[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        matrix[i][j] = INF;
        if (i == j) {
          matrix[i][j] = 0;
        }
      }
    }
    return matrix;
  }

  private static void inputRowsAndCols(Scanner scanner) {
    System.out.print("Number of vertexes: ");
    VERTEX = scanner.nextInt();
  }


  static void dijkstra(int src) {
    int[] dist = new int[VERTEX];
    boolean[] visited = new boolean[VERTEX];

    Arrays.fill(dist, INF);
    dist[src] = 0;

    PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparingInt(u -> dist[u]));
    pq.add(src);

    increaseComplexity();
    while (!pq.isEmpty()) {
      increaseComplexity(2);
      int u = pq.poll();
      visited[u] = true;

      increaseComplexity(2);
      for (int v = 0; v < VERTEX; v++) {
        increaseComplexity(4);
        if (GRAPH[u][v] != INF && !visited[v]) {
          increaseComplexity(3);
          int newDist = dist[u] + GRAPH[u][v];
          if (newDist < dist[v]) {
            increaseComplexity(2);
            dist[v] = newDist;
            pq.add(v);
          }
        }
      }
    }

    System.out.print(Arrays.toString(dist) + "\n");
  }

  public static void floydWarshall() {
    int[][] copiedMatrix = new int[VERTEX][VERTEX];
    for (int i = 0; i < VERTEX; i++) {
      System.arraycopy(GRAPH[i], 0, copiedMatrix[i], 0, VERTEX);
    }

    increaseComplexity(2);
    for (int k = 0; k < VERTEX; k++) {
      increaseComplexity(3);
      for (int i = 0; i < VERTEX; i++) {
        increaseComplexity(3);
        for (int j = 0; j < VERTEX; j++) {
          increaseComplexity(3);
          if (copiedMatrix[i][k] != INF && copiedMatrix[k][j] != INF) {
            increaseComplexity(4);
            copiedMatrix[i][j] = Math.min(copiedMatrix[i][j], copiedMatrix[i][k] + copiedMatrix[k][j]);
          }
        }
      }
    }

    for (int i = 0; i < VERTEX; i++) {
      System.out.println("The shortest paths between all pairs of graph vertices, starting from: " + i);
      System.out.print(Arrays.toString(copiedMatrix[i]));
      System.out.println();
    }
  }

  private static void resetComplexity() {
    COMPLEXITY = 0;
  }

  private static void increaseComplexity() {
    increaseComplexity(1);
  }

  private static void increaseComplexity(int i) {
    COMPLEXITY += i;
  }

  private static void showComplexity() {
    System.out.println("\nComplexity of this algorithm is: " + COMPLEXITY);
  }
}
