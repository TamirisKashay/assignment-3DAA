package connectivity;

import graph.Edge;
import graph.Graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TopologicalSort {
    private final List<Integer> topologicalOrder;
    private final boolean isDAG;

    public TopologicalSort(Graph graph) {
        topologicalOrder = new ArrayList<>();
        int vCount = graph.getVerticesCount();
        int[] inDegree = new int[vCount];

        List<List<Edge>> adj = graph.getAdjacencyList();

        for (int u = 0; u < vCount; u++) {
            for (Edge edge : adj.get(u)) {
                inDegree[edge.getDestination()]++;
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < vCount; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }

        while (!queue.isEmpty()) {
            int u = queue.poll();
            topologicalOrder.add(u);

            for (Edge edge : adj.get(u)) {
                int v = edge.getDestination();
                inDegree[v]--;
                if (inDegree[v] == 0) {
                    queue.add(v);
                }
            }
        }

        isDAG = (topologicalOrder.size() == vCount);
    }

    public List<Integer> getTopologicalOrder() {
        return topologicalOrder;
    }

    public boolean isDAG() {
        return isDAG;
    }
}