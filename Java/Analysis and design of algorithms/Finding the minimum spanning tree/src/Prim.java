import static java.lang.Integer.MAX_VALUE;

public class Prim {

  private final int[] parent;
  private final int[] key;
  private int COMPLEXITY = 0;

  public Prim(int[][] graph, int V) {
    parent = new int[V];
    key = new int[V];
    boolean[] mstSet = new boolean[V];

    for (int i = 0; i < V; i++) {
      key[i] = MAX_VALUE;
      mstSet[i] = false;
    }

    key[0] = 0;
    parent[0] = -1;

    COMPLEXITY += 2;
    for (int count = 0; count < V - 1; count++) {
      int u = minKey(key, mstSet, V);
      mstSet[u] = true;

      for (int v = 0; v < V; v++) {
        COMPLEXITY += 9;
        if (graph[u][v] != 0 && !mstSet[v] && graph[u][v] < key[v]) {
          parent[v] = u;
          key[v] = graph[u][v];
          COMPLEXITY += 5;
        }
      }
      COMPLEXITY += 2;
    }
  }

  int minKey(int[] key, boolean[] mstSet, int V) {
    COMPLEXITY += 2;
    int min = MAX_VALUE, min_index = -1;
    for (int v = 0; v < V; v++) {
      COMPLEXITY += 5;
      if (!mstSet[v] && key[v] < min) {
        min = key[v];
        min_index = v;
        COMPLEXITY += 3;
      }
      COMPLEXITY += 2;
    }
    return min_index;
  }

  public void printMST() {
    System.out.println("Edge :" + " Weight");
    for (int i = 1; i < parent.length; i++) {
      System.out.println(parent[i] + " - " + i + "    " + key[i]);
    }

    System.out.println("\n\nNumber of iterations: " + COMPLEXITY);
  }
}
