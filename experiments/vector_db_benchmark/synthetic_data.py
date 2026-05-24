"""
Synthetic embedding data for vector DB benchmarking.

Generates clustered Gaussian vectors that mimic tweet/user embedding spaces
(like SimClusters or TwHIN embeddings from the X recommendation algorithm).
"""

import numpy as np
from typing import Tuple


def generate_clustered_embeddings(
    n_vectors: int = 50_000,
    n_queries: int = 1_000,
    n_clusters: int = 50,
    dim: int = 128,
    cluster_std: float = 0.3,
    seed: int = 42,
) -> Tuple[np.ndarray, np.ndarray, np.ndarray]:
    """
    Returns (index_vectors, query_vectors, labels) where all vectors are
    L2-normalized (unit sphere) to match cosine similarity usage.

    index_vectors: (n_vectors, dim) float32
    query_vectors: (n_queries, dim) float32
    labels:        (n_vectors,) int — cluster assignment per vector
    """
    rng = np.random.default_rng(seed)

    cluster_centers = rng.standard_normal((n_clusters, dim)).astype(np.float32)
    cluster_centers /= np.linalg.norm(cluster_centers, axis=1, keepdims=True)

    assignments = rng.integers(0, n_clusters, size=n_vectors)
    noise = rng.standard_normal((n_vectors, dim)).astype(np.float32) * cluster_std
    index_vectors = cluster_centers[assignments] + noise
    index_vectors /= np.linalg.norm(index_vectors, axis=1, keepdims=True)

    query_assignments = rng.integers(0, n_clusters, size=n_queries)
    query_noise = rng.standard_normal((n_queries, dim)).astype(np.float32) * cluster_std
    query_vectors = cluster_centers[query_assignments] + query_noise
    query_vectors /= np.linalg.norm(query_vectors, axis=1, keepdims=True)

    return index_vectors, query_vectors, assignments


def compute_ground_truth(
    index_vectors: np.ndarray,
    query_vectors: np.ndarray,
    k: int = 10,
    batch_size: int = 100,
) -> np.ndarray:
    """
    Exact nearest neighbors via brute-force cosine similarity (dot product on
    unit vectors). Returns (n_queries, k) int array of neighbor indices.

    Batched to avoid OOM on large datasets.
    """
    n_queries = query_vectors.shape[0]
    ground_truth = np.zeros((n_queries, k), dtype=np.int64)

    for start in range(0, n_queries, batch_size):
        end = min(start + batch_size, n_queries)
        batch = query_vectors[start:end]
        sims = batch @ index_vectors.T  # (batch, n_vectors)
        top_k = np.argpartition(sims, -k, axis=1)[:, -k:]
        # sort within top_k by descending similarity
        for i, row_top in enumerate(top_k):
            sorted_idx = row_top[np.argsort(sims[i, row_top])[::-1]]
            ground_truth[start + i] = sorted_idx

    return ground_truth
