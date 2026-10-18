package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Graph {
    private final int verticesCount;
    private final List<Edge> edges;
    private final List<List<Edge>> adjacencyList;
    private final boolean isDirected;

    public Graph(int verticesCount, boolean isDirected) {
        this.verticesCount = verticesCount;
        this.edges = new ArrayList<>();
        this.adjacencyList = new ArrayList<>();
        this.isDirected = isDirected;

        for (int i = 0; i < verticesCount; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }

    public void addEdge(int source, int destination, double weight) {
        Edge edge = new Edge(source, destination, weight);
        edges.add(edge);
        adjacencyList.get(source).add(edge);

        if (!isDirected) {
            Edge reverseEdge = new Edge(destination, source, weight);
            adjacencyList.get(destination).add(reverseEdge);
        }
    }

    public int getVerticesCount() {
        return verticesCount;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public List<List<Edge>> getAdjacencyList() {
        return adjacencyList;
    }

    public boolean isDirected() {
        return isDirected;
    }
}