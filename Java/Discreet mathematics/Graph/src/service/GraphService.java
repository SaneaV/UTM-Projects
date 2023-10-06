package service;

import domain.Vertex;

import java.util.List;

public interface GraphService {

    boolean addVertex(int numberName, List<Vertex> vertex);

    boolean deleteVertex(int numberName, List<Vertex> vertex);

    boolean addArc(int firstVertex, int secondVertex, List<Vertex> vertex);

    boolean deleteArc(int firstVertex, int secondVertex, List<Vertex> vertex);

    void adjacencyList(List<Vertex> vertex);

    void incidenceMatrix(List<Vertex> vertex);

    void adjacencyMatrix(List<Vertex> vertex);

    void sortGraph(List<Vertex> vertex);

    void sortArcs(List<Vertex> vertex);
}
