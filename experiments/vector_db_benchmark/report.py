"""
Renders benchmark results as a terminal table and two matplotlib PNGs.
"""

import os
from collections import defaultdict
from typing import List

import matplotlib
matplotlib.use("Agg")
import matplotlib.pyplot as plt
import numpy as np
from tabulate import tabulate

from benchmark import BenchmarkResult

RESULTS_DIR = os.path.join(os.path.dirname(__file__), "results")

BACKEND_COLORS = {
    "BruteForce": "#555555",
    "FAISS-IVFFlat": "#1f77b4",
    "FAISS-HNSW": "#ff7f0e",
    "Annoy": "#2ca02c",
    "Qdrant": "#d62728",
}


def print_table(results: List[BenchmarkResult]) -> None:
    rows = []
    for r in results:
        param_str = ", ".join(f"{k}={v}" for k, v in r.params.items()) if r.params else "(none)"
        rows.append([
            r.backend_name,
            param_str,
            f"{r.build_ms:.0f}",
            f"{r.recall_at_k:.4f}",
            f"{r.p50:.0f}",
            f"{r.p95:.0f}",
            f"{r.p99:.0f}",
        ])

    headers = ["Backend", "Params", "Build(ms)", "Recall@10", "p50(us)", "p95(us)", "p99(us)"]
    print("\n" + tabulate(rows, headers=headers, tablefmt="github") + "\n")


def plot_recall_vs_latency(results: List[BenchmarkResult]) -> None:
    fig, ax = plt.subplots(figsize=(10, 6))

    by_backend = defaultdict(list)
    for r in results:
        by_backend[r.backend_name].append(r)

    for backend_name, backend_results in by_backend.items():
        xs = [r.p95 for r in backend_results]
        ys = [r.recall_at_k for r in backend_results]
        color = BACKEND_COLORS.get(backend_name, "#888888")
        ax.scatter(xs, ys, label=backend_name, color=color, s=80, alpha=0.85, zorder=3)
        # connect param sweep points with a faint line
        order = np.argsort(xs)
        ax.plot(
            [xs[i] for i in order],
            [ys[i] for i in order],
            color=color, alpha=0.3, linewidth=1,
        )

    ax.set_xlabel("p95 Query Latency (µs)", fontsize=12)
    ax.set_ylabel("Recall@10", fontsize=12)
    ax.set_title("Recall vs Latency — ANN Backend Comparison\n(lower-left = fast but inaccurate, upper-left = ideal)", fontsize=12)
    ax.legend(loc="lower right", fontsize=10)
    ax.set_ylim(0, 1.05)
    ax.grid(True, alpha=0.3)

    path = os.path.join(RESULTS_DIR, "recall_vs_latency.png")
    fig.savefig(path, dpi=150, bbox_inches="tight")
    plt.close(fig)
    print(f"Saved: {path}")


def plot_latency_distribution(results: List[BenchmarkResult]) -> None:
    # For each backend, pick the config with best recall@10 to compare fairly
    by_backend = defaultdict(list)
    for r in results:
        by_backend[r.backend_name].append(r)

    best_per_backend = {}
    for backend_name, backend_results in by_backend.items():
        best = max(backend_results, key=lambda r: r.recall_at_k)
        best_per_backend[backend_name] = best

    backend_names = list(best_per_backend.keys())
    data = [best_per_backend[n].latencies_us for n in backend_names]
    colors = [BACKEND_COLORS.get(n, "#888888") for n in backend_names]

    fig, ax = plt.subplots(figsize=(10, 6))
    bp = ax.boxplot(data, patch_artist=True, showfliers=False, medianprops={"color": "black", "linewidth": 2})

    for patch, color in zip(bp["boxes"], colors):
        patch.set_facecolor(color)
        patch.set_alpha(0.7)

    ax.set_xticks(range(1, len(backend_names) + 1))
    ax.set_xticklabels(backend_names, fontsize=11)
    ax.set_ylabel("Query Latency (µs)", fontsize=12)
    ax.set_title("Query Latency Distribution at Best Recall Config\n(box = IQR, whiskers = 5th–95th pct)", fontsize=12)
    ax.grid(True, axis="y", alpha=0.3)

    path = os.path.join(RESULTS_DIR, "latency_distribution.png")
    fig.savefig(path, dpi=150, bbox_inches="tight")
    plt.close(fig)
    print(f"Saved: {path}")


def render(results: List[BenchmarkResult]) -> None:
    os.makedirs(RESULTS_DIR, exist_ok=True)
    print_table(results)
    plot_recall_vs_latency(results)
    plot_latency_distribution(results)
