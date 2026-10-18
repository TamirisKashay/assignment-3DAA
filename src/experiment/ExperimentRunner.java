package experiment;

import connectivity.StronglyConnectedComponents;
import connectivity.TopologicalSort;
import graph.Edge;
import graph.Graph;
import mst.KruskalMST;
import mst.PrimMST;
import shortestpath.Dijkstra;

import java.util.Random;

public class ExperimentRunner {
    public static void runExperiments() {
        int[][] configs = {
                {10, 30},
                {100, 500},
                {1000, 5000}
        };
        String[] sizeNames = {"Small", "Medium", "Large"};

        System.out.println("Running experiments (5 iterations each)...");

        for (int i = 0; i < configs.length; i++) {
            int v = configs[i][0];
            int e = configs[i][1];
            String name = sizeNames[i];

            long kruskalTimeSum = 0, kruskalOpSum = 0;
            long primTimeSum = 0, primOpSum = 0;
            long sccTimeSum = 0;
            long topoTimeSum = 0;
            long dijkstraTimeSum = 0, dijkstraOpSum = 0;

            int runs = 5;
            for (int r = 0; r < runs; r++) {
                Random rand = new Random(42 + r);

                Graph undirectedGraph = generateConnectedGraph(v, e, false, rand);

                long start = System.nanoTime();
                KruskalMST kruskal = new KruskalMST(undirectedGraph);
                kruskalTimeSum += (System.nanoTime() - start);
                kruskalOpSum += kruskal.getOperationCount();

                start = System.nanoTime();
                PrimMST prim = new PrimMST(undirectedGraph);
                primTimeSum += (System.nanoTime() - start);
                primOpSum += prim.getOperationCount();

                start = System.nanoTime();
                Dijkstra dijkstra = new Dijkstra(undirectedGraph, 0);
                dijkstraTimeSum += (System.nanoTime() - start);
                dijkstraOpSum += dijkstra.getOperationCount();

                Graph directedGraph = generateDirectedGraph(v, e, rand);

                start = System.nanoTime();
                TopologicalSort topo = new TopologicalSort(directedGraph);
                topoTimeSum += (System.nanoTime() - start);

                start = System.nanoTime();
                StronglyConnectedComponents scc = new StronglyConnectedComponents(directedGraph);
                sccTimeSum += (System.nanoTime() - start);
            }

            System.out.printf("--- %s Graph (V=%d, E=%d) ---\n", name, v, e);
            System.out.printf("Kruskal:  Avg Time = %.4f ms, Avg Ops = %d\n", (kruskalTimeSum / 5.0) / 1e6, kruskalOpSum / 5);
            System.out.printf("Prim:     Avg Time = %.4f ms, Avg Ops = %d\n", (primTimeSum / 5.0) / 1e6, primOpSum / 5);
            System.out.printf("Dijkstra: Avg Time = %.4f ms, Avg Ops = %d\n", (dijkstraTimeSum / 5.0) / 1e6, dijkstraOpSum / 5);
            System.out.printf("TopoSort: Avg Time = %.4f ms\n", (topoTimeSum / 5.0) / 1e6);
            System.out.printf("SCC:      Avg Time = %.4f ms\n\n", (sccTimeSum / 5.0) / 1e6);
        }
    }

    private static Graph generateConnectedGraph(int v, int e, boolean isDirected, Random rand) {
        Graph graph = new Graph(v, isDirected);
        for (int i = 1; i < v; i++) {
            int parent = rand.nextInt(i);
            double weight = 1 + rand.nextDouble() * 99;
            graph.addEdge(parent, i, weight);
        }

        int addedEdges = v - 1;
        while (addedEdges < e) {
            int src = rand.nextInt(v);
            int dest = rand.nextInt(v);
            if (src != dest) {
                double weight = 1 + rand.nextDouble() * 99;
                graph.addEdge(src, dest, weight);
                addedEdges++;
            }
        }
        return graph;
    }

    private static Graph generateDirectedGraph(int v, int e, Random rand) {
        Graph graph = new Graph(v, true);
        int addedEdges = 0;
        while (addedEdges < e) {
            int src = rand.nextInt(v);
            int dest = rand.nextInt(v);
            if (src != dest) {
                double weight = 1 + rand.nextDouble() * 99;
                graph.addEdge(src, dest, weight);
                addedEdges++;
            }
        }
        return graph;
    }
}