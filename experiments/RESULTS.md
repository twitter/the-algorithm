# Experiment Results & Findings

Two self-contained Python experiments inspired by the X recommendation algorithm. Everything runs on synthetic data — no external servers or APIs required.

- **Experiment 1**: Vector DB benchmark (FAISS, Annoy, Qdrant, brute force)
- **Experiment 2**: A/B eval of cr-mixer blending strategies

Both experiments went through a deep bias-analysis pass after the first run, which surfaced 10 distinct issues across both. The results below are from the fixed versions.

---

## Experiment 1 — Vector DB Benchmark

### Setup

- **Dataset**: 50,000 index vectors + 1,000 query vectors, 128-dim, L2-normalized
- **Data structure**: 50 Gaussian clusters, `cluster_std=0.3` on the unit sphere
- **Ground truth**: exact brute-force cosine top-10 per query
- **Metric**: recall@10, build time (ms), p50/p95/p99 query latency (µs)
- **Warmup**: 50 queries before timed loop

### Backends Compared

| Backend | Index type | Params swept |
|---|---|---|
| `BruteForce` | NumPy dot product | (none — exact) |
| `FAISS-IVFFlat` | IVF + flat lists | `nprobe ∈ {1, 4, 16, 64}` |
| `FAISS-HNSW` | HNSW | `ef_search ∈ {16, 32, 64, 128}` |
| `Annoy` | Forest of random projection trees | `n_trees ∈ {10, 50, 100}` × `search_k ∈ {1k, 5k, 10k}` |
| `Qdrant(local-exact)` | (see note below) | `ef ∈ {16, 64, 128}` |

### Raw Results

```
| Backend            | Params                                   | Build(ms) | Recall@10 | p50(us) | p95(us) | p99(us) |
|--------------------|------------------------------------------|-----------|-----------|---------|---------|---------|
| BruteForce         | (none)                                   |        13 |    1.0000 |    2375 |    3586 |    5115 |
| FAISS-IVFFlat      | nlist=100, nprobe=1                      |       234 |    0.2215 |      68 |     101 |     263 |
| FAISS-IVFFlat      | nlist=100, nprobe=4                      |       230 |    0.3742 |     129 |     220 |     428 |
| FAISS-IVFFlat      | nlist=100, nprobe=16                     |       225 |    0.6282 |     469 |     731 |    1196 |
| FAISS-IVFFlat      | nlist=100, nprobe=64                     |       242 |    0.9485 |    1706 |    2684 |    4132 |
| FAISS-HNSW         | M=32, ef_construction=200, ef_search=16  |     30768 |    0.3181 |     233 |     577 |    1430 |
| FAISS-HNSW         | M=32, ef_construction=200, ef_search=32  |         0 |    0.4815 |     412 |    1075 |    1979 |
| FAISS-HNSW         | M=32, ef_construction=200, ef_search=64  |         0 |    0.6570 |     669 |    1364 |    2755 |
| FAISS-HNSW         | M=32, ef_construction=200, ef_search=128 |         0 |    0.8325 |    1144 |    2075 |    3854 |
| Annoy              | n_trees=10, search_k=1000                |      1377 |    0.1286 |     339 |     563 |     967 |
| Annoy              | n_trees=10, search_k=5000                |      1327 |    0.3881 |    1413 |    2115 |    3485 |
| Annoy              | n_trees=10, search_k=10000               |      1336 |    0.5659 |    2553 |    3967 |    5547 |
| Annoy              | n_trees=50, search_k=1000                |      4837 |    0.1604 |     374 |     616 |    1480 |
| Annoy              | n_trees=50, search_k=5000                |      5082 |    0.4778 |    1476 |    2437 |    4040 |
| Annoy              | n_trees=50, search_k=10000               |      4409 |    0.6745 |    2554 |    3674 |    5803 |
| Annoy              | n_trees=100, search_k=1000               |      8293 |    0.1820 |     420 |     617 |     940 |
| Annoy              | n_trees=100, search_k=5000               |      8221 |    0.5263 |    1531 |    2103 |    2729 |
| Annoy              | n_trees=100, search_k=10000              |      8296 |    0.7210 |    2629 |    3525 |    4534 |
| Qdrant(local-exact)| m=16, ef=16                              |    247446 |    1.0000 |   56977 |   63821 |  107643 |
| Qdrant(local-exact)| m=16, ef=64                              |    249983 |    1.0000 |   84703 |  127661 |  152191 |
| Qdrant(local-exact)| m=16, ef=128                             |    646374 |    1.0000 |   53032 |   59967 |   67113 |
```

> **Note on FAISS-HNSW build times**: only the first config shows the real build time (~31s) because the index is identical across the ef_search sweep (`ef_search` is a query-time parameter). The benchmark builds it once and shares it.

> **Note on Qdrant**: the Python `qdrant-client` in both `:memory:` mode and path-based local mode falls back to **exact brute-force search** regardless of HNSW config. The `recall=1.0` at every ef setting confirms this. To benchmark Qdrant's real HNSW you need a Docker/cloud Qdrant server.

### Findings

**1. FAISS-IVFFlat dominates the Pareto frontier on this data.**
- At nprobe=64: recall=0.95 at p50=1.7ms — best recall/latency tradeoff in the table.
- At nprobe=1: recall=0.22 at p50=68µs — clear order-of-magnitude latency win when low recall is acceptable.

**2. FAISS-HNSW gives a smoother recall curve but is consistently more expensive than IVFFlat at matched recall.**
- HNSW at ef=128 hits 0.83 recall at 1.1ms p50; IVFFlat at nprobe=64 hits 0.95 at 1.7ms.
- For this dataset, the heavy ~31s HNSW build cost doesn't pay off — IVFFlat builds in ~230ms and beats it on the frontier.

**3. Annoy is the slowest at any recall level.**
- 0.72 recall at p50=2.6ms is its best showing — strictly worse than FAISS at the same recall.
- Annoy's tree-based index also has the longest build times (up to 8s for n_trees=100).

**4. Qdrant local mode is unusable for benchmarking ANN.**
- The Python client local mode does exact search, so `recall=1.0` is technically correct but tells us nothing about HNSW behavior.
- Latency is ~55ms p50 — 22x slower than NumPy brute force for the same exact result, all due to Python/serialization overhead.
- **Recommendation**: if Qdrant performance matters, run it as a real server (Docker) and re-run the benchmark.

**5. Cluster structure makes the problem easier than real production embeddings.**
- 50 well-separated clusters in 128d mean "find the 10 nearest" usually means "find 10 within the same cluster", which graph/IVF methods solve trivially at moderate parameters.
- The recall ceiling for production data (TwHIN, SimClusters) would likely show a flatter and harder Pareto curve. A future variant should sweep `cluster_std` to characterize this.

---

## Experiment 2 — Blending Strategy A/B Eval

### Setup

- **Pool**: 5 candidate sources with variable sizes `[60, 40, 30, 50, 20]` = 200 candidates per trial
- **~8 duplicate tweet IDs** per trial (15% overlap) — exercises blender dedup
- **Authors**: each source has a "home cohort" of 12 authors; 75% probability of cohort author
- **`fav_count`**: log-normal base × `lognormal(mean = similarity_score × 2.5, σ=0.8)` — positive but noisy correlation with relevance
- **`similarity_score`**: Beta(2, 5) — skewed low, range (0, 1)
- **Trials**: 50, each with a freshly seeded pool
- **Top-K evaluated**: 50

### Blenders Compared

| Blender | Strategy |
|---|---|
| `Interleave` | Round-robin across sources in original pool order |
| `CountWeighted(shrink=0.3)` | Deficit-accumulator weighted round-robin; larger pools get more slots |
| `ContentSignal(similarity)` | Flatten + z-score per engine type + sort by score |
| `ContentSignal(fav_count)` | Flatten + sort by raw fav_count |
| `ContentSignal(recency)` | Flatten + sort by `created_at` |
| `SourceTypeBackFill` | Interleave non-backfill sources first, then append `profile_visit`+`video` |
| `Switch(freshness-sort)` | Sort pools by most-recent `source_event_time` then interleave |

### Raw Results (mean ± std over 50 trials)

```
| Blender                   | author_div    | src_entropy   | src_concentration | mean_relevance | top10_relevance | log_fav_count | recency_hrs    |
|---------------------------|---------------|---------------|-------------------|----------------|-----------------|---------------|----------------|
| Interleave                | 0.5748±0.0447 | 0.9994±0.0008 | 0.2076±0.0097     | 0.4925±0.0183  | 0.6250±0.0334   | 5.7015±0.2318 | 83.1121±7.1630 |
| CountWeighted(shrink=0.3) | 0.5628±0.0454 | 0.9774±0.0042 | 0.2760±0.0098     | 0.5020±0.0182  | 0.6320±0.0315   | 5.7109±0.2447 | 83.2158±7.1353 |
| ContentSignal(similarity) | 0.5596±0.0477 | 0.9477±0.0237 | 0.3100±0.0422     | 0.5062±0.0184  | 0.6450±0.0320   | 5.7243±0.2335 | 83.6031±7.5813 |
| ContentSignal(fav_count)  | 0.5532±0.0494 | 0.9431±0.0302 | 0.3148±0.0394     | 0.3380±0.0231  | 0.3727±0.0501   | 7.3981±0.1782 | 84.6310±7.2147 |
| ContentSignal(recency)    | 0.5524±0.0379 | 0.9363±0.0290 | 0.3312±0.0477     | 0.2888±0.0226  | 0.2948±0.0535   | 5.2106±0.2420 | 22.8725±2.9052 |
| SourceTypeBackFill        | 0.5164±0.0507 | 0.6823±0.0001 | 0.3408±0.0039     | 0.4437±0.0173  | 0.6043±0.0282   | 5.5792±0.2344 | 83.1037±7.2209 |
| Switch(freshness-sort)    | 0.5748±0.0430 | 0.9995±0.0007 | 0.2076±0.0097     | 0.4921±0.0180  | 0.6244±0.0328   | 5.6988±0.2349 | 83.2553±7.2150 |
```

**Metric guide:**
- `author_div`: fraction of unique authors in top-50 (higher = more diverse)
- `src_entropy`: normalized Shannon entropy of source distribution (1.0 = perfectly balanced)
- `src_concentration`: max fraction of top-50 from any single source (lower = more balanced)
- `mean_relevance`, `top10_relevance`: average similarity score (higher = more relevant)
- `log_fav_count`: average `log(1 + fav_count)` (higher = more engagement)
- `recency_hrs`: average hours since creation (lower = fresher feed)

### Findings

**1. There is a clean diversity-vs-relevance Pareto frontier.**
- `Interleave` / `Switch(freshness-sort)` hit highest source diversity (entropy=0.999) but middle-of-the-pack relevance (0.49).
- `ContentSignal(similarity)` hits highest relevance (0.51) at the cost of source balance (entropy=0.95, concentration=0.31).
- `CountWeighted(shrink=0.3)` sits cleanly between them — slight relevance bump (0.50) at slight diversity cost (entropy=0.98).

**2. `SourceTypeBackFill` does exactly what it's designed to do — and it shows.**
- Lowest source entropy (0.682) and lowest author diversity (0.516) because the top of the feed is dominated by `follow`/`engagement`/`topic` while `profile_visit`/`video` are pushed to the end.
- This is intentional: backfill sources are lower-confidence signals that should rank after the high-confidence ones. But it has a clear diversity cost.

**3. `ContentSignal(fav_count)` has the strongest engagement signal but worst relevance.**
- `log_fav_count = 7.40` vs ~5.7 for everyone else.
- `mean_relevance = 0.338` — significantly below others (0.49–0.51).
- Even with the correlation we built in (fav_count boosted by similarity), pure engagement-ranking still trades off ~33% of relevance. **This is the classic engagement-vs-quality tension** that real recommendation systems struggle with.

**4. `ContentSignal(recency)` gives a 73% fresher feed at a 43% relevance cost.**
- `recency_hrs = 22.9` vs ~84 for everyone else — by far the freshest feed.
- But `mean_relevance = 0.289` — the lowest of any blender.
- Pure recency ranking is the most extreme tradeoff in the table.

**5. `CountWeighted(shrink=0.3)` correctly oversamples larger pools.**
- `src_concentration = 0.276` vs `0.208` for plain Interleave — proves it's pulling more from source 0 (60 candidates) and source 3 (50) than from source 4 (20).
- Without variable pool sizes, this blender is mathematically equivalent to Interleave. The fix to make pool sizes variable was necessary to evaluate it.

**6. `Switch(freshness-sort)` is barely distinguishable from `Interleave` in this dataset.**
- All metrics within noise (`0.5748±0.045` vs `0.5748±0.044` for author diversity).
- Pool order matters mainly for tie-breaking in dedup, and with only ~8 duplicates per trial there's little for the source ordering to influence.
- In a higher-overlap scenario (e.g., 40% overlap), the freshness ordering would matter more.

---

## Deep Bias Analysis: What Was Wrong Initially

After the first run produced suspiciously clean results, both experiments were audited. **10 issues were identified and fixed**:

### Vector DB Benchmark

| # | Severity | Issue | Fix |
|---|---|---|---|
| 1 | CRITICAL | Qdrant `:memory:` uses exact brute-force, not HNSW (recall=1.0 at every ef confirms) | Switched to path-based client; confirmed same issue; renamed backend to `Qdrant(local-exact)` |
| 2 | CRITICAL | FAISS-HNSW index rebuilt from scratch for each `ef_search` value (it's a query-time param — same index works for all) | Build once in `run_all`, inject via `_shared_index`; only first config reports real build time |
| 3 | CRITICAL | No warmup queries → cold CPU cache inflates p99 latency | 50 warmup queries before timed loop |
| 4 | SIGNIFICANT | FAISS returns `-1` sentinels for unfound neighbors; these were passed into recall set intersection | Filter `i >= 0` in both FAISS backends |

### Blending Eval

| # | Severity | Issue | Fix |
|---|---|---|---|
| 5 | CRITICAL | `_snowflake_id(created_at, seq=tid)` was called with a **fresh `created_at` per candidate**, so the same logical `tid` got a different `tweet_id` in every source pool. **Dedup never fired.** | Pre-generate `(tweet_id, created_at)` per tid before distributing to sources |
| 6 | CRITICAL | `count_weighted_blend` computed `take = round(weight * total_remaining)` inside the inner loop. With 5 equal pools of 40 each, this evaluated to `take=40` on the first iteration → drained source 0 entirely, then source 1, etc. **Sequential concatenation, not weighted interleaving.** | Replaced with deficit accumulator: `counter[i] += weight[i]` per tick, emit when ≥ 1.0 |
| 7 | SIGNIFICANT | `author_id` was uniform-random independent of source, so `author_diversity` was a birthday-problem constant (~0.64 for all blenders) | Each source has a home cohort of 12 authors with 75% probability |
| 8 | SIGNIFICANT | `source_diversity = unique_types / 50` and `engine_diversity = unique_engines / 50` were saturated — every blender trivially hit all 5/3 types | Replaced with **normalized Shannon entropy** (`src_entropy`) and **max-fraction concentration** (`src_concentration`) |
| 9 | SIGNIFICANT | `fav_count` and `similarity_score` were independent — the `ContentSignal(fav_count)` vs `ContentSignal(similarity)` comparison was testing signal orthogonality, not signal quality | `fav_count = base_fav × lognormal(mean = similarity × 2.5, σ=0.8)` — positive but noisy correlation |
| 10 | MODERATE | `switch_blend` sorted pools then called `content_signal_blend` which flattens everything — **functionally identical to `ContentSignal(similarity)`** | Now sorts pools by freshness then calls `interleave_blend`, so pool order is preserved |

### What the bugs cost us in the first run

Before fixes, the table showed `author_div ≈ 0.64` for every blender — making it look like blender choice didn't affect author diversity. **This was an artifact of uniform random author assignment, not a real result.** After correlating authors with sources, the same metric spans 0.516–0.575 with meaningful differences.

Similarly, `CountWeighted` looked nearly identical to `Interleave` — but that was because the broken implementation was running sequential concatenation, not weighted round-robin. After the fix and with variable pool sizes, it shows a real ~7-point concentration difference.

---

## Conclusions

**Vector DB**: For clustered embeddings of this scale, **FAISS-IVFFlat is the practical winner**. It builds fastest, has the cleanest recall-vs-latency curve, and offers easy tuning via `nprobe`. FAISS-HNSW is competitive but expensive to build and slightly worse at matched recall. Annoy is strictly dominated. Qdrant local mode cannot be used to benchmark ANN — it does exact search.

**Blending**: The cr-mixer blender zoo represents real engineering tradeoffs. **No single blender wins on all metrics**, which is exactly why the production system uses a feature-flag-routable `SwitchBlender` to pick per surface. The biggest takeaways:
- Pure engagement ranking (`fav_count`) sacrifices significant relevance even with realistic correlation.
- Pure recency ranking sacrifices even more.
- Round-robin interleaving maximizes source diversity but doesn't maximize relevance.
- Weighted interleaving (`CountWeighted`) is a reasonable middle ground when source pools are unequal.

**Methodology**: This whole exercise reinforced that **synthetic-data benchmarks can produce false confidence**. Both experiments initially produced "clean" results that were entirely artifacts of correlated bugs and uniform-random independence assumptions. The deep audit caught 10 issues; the fixed results tell a much more nuanced story.
