package domain;

import java.util.List;

public class Vertex {
    private final int numberName;
    private final List<Integer> arcs;

    public Vertex(int numberName, List<Integer> arcs) {
        this.numberName = numberName;
        this.arcs = arcs;
    }

    public int getNumberName() {
        return numberName;
    }

    public List<Integer> getArcs() {
        return arcs;
    }
}
