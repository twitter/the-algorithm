"""
Unified ANN backend interface + implementations for FAISS, Annoy, Qdrant,
and a NumPy brute-force baseline.

All backends use cosine similarity (dot product on unit vectors).
"""

import shutil
import tempfile
import time
from abc import ABC, abstractmethod
from typing import List, Optional

import numpy as np


class AnnBackend(ABC):
    @abstractmethod
    def build(self, vectors: np.ndarray) -> float:
        """Build/train the index. Returns build time in ms."""

    @abstractmethod
    def query(self, vector: np.ndarray, k: int) -> List[int]:
        """Return indices of k approximate nearest neighbors."""

    @abstractmethod
    def name(self) -> str: ...

    @abstractmethod
    def params(self) -> dict: ...


# ---------------------------------------------------------------------------
# Brute Force (exact baseline)
# ---------------------------------------------------------------------------

class BruteForceBackend(AnnBackend):
    def __init__(self):
        self._vectors = None

    def build(self, vectors: np.ndarray) -> float:
        t0 = time.perf_counter()
        self._vectors = vectors.copy()
        return (time.perf_counter() - t0) * 1000

    def query(self, vector: np.ndarray, k: int) -> List[int]:
        sims = self._vectors @ vector
        top_k = np.argpartition(sims, -k)[-k:]
        return top_k[np.argsort(sims[top_k])[::-1]].tolist()

    def name(self) -> str:
        return "BruteForce"

    def params(self) -> dict:
        return {}


# ---------------------------------------------------------------------------
# FAISS IVFFlat
# ---------------------------------------------------------------------------

class FaissIVFFlatBackend(AnnBackend):
    def __init__(self, nlist: int = 100, nprobe: int = 16):
        self.nlist = nlist
        self.nprobe = nprobe
        self._index = None

    def build(self, vectors: np.ndarray) -> float:
        import faiss
        t0 = time.perf_counter()
        dim = vectors.shape[1]
        quantizer = faiss.IndexFlatIP(dim)
        self._index = faiss.IndexIVFFlat(quantizer, dim, self.nlist, faiss.METRIC_INNER_PRODUCT)
        self._index.train(vectors)
        self._index.add(vectors)
        self._index.nprobe = self.nprobe
        return (time.perf_counter() - t0) * 1000

    def query(self, vector: np.ndarray, k: int) -> List[int]:
        self._index.nprobe = self.nprobe
        _, indices = self._index.search(vector.reshape(1, -1), k)
        # FAISS returns -1 as a sentinel for unfound neighbors; strip them
        return [i for i in indices[0].tolist() if i >= 0]

    def name(self) -> str:
        return "FAISS-IVFFlat"

    def params(self) -> dict:
        return {"nlist": self.nlist, "nprobe": self.nprobe}


# ---------------------------------------------------------------------------
# FAISS HNSW
# ---------------------------------------------------------------------------

class FaissHNSWBackend(AnnBackend):
    def __init__(
        self,
        M: int = 32,
        ef_construction: int = 200,
        ef_search: int = 64,
        _shared_index=None,
    ):
        self.M = M
        self.ef_construction = ef_construction
        self.ef_search = ef_search
        # When a shared index is injected (by run_all to avoid 4x rebuilds),
        # build() skips construction and records 0ms.
        self._shared_index = _shared_index
        self._index = None

    def build(self, vectors: np.ndarray) -> float:
        if self._shared_index is not None:
            self._index = self._shared_index
            return 0.0

        import faiss
        t0 = time.perf_counter()
        dim = vectors.shape[1]
        self._index = faiss.IndexHNSWFlat(dim, self.M, faiss.METRIC_INNER_PRODUCT)
        self._index.hnsw.efConstruction = self.ef_construction
        self._index.hnsw.efSearch = self.ef_search
        self._index.add(vectors)
        return (time.perf_counter() - t0) * 1000

    def query(self, vector: np.ndarray, k: int) -> List[int]:
        self._index.hnsw.efSearch = self.ef_search
        _, indices = self._index.search(vector.reshape(1, -1), k)
        return [i for i in indices[0].tolist() if i >= 0]

    def name(self) -> str:
        return "FAISS-HNSW"

    def params(self) -> dict:
        return {"M": self.M, "ef_construction": self.ef_construction, "ef_search": self.ef_search}


# ---------------------------------------------------------------------------
# Annoy
# ---------------------------------------------------------------------------

class AnnoyBackend(AnnBackend):
    def __init__(self, n_trees: int = 50, search_k: int = 5000):
        self.n_trees = n_trees
        self.search_k = search_k
        self._index = None
        self._dim = None

    def build(self, vectors: np.ndarray) -> float:
        from annoy import AnnoyIndex
        t0 = time.perf_counter()
        self._dim = vectors.shape[1]
        self._index = AnnoyIndex(self._dim, "dot")
        for i, v in enumerate(vectors):
            self._index.add_item(i, v.tolist())
        self._index.build(self.n_trees)
        return (time.perf_counter() - t0) * 1000

    def query(self, vector: np.ndarray, k: int) -> List[int]:
        return self._index.get_nns_by_vector(
            vector.tolist(), k, search_k=self.search_k
        )

    def name(self) -> str:
        return "Annoy"

    def params(self) -> dict:
        return {"n_trees": self.n_trees, "search_k": self.search_k}


# ---------------------------------------------------------------------------
# Qdrant (on-disk local path, no server — enables real HNSW)
# ---------------------------------------------------------------------------

class QdrantBackend(AnnBackend):
    def __init__(self, ef: int = 64, m: int = 16):
        self.ef = ef
        self.m = m
        self._client = None
        self._collection = "bench"
        self._tmp_dir: Optional[str] = None

    def build(self, vectors: np.ndarray) -> float:
        from qdrant_client import QdrantClient
        from qdrant_client.models import Distance, VectorParams, PointStruct, HnswConfigDiff

        # Use a temp directory so Qdrant runs its full HNSW stack rather
        # than the exact-search fallback used by the ":memory:" client.
        if self._tmp_dir is None:
            self._tmp_dir = tempfile.mkdtemp(prefix="qdrant_bench_")

        t0 = time.perf_counter()
        self._client = QdrantClient(path=self._tmp_dir)
        dim = vectors.shape[1]

        try:
            self._client.delete_collection(self._collection)
        except Exception:
            pass
        self._client.create_collection(
            collection_name=self._collection,
            vectors_config=VectorParams(size=dim, distance=Distance.COSINE),
            hnsw_config=HnswConfigDiff(m=self.m, ef_construct=self.ef),
        )

        batch_size = 1000
        for start in range(0, len(vectors), batch_size):
            end = min(start + batch_size, len(vectors))
            points = [
                PointStruct(id=int(i), vector=vectors[i].tolist())
                for i in range(start, end)
            ]
            self._client.upsert(collection_name=self._collection, points=points)

        return (time.perf_counter() - t0) * 1000

    def query(self, vector: np.ndarray, k: int) -> List[int]:
        from qdrant_client.models import SearchParams
        try:
            results = self._client.query_points(
                collection_name=self._collection,
                query=vector.tolist(),
                limit=k,
                search_params=SearchParams(hnsw_ef=self.ef),
            ).points
            return [r.id for r in results]
        except AttributeError:
            results = self._client.search(
                collection_name=self._collection,
                query_vector=vector.tolist(),
                limit=k,
                search_params=SearchParams(hnsw_ef=self.ef),
            )
            return [r.id for r in results]

    def __del__(self):
        if self._tmp_dir:
            try:
                shutil.rmtree(self._tmp_dir, ignore_errors=True)
            except Exception:
                pass

    def name(self) -> str:
        # The Python qdrant-client local mode (both :memory: and path-based)
        # performs exact nearest-neighbor search regardless of HNSW config.
        # recall=1.0 at every ef setting confirms this. A real Qdrant server
        # (Docker/Cloud) is required to benchmark true HNSW behavior.
        return "Qdrant(local-exact)"

    def params(self) -> dict:
        return {"m": self.m, "ef": self.ef}


# ---------------------------------------------------------------------------
# Factory helpers
# ---------------------------------------------------------------------------

def all_backend_configs() -> List[AnnBackend]:
    """Returns one backend instance per param config to benchmark."""
    configs = []

    configs.append(BruteForceBackend())

    for nprobe in [1, 4, 16, 64]:
        configs.append(FaissIVFFlatBackend(nlist=100, nprobe=nprobe))

    # HNSW: shared index will be injected by run_all() to avoid 4x rebuilds.
    # ef_construction and M must match across all configs in this group.
    for ef_search in [16, 32, 64, 128]:
        configs.append(FaissHNSWBackend(M=32, ef_construction=200, ef_search=ef_search))

    for n_trees in [10, 50, 100]:
        for search_k in [1000, 5000, 10000]:
            configs.append(AnnoyBackend(n_trees=n_trees, search_k=search_k))

    for ef in [16, 64, 128]:
        configs.append(QdrantBackend(ef=ef, m=16))

    return configs
