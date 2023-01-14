import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Kruskal {

  private final int[] parent;
  private final List<Edge> mst;
  private int COMPLEXITY = 0;

  public Kruskal(int[][] graph, int V) {
    parent = new int[V];
    mst = new ArrayList<>();

    for (int i = 0; i < V; i++) {
      COMPLEXITY+=2;
      parent[i] = i;
    }

    List<Edge> edges = new ArrayList<>();
    COMPLEXITY+=2;
    for (int i = 0; i < V; i++) {
      COMPLEXITY++;
      for (int j = 0; j < V; j++) {
        COMPLEXITY+=2;
        if (graph[i][j] != 0) {
          edges.add(new Edge(i, j, graph[i][j]));
        }
        COMPLEXITY+=2;
      }
    }

    Collections.sort(edges);

    for (Edge edge : edges) {
      int x = find(edge.getFrom());
      int y = find(edge.getTo());
      if (x != y) {
        COMPLEXITY+=5;
        mst.add(edge);
        parent[x] = y;
        if (mst.size() == V - 1) {
          break;
        }
      }
    }
  }

  int find(int i) {
    COMPLEXITY+=2;
    if (parent[i] == i) {
      return i;
    }
    return find(parent[i]);
  }

  public void printMST() {
    System.out.println("Edge :" + " Weight");
    for (Edge edge : mst) {
      System.out.println(edge.getFrom() + " - " + edge.getTo() + "    " + edge.getWeight());
    }

    System.out.println("\n\nNumber of iterations: " + COMPLEXITY);
  }

  private static class Edge implements Comparable<Edge> {

    private final int from;
    private final int to;
    private final int weight;

    public Edge(int from, int to, int weight) {
      this.from = from;
      this.to = to;
      this.weight = weight;
    }

    public int getFrom() {
      return from;
    }

    public int getTo() {
      return to;
    }

    public int getWeight() {
      return weight;
    }

    @Override
    public int compareTo(Edge o) {
      return Integer.compare(this.weight, o.weight);
    }
  }
}
