"""
Runs each ANN backend over all param configs and collects:
  build_ms, recall@k, p50/p95/p99 query latency (microseconds).

Fixes applied vs. initial version:
  - 50 warmup queries before timed loop (eliminates cold-cache outliers)
  - FAISS-HNSW shared index injection (builds once, not 4x)
  - FAISS -1 sentinel filtering in backends.py
"""

import time
from dataclasses import dataclass, field
from typing import List

import numpy as np

from backends import AnnBackend, FaissHNSWBackend

N_WARMUP = 50


@dataclass
class BenchmarkResult:
    backend_name: str
    params: dict
    build_ms: float
    recall_at_k: float
    latencies_us: List[float] = field(default_factory=list)

    @property
    def p50(self) -> float:
        return float(np.percentile(self.latencies_us, 50))

    @property
    def p95(self) -> float:
        return float(np.percentile(self.latencies_us, 95))

    @property
    def p99(self) -> float:
        return float(np.percentile(self.latencies_us, 99))


def compute_recall(retrieved: List[int], ground_truth: List[int]) -> float:
    if not ground_truth:
        return 0.0
    return len(set(retrieved) & set(ground_truth)) / len(ground_truth)


def run_benchmark(
    backend: AnnBackend,
    index_vectors: np.ndarray,
    query_vectors: np.ndarray,
    ground_truth: np.ndarray,
    k: int = 10,
) -> BenchmarkResult:
    print(f"  Building {backend.name()} {backend.params()} ...", end=" ", flush=True)
    build_ms = backend.build(index_vectors)
    print(f"{build_ms:.0f}ms", flush=True)

    # Warmup: run queries without timing to bring caches to steady state
    rng = np.random.default_rng(0)
    warmup_indices = rng.integers(0, len(query_vectors), size=N_WARMUP)
    for idx in warmup_indices:
        backend.query(query_vectors[idx], k)

    latencies_us = []
    recalls = []

    for i, qv in enumerate(query_vectors):
        t0 = time.perf_counter()
        result = backend.query(qv, k)
        elapsed_us = (time.perf_counter() - t0) * 1_000_000
        latencies_us.append(elapsed_us)
        recalls.append(compute_recall(result, ground_truth[i].tolist()))

    return BenchmarkResult(
        backend_name=backend.name(),
        params=backend.params(),
        build_ms=build_ms,
        recall_at_k=float(np.mean(recalls)),
        latencies_us=latencies_us,
    )


def run_all(
    backends: List[AnnBackend],
    index_vectors: np.ndarray,
    query_vectors: np.ndarray,
    ground_truth: np.ndarray,
    k: int = 10,
) -> List[BenchmarkResult]:
    # Build FAISS-HNSW index once and share across all ef_search configs.
    # All HNSW backends in all_backend_configs() use the same M and ef_construction,
    # so one build covers all of them.
    hnsw_backends = [b for b in backends if isinstance(b, FaissHNSWBackend)]
    if len(hnsw_backends) > 1:
        first = hnsw_backends[0]
        import faiss
        print(f"  Pre-building shared FAISS-HNSW index (M={first.M}, ef_construction={first.ef_construction}) ...",
              end=" ", flush=True)
        t0 = time.perf_counter()
        shared = faiss.IndexHNSWFlat(index_vectors.shape[1], first.M, faiss.METRIC_INNER_PRODUCT)
        shared.hnsw.efConstruction = first.ef_construction
        shared.add(index_vectors)
        build_ms = (time.perf_counter() - t0) * 1000
        print(f"{build_ms:.0f}ms", flush=True)

        for b in hnsw_backends:
            b._shared_index = shared

        # Record the actual build time only on the first config
        hnsw_backends[0]._hnsw_build_ms = build_ms

    results = []
    for backend in backends:
        try:
            result = run_benchmark(backend, index_vectors, query_vectors, ground_truth, k)
            # Restore real build time for the first HNSW config
            if isinstance(backend, FaissHNSWBackend) and hasattr(backend, "_hnsw_build_ms"):
                result.build_ms = backend._hnsw_build_ms
            results.append(result)
        except Exception as e:
            print(f"  ERROR in {backend.name()} {backend.params()}: {e}")
    return results
