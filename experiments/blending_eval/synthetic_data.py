"""
Synthetic tweet candidate data for blending strategy evaluation.

Fixes vs. initial version:
  - Tweet ID overlap: tweet metadata (tweet_id, created_at, fav_count) is
    pre-generated per logical tweet and reused across sources, so the same
    tweet truly gets the same tweet_id in every pool it appears in.
  - Author correlation: each source has a "home" author cohort; candidates
    from that source are 75% likely to come from its cohort authors. This
    makes blenders that oversample one source show lower author diversity.
  - fav/sim correlation: fav_count scales with similarity_score so
    engagement-based vs. relevance-based sorting produce meaningfully
    different orderings with realistic signal overlap.
  - Variable pool sizes: sources produce [60, 40, 30, 50, 20] candidates,
    making CountWeightedBlender behave distinctly from InterleaveBlender.
"""

import random
from dataclasses import dataclass
from datetime import datetime, timedelta
from typing import Dict, List, Tuple

import numpy as np

SOURCE_TYPES = ["follow", "engagement", "topic", "profile_visit", "video"]
ENGINE_TYPES = ["twhin", "simclusters", "realGraph"]
BACKFILL_SOURCE_TYPES = {"profile_visit", "video"}

N_AUTHORS = 50
AUTHORS_PER_SOURCE = 12   # home cohort size per source (overlapping is fine)

# Default pool sizes when n_sources=5; sum = 200 total candidates
DEFAULT_POOL_SIZES = [60, 40, 30, 50, 20]


@dataclass
class TweetCandidate:
    tweet_id: int
    fav_count: int
    author_id: int
    source_type: str
    engine_type: str
    similarity_score: float
    source_event_time: datetime
    created_at: datetime

    def __hash__(self):
        return hash(self.tweet_id)

    def __eq__(self, other):
        return isinstance(other, TweetCandidate) and self.tweet_id == other.tweet_id


def _snowflake_id(created_at: datetime, tid: int) -> int:
    epoch_ms = int(created_at.timestamp() * 1000) - 1288834974657
    return (epoch_ms << 22) | (tid & 0x3FFFFF)


def _build_source_author_cohorts(rng: np.random.Generator, n_sources: int) -> List[List[int]]:
    """Each source has a home cohort of AUTHORS_PER_SOURCE authors, drawn with replacement."""
    cohorts = []
    for _ in range(n_sources):
        cohort = rng.integers(0, N_AUTHORS, size=AUTHORS_PER_SOURCE).tolist()
        cohorts.append(cohort)
    return cohorts


def _generate_tweet_metadata(
    tids: List[int],
    now: datetime,
    rng: np.random.Generator,
) -> Dict[int, Tuple[int, datetime, int]]:
    """
    Pre-generate (tweet_id, created_at, base_fav_count) for each logical tid.
    Reusing this dict for shared candidates ensures tweet_id is identical
    across all source pools that include the same tweet.
    """
    meta = {}
    for tid in tids:
        age_days = float(rng.uniform(0, 7))
        created_at = now - timedelta(days=age_days)
        tweet_id = _snowflake_id(created_at, tid)
        # Base engagement: log-normal, clipped. Will be boosted per source by similarity.
        raw_fav = float(rng.lognormal(mean=4.5, sigma=1.5))
        base_fav = int(min(max(raw_fav, 0), 100_000))
        meta[tid] = (tweet_id, created_at, base_fav)
    return meta


def generate_candidate_pool(
    n_sources: int = 5,
    candidates_per_source: int = 40,
    overlap_fraction: float = 0.15,
    pool_sizes: List[int] = None,
    seed: int = None,
) -> List[List[TweetCandidate]]:
    """
    Returns n_sources lists of TweetCandidate, sorted descending by
    similarity_score within each source.

    pool_sizes: explicit per-source candidate counts. When provided,
    candidates_per_source is ignored. Variable sizes make CountWeightedBlender
    behave differently from InterleaveBlender.

    Shared tweets: a fraction (overlap_fraction) of tweet IDs appear in
    multiple source pools with the same tweet_id and created_at, so
    blender deduplication actually fires.
    """
    if pool_sizes is None:
        if n_sources == 5:
            pool_sizes = DEFAULT_POOL_SIZES
        else:
            pool_sizes = [candidates_per_source] * n_sources

    rng = np.random.default_rng(seed)
    py_rng = random.Random(seed)
    now = datetime.utcnow()

    # Build a global pool of tweet IDs.
    # Shared IDs: will be distributed across multiple sources.
    total_slots = sum(pool_sizes)
    n_shared_ids = max(1, int(total_slots * overlap_fraction))
    # Unique IDs are numbered starting from 1; shared IDs occupy the first n_shared_ids.
    shared_ids = list(range(1, n_shared_ids + 1))
    next_unique_id = n_shared_ids + 1

    # Pre-generate ALL tweet metadata up front so shared tweets have
    # the same tweet_id and created_at regardless of which source sees them.
    all_tids_needed = list(range(1, next_unique_id + total_slots))
    tweet_meta = _generate_tweet_metadata(all_tids_needed, now, rng)

    # Per-source author cohorts: each source skews toward its home authors
    cohorts = _build_source_author_cohorts(rng, n_sources)

    pools = []
    unique_id_cursor = next_unique_id

    for source_idx in range(n_sources):
        n_cands = pool_sizes[source_idx]
        source_type = SOURCE_TYPES[source_idx % len(SOURCE_TYPES)]
        engine_type = ENGINE_TYPES[source_idx % len(ENGINE_TYPES)]
        home_cohort = cohorts[source_idx]

        n_from_shared = min(int(n_cands * overlap_fraction), len(shared_ids))
        shared_sample = py_rng.sample(shared_ids, n_from_shared)
        n_unique = n_cands - n_from_shared
        unique_sample = list(range(unique_id_cursor, unique_id_cursor + n_unique))
        unique_id_cursor += n_unique

        tids_for_source = shared_sample + unique_sample
        py_rng.shuffle(tids_for_source)

        scores = rng.beta(2, 5, size=len(tids_for_source)).tolist()
        scores.sort(reverse=True)

        candidates = []
        for tid, score in zip(tids_for_source, scores):
            tweet_id, created_at, base_fav = tweet_meta[tid]

            # fav_count positively correlated with similarity_score:
            # high-similarity tweets get a multiplicative engagement boost.
            # noise keeps them from being perfectly correlated.
            sim_boost = float(rng.lognormal(mean=score * 2.5, sigma=0.8))
            fav_count = int(min(base_fav * sim_boost, 100_000))

            src_age_days = float(rng.uniform(0, 30))
            source_event_time = now - timedelta(days=src_age_days)

            # Author: 75% chance of home cohort, 25% any author
            if rng.random() < 0.75:
                author_id = int(py_rng.choice(home_cohort))
            else:
                author_id = int(rng.integers(0, N_AUTHORS))

            candidates.append(TweetCandidate(
                tweet_id=tweet_id,
                fav_count=fav_count,
                author_id=author_id,
                source_type=source_type,
                engine_type=engine_type,
                similarity_score=score,
                source_event_time=source_event_time,
                created_at=created_at,
            ))

        pools.append(candidates)

    return pools


def describe_pool(pools: List[List[TweetCandidate]]) -> None:
    total = sum(len(p) for p in pools)
    unique_ids = len({c.tweet_id for p in pools for c in p})
    print(f"Pool: {len(pools)} sources, {total} total candidates, {unique_ids} unique tweet IDs "
          f"({total - unique_ids} duplicates across sources)")
    for i, p in enumerate(pools):
        src = p[0].source_type if p else "?"
        eng = p[0].engine_type if p else "?"
        authors = len({c.author_id for c in p})
        print(f"  Source {i}: {len(p)} candidates, type={src}, engine={eng}, "
              f"unique_authors={authors}, "
              f"score=[{min(c.similarity_score for c in p):.3f}, {max(c.similarity_score for c in p):.3f}]")
