package mst;

import graph.Edge;
import graph.Graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class PrimMST {
    private List<Edge> mstEdges;
    private double totalWeight;
    private long operationCount;
    private boolean isConnected;

    public PrimMST(Graph graph) {
        mstEdges = new ArrayList<>();
        totalWeight = 0;
        operationCount = 0;

        int verticesCount = graph.getVerticesCount();
        if (verticesCount == 0) {
            isConnected = true;
            return;
        }

        boolean[] visited = new boolean[verticesCount];
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingDouble(Edge::getWeight));

        visited[0] = true;
        pq.addAll(graph.getAdjacencyList().get(0));

        while (!pq.isEmpty()) {
            Edge edge = pq.poll();
            operationCount++;

            int destination = edge.getDestination();

            if (visited[destination]) {
                continue;
            }

            visited[destination] = true;
            mstEdges.add(edge);
            totalWeight += edge.getWeight();

            for (Edge nextEdge : graph.getAdjacencyList().get(destination)) {
                operationCount++;
                if (!visited[nextEdge.getDestination()]) {
                    pq.add(nextEdge);
                }
            }
        }

        int visitedCount = 0;
        for (boolean v : visited) {
            if (v) visitedCount++;
        }
        isConnected = (visitedCount == verticesCount);
    }

    public List<Edge> getMstEdges() {
        return mstEdges;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public long getOperationCount() {
        return operationCount;
    }

    public boolean isConnected() {
        return isConnected;
    }
}