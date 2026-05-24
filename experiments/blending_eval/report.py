"""
Renders blending A/B eval results as a terminal table and two matplotlib PNGs.
"""

import os
from typing import Dict, Tuple

import matplotlib
matplotlib.use("Agg")
import matplotlib.pyplot as plt
import numpy as np
from tabulate import tabulate

RESULTS_DIR = os.path.join(os.path.dirname(__file__), "results")

BLENDER_COLORS = [
    "#1f77b4", "#ff7f0e", "#2ca02c", "#d62728",
    "#9467bd", "#8c564b", "#e377c2",
]


def print_table(results: Dict[str, Dict[str, Tuple[float, float]]]) -> None:
    blender_names = list(results.keys())
    metric_names = list(next(iter(results.values())).keys())

    rows = []
    for blender_name in blender_names:
        row = [blender_name]
        for m in metric_names:
            mean, std = results[blender_name][m]
            row.append(f"{mean:.4f}±{std:.4f}")
        rows.append(row)

    headers = ["Blender"] + metric_names
    print("\n" + tabulate(rows, headers=headers, tablefmt="github") + "\n")


def plot_blender_comparison(results: Dict[str, Dict[str, Tuple[float, float]]]) -> None:
    blender_names = list(results.keys())
    metric_names = list(next(iter(results.values())).keys())
    n_metrics = len(metric_names)
    n_blenders = len(blender_names)

    x = np.arange(n_metrics)
    width = 0.8 / n_blenders

    fig, ax = plt.subplots(figsize=(15, 6))

    for i, blender_name in enumerate(blender_names):
        means = [results[blender_name][m][0] for m in metric_names]
        stds = [results[blender_name][m][1] for m in metric_names]
        offset = (i - n_blenders / 2 + 0.5) * width
        ax.bar(x + offset, means, width, label=blender_name,
               color=BLENDER_COLORS[i % len(BLENDER_COLORS)], alpha=0.85,
               yerr=stds, capsize=2, error_kw={"elinewidth": 0.8})

    ax.set_xticks(x)
    ax.set_xticklabels(metric_names, fontsize=10, rotation=15, ha="right")
    ax.set_ylabel("Metric value (mean ± std over 50 trials)", fontsize=11)
    ax.set_title("Blending Strategy Comparison — A/B Eval\n"
                 "(src_entropy: higher = more balanced; src_concentration: lower = more balanced;\n"
                 "recency_hrs: lower = fresher feed)", fontsize=11)
    ax.legend(loc="upper right", fontsize=8, ncol=2)
    ax.grid(True, axis="y", alpha=0.3)

    path = os.path.join(RESULTS_DIR, "blender_comparison.png")
    fig.savefig(path, dpi=150, bbox_inches="tight")
    plt.close(fig)
    print(f"Saved: {path}")


def plot_diversity_vs_relevance(results: Dict[str, Dict[str, Tuple[float, float]]]) -> None:
    fig, ax = plt.subplots(figsize=(9, 6))

    for i, (blender_name, metrics) in enumerate(results.items()):
        x = metrics.get("src_entropy", (0, 0))[0]
        y = metrics.get("mean_relevance", (0, 0))[0]
        x_err = metrics.get("src_entropy", (0, 0))[1]
        y_err = metrics.get("mean_relevance", (0, 0))[1]
        color = BLENDER_COLORS[i % len(BLENDER_COLORS)]
        ax.errorbar(x, y, xerr=x_err, yerr=y_err, fmt="o", color=color,
                    markersize=8, capsize=4, label=blender_name, zorder=3)
        ax.annotate(
            blender_name,
            (x, y),
            textcoords="offset points",
            xytext=(6, 4),
            fontsize=8,
            color=color,
        )

    ax.set_xlabel("Source Entropy (normalized, 1=perfectly balanced)", fontsize=12)
    ax.set_ylabel("Mean Relevance (avg similarity score, top-50)", fontsize=12)
    ax.set_title("Source Diversity vs Relevance Trade-off\n"
                 "(upper-right = best of both; error bars = ±1 std over 50 trials)", fontsize=12)
    ax.grid(True, alpha=0.3)

    path = os.path.join(RESULTS_DIR, "diversity_vs_relevance.png")
    fig.savefig(path, dpi=150, bbox_inches="tight")
    plt.close(fig)
    print(f"Saved: {path}")


def render(results: Dict[str, Dict[str, Tuple[float, float]]]) -> None:
    os.makedirs(RESULTS_DIR, exist_ok=True)
    print_table(results)
    plot_blender_comparison(results)
    plot_diversity_vs_relevance(results)
