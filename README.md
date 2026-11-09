# Assignment 3: Smart City Infrastructure and Route Planning

## 1. Overview
This project models a smart city's infrastructure network as a graph where vertices represent city districts or locations, edges represent roads or infrastructure connections, and weights correspond to construction costs or travel distances. The application implements fundamental graph algorithms to support infrastructure planning, connectivity analysis, and route optimization.

### Project Structure
- `src/graph/`: Core data structures (`Graph`, `Edge`).
- `src/dsu/`: Disjoint Set Union with path compression and union by rank.
- `src/mst/`: Minimum Spanning Tree algorithms (Kruskal's and Prim's).
- `src/connectivity/`: Topological sorting and Strongly Connected Components (Kosaraju's algorithm).
- `src/shortestpath/`: Dijkstra's algorithm for shortest paths[cite: 2].
- `src/experiment/`: Automated benchmark runner and graph generators.

---

## 2. Implementation
- **DSU**: Implemented with path compression in `find` and union by rank to achieve nearly constant time complexity $O(\alpha(V))$[cite: 3].
- **Kruskal's Algorithm**: Sorts all graph edges by weight and greedily incorporates them using the DSU data structure to prevent cycles.
- **Prim's Algorithm**: Utilizes an adjacency list and a binary min-priority queue (`PriorityQueue`) to grow the MST from a starting vertex.
- **Topological Sort**: Implements Kahn's algorithm using in-degrees and a queue to determine linear ordering and detect cycles in directed graphs[cite: 2].
- **Strongly Connected Components (SCC)**: Uses Kosaraju's two-pass DFS algorithm with edge transposition[cite: 2].
- **Dijkstra's Algorithm**: Computes single-source shortest paths using a priority queue with non-negative edge weight constraints[cite: 2].

---

## 3. Complexity Analysis

| Algorithm / Component | Time Complexity | Space Complexity | Notes / Assumptions |
| :--- | :--- | :--- | :--- |
| **DSU (`find` & `union`)** | $O(\alpha(V))$ per op. | $O(V)$ | Optimized via path compression and union by rank[cite: 3]. |
| **Kruskal's Algorithm** | $O(E \log E)$ | $O(V + E)$ | Dominated by edge sorting time. |
| **Prim's Algorithm** | $O((V + E) \log V)$ | $O(V + E)$ | Using a binary min-heap priority queue. |
| **Topological Sort** | $O(V + E)$ | $O(V + E)$ | Kahn's algorithm with queue processing. |
| **Kosaraju's SCC** | $O(V + E)$ | $O(V + E)$ | Two sequential DFS passes and graph transposition. |
| **Dijkstra's Algorithm** | $O((V + E) \log V)$ | $O(V + E)$ | Priority queue based implementation. |

---

## 4. Experimental Setup
- **Graph Configurations**:
    - **Small**: $V = 10, E = 30$[cite: 3]
    - **Medium**: $V = 100, E = 500$[cite: 3]
    - **Large**: $V = 1,000, E = 5,000$[cite: 3]
- **Reproducibility**: Graphs were generated deterministically using a fixed random seed (`seed = 42 + runIndex`)[cite: 3]. Undirected graphs guaranteed connectivity by constructing a spanning backbone tree prior to adding random edges[cite: 3].
- **Measurements**: Each experiment was repeated 5 times, measuring average execution time via `System.nanoTime()` and tracking key operational counts[cite: 3].

---

## 5. Results

### Experimental Benchmark Output
### Benchmark Results Table
| Graph Size | Vertices ($V$) | Edges ($E$) | Algorithm | Avg Time (ms) | Avg Operations |
| :--- | :---: | :---: | :--- | :---: | :---: |
| **Small** | 10 | 30 | Kruskal | 0.2695 | 30 |
| Small | 10 | 30 | Prim | 1.2567 | 83 |
| Small | 10 | 30 | Dijkstra | 0.3056 | 76 |
| Small | 10 | 30 | TopoSort | 0.1610 | N/A |
| Small | 10 | 30 | SCC | 0.1763 | N/A |
| **Medium** | 100 | 500 | Kruskal | 0.4549 | 500 |
| Medium | 100 | 500 | Prim | 0.3740 | 1487 |
| Medium | 100 | 500 | Dijkstra | 0.2017 | 1196 |
| Medium | 100 | 500 | TopoSort | 0.0987 | N/A |
| Medium | 100, | 500 | SCC | 0.2125 | N/A |
| **Large** | 1000 | 5000 | Kruskal | 2.8342 | 5000 |
| Large | 1000 | 5000 | Prim | 3.1143 | 14984 |
| Large | 1000 | 5000 | Dijkstra | 2.3788 | 11944 |
| Large | 1000 | 5000 | TopoSort | 0.5324 | N/A |
| Large | 1000 | 5000 | SCC | 1.4859 | N/A |
```text
Running experiments (5 iterations each)...
--- Small Graph (V=10, E=30) ---
Kruskal:  Avg Time = 0,1537 ms, Avg Ops = 30
Prim:     Avg Time = 0,8676 ms, Avg Ops = 83
Dijkstra: Avg Time = 0,2546 ms, Avg Ops = 76
TopoSort: Avg Time = 0,1183 ms
SCC:      Avg Time = 0,1724 ms

--- Medium Graph (V=100, E=500) ---
Kruskal:  Avg Time = 0,4738 ms, Avg Ops = 500
Prim:     Avg Time = 0,3556 ms, Avg Ops = 1487
Dijkstra: Avg Time = 0,2014 ms, Avg Ops = 1196
TopoSort: Avg Time = 0,0581 ms
SCC:      Avg Time = 0,2067 ms

--- Large Graph (V=1000, E=5000) ---
Kruskal:  Avg Time = 2,8686 ms, Avg Ops = 5000
Prim:     Avg Time = 2,7233 ms, Avg Ops = 14984
Dijkstra: Avg Time = 2,1459 ms, Avg Ops = 11944
TopoSort: Avg Time = 0,4713 ms
SCC:      Avg Time = 1,3519 ms
```
## 6. Comparison and Discussion
- **Kruskal vs. Prim**: Kruskal's algorithm scales extremely well on sparse graphs due to efficient sorting and DSU operations. Prim's algorithm performs competitively, though priority queue overhead scales with the number of edge relaxations.
- **Connectivity & Topological Ordering**: Topological sorting runs rapidly on DAGs in linear time $O(V + E)$. When cycles are present, Kahn's algorithm correctly terminates early or reports incomplete ordering.
- **Dijkstra's Limitations**: Dijkstra efficiently finds shortest paths under non-negative weights[cite: 2]. Negative edge weights violate its greedy premise because finalized shortest distances can be invalidated by subsequent negative weight relaxation.

---

## 7. Testing and Correctness
The implementation was verified against edge cases:
- Verified that Kruskal and Prim yield identical total MST weights on connected, undirected, weighted graphs[cite: 4].
- Confirmed topological sorting validity on DAGs and cycle detection on cyclic structures[cite: 4].
- Verified that every vertex belongs to exactly one SCC[cite: 4].
- Confirmed correct distance computations and unreachable vertex handling in Dijkstra's algorithm[cite: 4].

---

## 8. Conclusion
All required smart city infrastructure algorithms were successfully implemented, benchmarked, and analyzed. Kruskal and Prim provide robust solutions for minimum-cost road networks, while Dijkstra optimizes route planning, and connectivity algorithms ensure reliable infrastructure dependency management[cite: 1, 2].