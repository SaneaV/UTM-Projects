package service.impl;

import domain.Vertex;
import service.GraphService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparing;

public class GraphServiceImpl implements GraphService {

    @Override
    public boolean addVertex(int numberName, List<Vertex> vertex) {
        if (numberName == 0) {
            return false;
        }

        for (Vertex v : vertex) {
            if (v.getNumberName() == numberName) {
                return false;
            }
        }

        return vertex.add(new Vertex(numberName, new ArrayList<>()));
    }

    @Override
    public boolean deleteVertex(int numberName, List<Vertex> graph) {
        if (numberName == 0) {
            return false;
        }

        for (Vertex value : graph) {
            final List<Integer> arcs = value.getArcs();
            for (int j = 0; j < arcs.size(); j++) {
                if (arcs.get(j) == numberName) {
                    arcs.remove(j);
                }
            }
        }

        for (int i = 0; i < graph.size(); i++) {
            if (graph.get(i).getNumberName() == numberName) {
                graph.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addArc(int firstVertex, int secondVertex, List<Vertex> graph) {
        if (firstVertex == 0 || secondVertex == 0) {
            return false;
        }

        boolean firstVertexExist = false;
        int firstVertexIndex = -1;
        boolean secondVertexExist = false;

        for (Vertex v : graph) {
            if (v.getNumberName() == firstVertex) {
                firstVertexExist = true;
                firstVertexIndex = graph.indexOf(v);
                if (v.getArcs().contains(secondVertex))
                    return false;
            }
            if (v.getNumberName() == secondVertex) secondVertexExist = true;
        }

        if (firstVertexExist && secondVertexExist) {
            return graph.get(firstVertexIndex).getArcs().add(secondVertex);
        } else return false;
    }

    @Override
    public boolean deleteArc(int firstVertex, int secondVertex, List<Vertex> graph) {
        if (firstVertex == 0 || secondVertex == 0) {
            return false;
        }

        for (Vertex vertex : graph) {
            for (int j = 0; j < vertex.getArcs().size(); j++) {
                if (vertex.getNumberName() == firstVertex && vertex.getArcs().get(j) == secondVertex) {
                    vertex.getArcs().remove(j);
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void adjacencyList(List<Vertex> graph) {
        if (graph.size() == 0) {
            System.out.println("Граф пуст");
            return;
        }

        graph.forEach(
                v -> {
                    System.out.print(v.getNumberName() + " |");
                    printArcs(v);
                    System.out.println();
                }
        );
    }

    @Override
    public void adjacencyMatrix(List<Vertex> graph) {
        if (graph.size() == 0) {
            System.out.println("Граф пуст");
            return;
        }

        System.out.print("    ");
        for (Vertex v : graph) {
            System.out.print(v.getNumberName() + " ");
        }

        System.out.print("\n   ");
        for (Vertex ignored : graph) {
            System.out.print("__");
        }

        System.out.println();

        for (int i = 0; i < graph.size(); i++) {
            System.out.print(graph.get(i).getNumberName() + " | ");
            for (Vertex value : graph) {
                if (graph.get(i).getArcs().contains(value.getNumberName())) {
                    System.out.print("1 ");
                } else {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
    }

    @Override
    public void incidenceMatrix(List<Vertex> graph) {
        if (graph.size() == 0) {
            System.out.println("Граф пуст");
            return;
        }

        System.out.print("       ");
        for (Vertex v : graph) {
            System.out.print("\t " + v.getNumberName());
        }

        System.out.print("\n     ");
        for (Vertex ignored : graph) {
            System.out.print("_____");
        }

        System.out.println();
        int size = calculateNumberOfArcs(graph);
        for (int i = 0; i < size; i++) {
            Map<String, Integer> map = printArcs(i, graph);
            for (Vertex value : graph) {
                if (map != null) {
                    if (graph.get(map.get("i")).getArcs().get(map.get("j")) == graph.get(map.get("i")).getNumberName() &&
                            value.getNumberName() == graph.get(map.get("i")).getNumberName()) {
                        System.out.print("\t 2");
                    } else if (value.getNumberName() == graph.get(map.get("i")).getNumberName()) {
                        System.out.print("\t-1");
                    } else if (value.getNumberName() == graph.get(map.get("i")).getArcs().get(map.get("j"))) {
                        System.out.print("\t 1");
                    } else {
                        System.out.print("\t 0");
                    }
                }
            }
            if (map != null) {
                System.out.println();
            }
        }

    }

    private int calculateNumberOfArcs(List<Vertex> graph) {
        int sum = 0;

        for (Vertex vertex : graph) {
            for (int j = 0; j < vertex.getArcs().size(); j++) {
                sum++;
            }
        }
        return sum;
    }

    private Map<String, Integer> printArcs(int number, List<Vertex> graph) {
        int sum = 0;

        for (int i = 0; i < graph.size(); i++) {
            for (int j = 0; j < graph.get(i).getArcs().size(); j++) {
                if (sum == number) {
                    System.out.print(graph.get(i).getNumberName() + "-" + graph.get(i).getArcs().get(j) + " | ");
                    Map<String, Integer> line = new HashMap<>();
                    line.put("i", i);
                    line.put("j", j);
                    return line;
                }
                sum++;
            }
        }
        return null;
    }

    @Override
    public void sortGraph(List<Vertex> graph) {
        graph.sort(comparing(Vertex::getNumberName));
    }

    @Override
    public void sortArcs(List<Vertex> graph) {
        for (Vertex v : graph) {
            v.getArcs().sort(comparing(Integer::valueOf));
        }
    }

    private void printArcs(Vertex vertex) {
        vertex.getArcs().forEach(arc -> System.out.print(" " + arc + ","));
        System.out.print(" 0");
    }
}
