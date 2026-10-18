package connectivity;

import graph.Edge;
import graph.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class StronglyConnectedComponents {
    private final List<List<Integer>> sccs;

    public StronglyConnectedComponents(Graph graph) {
        sccs = new ArrayList<>();
        int vCount = graph.getVerticesCount();
        boolean[] visited = new boolean[vCount];
        Stack<Integer> stack = new Stack<>();

        List<List<Edge>> adj = graph.getAdjacencyList();

        for (int i = 0; i < vCount; i++) {
            if (!visited[i]) {
                dfs1(i, visited, stack, adj);
            }
        }

        Graph transposeGraph = getTranspose(graph);
        List<List<Edge>> transposeAdj = transposeGraph.getAdjacencyList();

        boolean[] visitedTranspose = new boolean[vCount];
        while (!stack.isEmpty()) {
            int u = stack.pop();
            if (!visitedTranspose[u]) {
                List<Integer> component = new ArrayList<>();
                dfs2(u, visitedTranspose, component, transposeAdj);
                sccs.add(component);
            }
        }
    }

    private void dfs1(int u, boolean[] visited, Stack<Integer> stack, List<List<Edge>> adj) {
        visited[u] = true;
        for (Edge edge : adj.get(u)) {
            int v = edge.getDestination();
            if (!visited[v]) {
                dfs1(v, visited, stack, adj);
            }
        }
        stack.push(u);
    }

    private void dfs2(int u, boolean[] visited, List<Integer> component, List<List<Edge>> adj) {
        visited[u] = true;
        component.add(u);
        for (Edge edge : adj.get(u)) {
            int v = edge.getDestination();
            if (!visited[v]) {
                dfs2(v, visited, component, adj);
            }
        }
    }

    private Graph getTranspose(Graph graph) {
        Graph transpose = new Graph(graph.getVerticesCount(), true);
        for (Edge edge : graph.getEdges()) {
            transpose.addEdge(edge.getDestination(), edge.getSource(), edge.getWeight());
        }
        return transpose;
    }

    public List<List<Integer>> getSccs() {
        return sccs;
    }
}