package shortestpath;

import graph.Edge;
import graph.Graph;

import java.util.*;

public class Dijkstra {
    private final double[] distances;
    private final int[] predecessor;
    private final long operationCount;

    public Dijkstra(Graph graph, int source) {
        int vCount = graph.getVerticesCount();
        distances = new double[vCount];
        predecessor = new int[vCount];
        Arrays.fill(distances, Double.MAX_VALUE);
        Arrays.fill(predecessor, -1);

        distances[source] = 0;
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(Node::getDistance));
        pq.add(new Node(source, 0));

        long ops = 0;
        List<List<Edge>> adj = graph.getAdjacencyList();

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            ops++;

            int u = current.vertex;

            if (current.distance > distances[u]) {
                continue;
            }

            for (Edge edge : adj.get(u)) {
                ops++;
                int v = edge.getDestination();
                double weight = edge.getWeight();

                if (distances[u] + weight < distances[v]) {
                    distances[v] = distances[u] + weight;
                    predecessor[v] = u;
                    pq.add(new Node(v, distances[v]));
                }
            }
        }
        operationCount = ops;
    }

    public List<Integer> getPath(int destination) {
        List<Integer> path = new ArrayList<>();
        int curr = destination;
        if (distances[curr] == Double.MAX_VALUE) {
            return path; // Пути нет (вершина недостижима)
        }
        while (curr != -1) {
            path.add(curr);
            curr = predecessor[curr];
        }
        Collections.reverse(path);
        return path;
    }

    public double[] getDistances() {
        return distances;
    }

    public long getOperationCount() {
        return operationCount;
    }

    private static class Node {
        int vertex;
        double distance;

        Node(int vertex, double distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        public double getDistance() {
            return distance;
        }
    }
}