import static java.lang.Integer.MAX_VALUE;

public class BellmanFordImpl {
    public static boolean hasNegativeCycle(Graph graph, int sourceVertex, int[] distance) {
        int vertex = graph.getVertex();
        for (int i = 1; i < vertex; i++) {
            distance[i] = MAX_VALUE;
        }

        distance[sourceVertex] = 0;
        for (int i = 1; i < vertex; i++) {
            for (Edge edge : graph.getEdges()) {
                int source = edge.getSource();
                int destination = edge.getDestination();
                int weight = edge.getWeight();
                if (distance[source] != MAX_VALUE && distance[destination] > distance[source] + weight) {
                    distance[destination] = distance[source] + weight;
                }
            }
        }

        for (Edge edge : graph.getEdges()) {
            int source = edge.getSource();
            int destination = edge.getDestination();
            int weight = edge.getWeight();
            if (distance[source] != MAX_VALUE && distance[destination] > distance[source] + weight) {
                return true;
            }
        }
        return false;
    }
}

