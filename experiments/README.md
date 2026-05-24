# Experiments

Two self-contained Python experiments inspired by the X recommendation algorithm. All experiments use synthetic data — no servers or external APIs required.

## Quick Start

```bash
cd experiments
pip install -r requirements.txt

# Vector DB benchmark (~10 min for full HNSW+Qdrant run)
python vector_db_benchmark/run.py

# Blending strategy A/B eval (~30 sec)
python blending_eval/run.py
```

Each experiment writes:
- A tabular summary to stdout
- PNG plots to `<exp>/results/`
- Raw stats text to `<exp>/results/raw_stats.txt`

## Experiments

### 1. Vector DB Benchmark — `vector_db_benchmark/`

Compares FAISS-IVFFlat, FAISS-HNSW, Annoy, Qdrant (local), and a NumPy brute-force baseline on recall@10 and query latency over a synthetic 50K-vector dataset.

### 2. Blending A/B Eval — `blending_eval/`

Python reimplementations of the 7 candidate-blending strategies from `cr-mixer/`, compared on synthetic candidate pools across diversity, relevance, engagement, and recency metrics.

## Results & Findings

See [`RESULTS.md`](RESULTS.md) for the full writeup, including:
- Raw result tables for both experiments
- Detailed findings per blender / per backend
- Deep bias analysis: 10 issues found and fixed in the initial implementation
- Conclusions about when each strategy is appropriate

## File Layout

```
experiments/
  README.md                 ← this file
  RESULTS.md                ← full writeup & conclusions
  requirements.txt
  vector_db_benchmark/
    run.py                  ← entry point
    synthetic_data.py       ← clustered Gaussian embeddings
    backends.py             ← unified ANN backend interface
    benchmark.py            ← timing + recall measurement
    report.py               ← table + PNG output
    results/
      raw_stats.txt
      recall_vs_latency.png
      latency_distribution.png
  blending_eval/
    run.py
    synthetic_data.py       ← synthetic TweetCandidate pools
    blenders.py             ← 7 cr-mixer-style blenders
    metrics.py              ← diversity / relevance / engagement metrics
    ab_eval.py              ← 50-trial A/B runner
    report.py
    results/
      raw_stats.txt
      blender_comparison.png
      diversity_vs_relevance.png
```
