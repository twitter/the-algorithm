"""
Entry point for the vector DB benchmark.

Usage:
    cd experiments
    python vector_db_benchmark/run.py [--n-vectors N] [--n-queries N] [--k K]
"""

import argparse
import sys
import os

sys.path.insert(0, os.path.dirname(__file__))

from synthetic_data import generate_clustered_embeddings, compute_ground_truth
from backends import all_backend_configs
from benchmark import run_all
from report import render


def main():
    parser = argparse.ArgumentParser(description="ANN Vector DB Benchmark")
    parser.add_argument("--n-vectors", type=int, default=50_000, help="Index size")
    parser.add_argument("--n-queries", type=int, default=1_000, help="Number of query vectors")
    parser.add_argument("--k", type=int, default=10, help="Neighbors to retrieve")
    parser.add_argument("--dim", type=int, default=128, help="Embedding dimension")
    parser.add_argument("--seed", type=int, default=42)
    args = parser.parse_args()

    print(f"Generating {args.n_vectors:,} index vectors + {args.n_queries:,} queries "
          f"({args.dim}d, {50} clusters) ...")
    index_vectors, query_vectors, _ = generate_clustered_embeddings(
        n_vectors=args.n_vectors,
        n_queries=args.n_queries,
        dim=args.dim,
        seed=args.seed,
    )

    print(f"Computing ground truth (exact top-{args.k}) ...")
    ground_truth = compute_ground_truth(index_vectors, query_vectors, k=args.k)

    backends = all_backend_configs()
    print(f"\nRunning {len(backends)} backend configs ...\n")
    results = run_all(backends, index_vectors, query_vectors, ground_truth, k=args.k)

    render(results)


if __name__ == "__main__":
    main()
