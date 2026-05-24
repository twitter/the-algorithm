"""
Metrics computed on the top-K output of a blender.

Fixes vs. initial version:
  - source_diversity replaced with source_entropy (normalized Shannon entropy
    of the source-type distribution in top-k). Counts how evenly spread
    sources are, not just how many unique ones appear (which was saturated).
  - engine_diversity replaced with source_concentration (max fraction of
    top-k from any single source type). High = one source dominates.
  - mean_recency_hours accepts reference_time param so the caller can pin
    the reference to pool generation time rather than eval time.
"""

import math
from collections import Counter
from datetime import datetime
from typing import List, Optional

from synthetic_data import TweetCandidate, SOURCE_TYPES

TOP_K = 50


def author_diversity(candidates: List[TweetCandidate], k: int = TOP_K) -> float:
    """Fraction of unique author IDs in top-k (birthday-problem baseline ~0.63 for 50 authors)."""
    top = candidates[:k]
    if not top:
        return 0.0
    return len({c.author_id for c in top}) / len(top)


def source_entropy(candidates: List[TweetCandidate], k: int = TOP_K) -> float:
    """
    Normalized Shannon entropy of the source-type distribution in top-k.
    1.0 = perfectly uniform across all source types.
    0.0 = all candidates from one source type.
    Unlike a raw unique-count, this distinguishes "5 types at 10% each" from
    "5 types but one dominates at 80%".
    """
    top = candidates[:k]
    if not top:
        return 0.0
    counts = Counter(c.source_type for c in top)
    n = len(top)
    n_types = len(SOURCE_TYPES)
    entropy = -sum((v / n) * math.log(v / n) for v in counts.values() if v > 0)
    max_entropy = math.log(n_types) if n_types > 1 else 1.0
    return entropy / max_entropy


def source_concentration(candidates: List[TweetCandidate], k: int = TOP_K) -> float:
    """
    Fraction of top-k from the single most-represented source type.
    High = one source dominates; low = balanced.
    Complements source_entropy: entropy measures spread, concentration flags
    the worst-case source imbalance.
    """
    top = candidates[:k]
    if not top:
        return 0.0
    counts = Counter(c.source_type for c in top)
    return max(counts.values()) / len(top)


def mean_relevance(candidates: List[TweetCandidate], k: int = TOP_K) -> float:
    top = candidates[:k]
    if not top:
        return 0.0
    return sum(c.similarity_score for c in top) / len(top)


def top10_relevance(candidates: List[TweetCandidate]) -> float:
    return mean_relevance(candidates, k=10)


def mean_log_fav_count(candidates: List[TweetCandidate], k: int = TOP_K) -> float:
    top = candidates[:k]
    if not top:
        return 0.0
    return sum(math.log1p(c.fav_count) for c in top) / len(top)


def mean_recency_hours(
    candidates: List[TweetCandidate],
    k: int = TOP_K,
    reference_time: Optional[datetime] = None,
) -> float:
    """Mean age of top-k candidates in hours. Lower = fresher feed."""
    top = candidates[:k]
    if not top:
        return 0.0
    now = reference_time if reference_time is not None else datetime.utcnow()
    return sum((now - c.created_at).total_seconds() / 3600 for c in top) / len(top)


ALL_METRICS = {
    "author_div": author_diversity,
    "src_entropy": source_entropy,
    "src_concentration": source_concentration,
    "mean_relevance": mean_relevance,
    "top10_relevance": top10_relevance,
    "log_fav_count": mean_log_fav_count,
    "recency_hrs": mean_recency_hours,
}
