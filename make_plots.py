import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv("results/tables/benchmark_results.csv")
plt.style.use('seaborn-v0_8-whitegrid')

algorithms = ["Kruskal", "Prim", "Dijkstra", "TopoSort", "SCC"]
markers = ['o', 's', '^', 'D', 'v']
colors = ['#1f77b4', '#ff7f0e', '#2ca02c', '#d62728', '#9467bd']
x_pos = [0, 1, 2]

plt.figure(figsize=(8.5, 5), dpi=300)
for alg, marker, color in zip(algorithms, markers, colors):
    subset = df[df["Algorithm"] == alg]
    plt.plot(x_pos, subset["Avg_Time_ms"], marker=marker, color=color, linewidth=2.2, markersize=7, label=alg)
plt.title("Execution Time vs. Number of Vertices (V)", fontsize=13, fontweight='bold')
plt.xlabel("Number of Vertices (V)")
plt.ylabel("Average Execution Time (ms)")
plt.xticks(x_pos, ["10 (Small)", "100 (Medium)", "1,000 (Large)"])
plt.legend(title="Algorithm")
plt.tight_layout()
plt.savefig("results/plots/execution_time_vs_vertices.png")
plt.close()

plt.figure(figsize=(8.5, 5), dpi=300)
for alg, marker, color in zip(algorithms, markers, colors):
    subset = df[df["Algorithm"] == alg]
    plt.plot(x_pos, subset["Avg_Time_ms"], marker=marker, color=color, linewidth=2.2, markersize=7, label=alg)
plt.title("Execution Time vs. Number of Edges (E)", fontsize=13, fontweight='bold')
plt.xlabel("Number of Edges (E)")
plt.ylabel("Average Execution Time (ms)")
plt.xticks(x_pos, ["30 (Small)", "500 (Medium)", "5,000 (Large)"])
plt.legend(title="Algorithm")
plt.tight_layout()
plt.savefig("results/plots/execution_time_vs_edges.png")
plt.close()

plt.figure(figsize=(8.5, 5), dpi=300)
for alg, marker, color in zip(["Kruskal", "Prim", "Dijkstra"], markers[:3], colors[:3]):
    subset = df[df["Algorithm"] == alg]
    plt.plot(x_pos, subset["Avg_Operations"].astype(int), marker=marker, color=color, linewidth=2.2, markersize=7, label=alg)
plt.title("Average Operation Count vs. Graph Scale", fontsize=13, fontweight='bold')
plt.xlabel("Graph Configuration (V, E)")
plt.ylabel("Average Operation Count")
plt.xticks(x_pos, ["Small (10, 30)", "Medium (100, 500)", "Large (1000, 5000)"])
plt.legend(title="Algorithm")
plt.tight_layout()
plt.savefig("results/plots/operation_count_vs_scale.png")
plt.close()
print("Done!")
