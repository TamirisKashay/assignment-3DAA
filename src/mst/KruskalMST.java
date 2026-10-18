package mst;

import dsu.DSU;
import graph.Edge;
import graph.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KruskalMST {
    private List<Edge> mstEdges;
    private double totalWeight;
    private long operationCount;

    public KruskalMST(Graph graph) {
        mstEdges = new ArrayList<>();
        totalWeight = 0;
        operationCount = 0;

        List<Edge> edges = new ArrayList<>(graph.getEdges());
        Collections.sort(edges);

        DSU dsu = new DSU(graph.getVerticesCount());

        for (Edge edge : edges) {
            operationCount++;
            int root1 = dsu.find(edge.getSource());
            int root2 = dsu.find(edge.getDestination());

            if (root1 != root2) {
                dsu.union(root1, root2);
                mstEdges.add(edge);
                totalWeight += edge.getWeight();
            }
        }
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
}