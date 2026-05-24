"""
Python reimplementations of cr-mixer blending strategies.

Fixes vs. initial version:
  - count_weighted_blend: replaced broken sequential-drain with a proper
    deficit-accumulator weighted round-robin. take-budget is computed once
    per outer round from the snapshot total, not re-evaluated inside the
    inner loop as pools drain.
  - switch_blend: now meaningfully distinct from ContentSignal(similarity).
    It sorts source pools by freshness (most-recent source_event_time first)
    and then interleaves in that priority order, so fresher sources dominate
    the top of the output.

References:
  cr-mixer/server/src/main/scala/com/twitter/cr_mixer/blender/
"""

import math
import random
from typing import List

from synthetic_data import TweetCandidate, BACKFILL_SOURCE_TYPES


def _dedup(candidates: List[TweetCandidate]) -> List[TweetCandidate]:
    seen = set()
    result = []
    for c in candidates:
        if c.tweet_id not in seen:
            seen.add(c.tweet_id)
            result.append(c)
    return result


# ---------------------------------------------------------------------------
# 1. InterleaveBlender — round-robin across sources
# ---------------------------------------------------------------------------

def interleave_blend(pools: List[List[TweetCandidate]]) -> List[TweetCandidate]:
    active = [list(p) for p in pools if p]
    result = []
    while active:
        next_active = []
        for pool in active:
            if pool:
                result.append(pool.pop(0))
            if pool:
                next_active.append(pool)
        active = next_active
    return _dedup(result)


# ---------------------------------------------------------------------------
# 2. CountWeightedBlender — proportional interleaving by source count
# ---------------------------------------------------------------------------

def count_weighted_blend(
    pools: List[List[TweetCandidate]],
    shrinkage: float = 0.5,
) -> List[TweetCandidate]:
    """
    Deficit-accumulator weighted round-robin.

    shrinkage=0 → fully proportional to initial counts
    shrinkage=1 → uniform (equal weight regardless of count, same as interleave)

    Each "tick" adds weight[i] to counter[i]; a candidate is emitted from
    source i whenever counter[i] crosses 1.0. This correctly produces
    weighted interleaving for unequal pool sizes.
    """
    active = [list(p) for p in pools if p]
    if not active:
        return []

    initial_counts = [len(p) for p in active]
    total = sum(initial_counts)
    n = len(active)
    uniform = 1.0 / n

    weights = [
        (c / total) * (1 - shrinkage) + uniform * shrinkage
        for c in initial_counts
    ]

    counters = [0.0] * n
    result = []

    while any(active):
        for i in range(n):
            if not active[i]:
                continue
            counters[i] += weights[i]
            while counters[i] >= 1.0 and active[i]:
                result.append(active[i].pop(0))
                counters[i] -= 1.0

    return _dedup(result)


# ---------------------------------------------------------------------------
# 3. ContentSignalBlender — flatten + sort by a content signal
# ---------------------------------------------------------------------------

CONTENT_SIGNAL_MODES = ["recency", "fav_count", "similarity", "random"]


def content_signal_blend(
    pools: List[List[TweetCandidate]],
    mode: str = "similarity",
) -> List[TweetCandidate]:
    flat = [c for pool in pools for c in pool]

    if mode == "recency":
        flat.sort(key=lambda c: c.created_at, reverse=True)

    elif mode == "fav_count":
        flat.sort(key=lambda c: c.fav_count, reverse=True)

    elif mode == "similarity":
        # Group by engine type, z-score standardize within each group, then sort.
        # Standardization prevents one engine's score scale from dominating.
        by_engine: dict = {}
        for c in flat:
            by_engine.setdefault(c.engine_type, []).append(c)

        standardized: List[tuple] = []
        for engine_candidates in by_engine.values():
            scores = [c.similarity_score for c in engine_candidates]
            mean = sum(scores) / len(scores)
            variance = sum((s - mean) ** 2 for s in scores) / len(scores)
            std = math.sqrt(variance) if variance > 0 else 1.0
            for c, s in zip(engine_candidates, scores):
                standardized.append(((s - mean) / std, c))

        standardized.sort(key=lambda x: x[0], reverse=True)
        flat = [c for _, c in standardized]

    elif mode == "random":
        random.shuffle(flat)

    return _dedup(flat)


# ---------------------------------------------------------------------------
# 4. SourceTypeBackFillBlender — partition backfill sources, append at end
# ---------------------------------------------------------------------------

def source_type_backfill_blend(
    pools: List[List[TweetCandidate]],
    backfill_types: set = BACKFILL_SOURCE_TYPES,
) -> List[TweetCandidate]:
    regular_pools = []
    backfill_pools = []

    for pool in pools:
        if not pool:
            continue
        if pool[0].source_type in backfill_types:
            backfill_pools.append(pool)
        else:
            regular_pools.append(pool)

    regular = interleave_blend(regular_pools)
    backfill = interleave_blend(backfill_pools)
    return _dedup(regular + backfill)


# ---------------------------------------------------------------------------
# 5. SwitchBlender — freshness-sorted source priority, then interleave
# ---------------------------------------------------------------------------

def switch_blend(
    pools: List[List[TweetCandidate]],
    mode: str = "source_signal_sort",
) -> List[TweetCandidate]:
    """
    Sorts source pools by the recency of their most recent source_event_time
    (freshest source first), then interleaves in that priority order.

    This is distinct from:
    - InterleaveBlender: uses original (arbitrary) pool order
    - ContentSignal(similarity): flattens everything and sorts by score

    The effect: candidates from the source with the freshest signal events
    appear earlier in the output, giving fresher signals a head start in
    the final truncation step.
    """
    active = [p for p in pools if p]

    active.sort(
        key=lambda p: max(c.source_event_time for c in p),
        reverse=True,
    )

    if mode == "round_robin":
        return interleave_blend(active)
    else:
        # source_signal_sort: interleave with freshest-source priority
        return interleave_blend(active)


# ---------------------------------------------------------------------------
# Registry for the A/B eval runner
# ---------------------------------------------------------------------------

ALL_BLENDERS = {
    "Interleave": lambda pools: interleave_blend(pools),
    "CountWeighted(shrink=0.3)": lambda pools: count_weighted_blend(pools, shrinkage=0.3),
    "ContentSignal(similarity)": lambda pools: content_signal_blend(pools, mode="similarity"),
    "ContentSignal(fav_count)": lambda pools: content_signal_blend(pools, mode="fav_count"),
    "ContentSignal(recency)": lambda pools: content_signal_blend(pools, mode="recency"),
    "SourceTypeBackFill": lambda pools: source_type_backfill_blend(pools),
    "Switch(freshness-sort)": lambda pools: switch_blend(pools, mode="source_signal_sort"),
}
