import java.util.ArrayList;
import java.util.List;

public class Graph {

    private final int vertex;
    private final List<Edge> edges;

    public Graph(int vertex) {
        this.vertex = vertex;
        this.edges = new ArrayList<Edge>();
    }

    public int getVertex() {
        return vertex;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void addEdge(int source, int destination, int weight) {
        Edge e = new Edge(source, destination, weight);
        edges.add(e);
    }
}
