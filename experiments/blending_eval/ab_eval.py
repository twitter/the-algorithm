"""
A/B evaluation framework for blending strategies.

Runs n_trials with freshly sampled candidate pools and computes
mean ± std for each blender × metric pair.

Fixes vs. initial version:
  - reference_time is captured once per trial before blenders run and
    passed to mean_recency_hours, so all blenders in a trial share the
    same temporal reference point.
  - Returns both mean and std so the report can show confidence ranges.
"""

import functools
from datetime import datetime
from typing import Callable, Dict, List, Tuple

import numpy as np

from synthetic_data import TweetCandidate, generate_candidate_pool
from metrics import mean_recency_hours


def run_ab_eval(
    blenders: Dict[str, Callable],
    metrics: Dict[str, Callable],
    n_trials: int = 50,
    n_sources: int = 5,
    candidates_per_source: int = 40,
) -> Dict[str, Dict[str, Tuple[float, float]]]:
    """
    Returns {blender_name: {metric_name: (mean, std)}} averaged over n_trials.
    """
    accumulated: Dict[str, Dict[str, List[float]]] = {
        blender_name: {metric_name: [] for metric_name in metrics}
        for blender_name in blenders
    }

    for trial in range(n_trials):
        pool = generate_candidate_pool(
            n_sources=n_sources,
            candidates_per_source=candidates_per_source,
            seed=trial,
        )

        # Pin reference time once per trial so all blenders share the same
        # temporal baseline when computing recency metrics.
        reference_time = datetime.utcnow()

        # Bind reference_time into the recency metric for this trial
        trial_metrics = {
            name: (
                functools.partial(fn, reference_time=reference_time)
                if fn is mean_recency_hours
                else fn
            )
            for name, fn in metrics.items()
        }

        for blender_name, blend_fn in blenders.items():
            try:
                result = blend_fn(pool)
            except Exception as e:
                print(f"  [trial {trial}] {blender_name} failed: {e}")
                result = []

            for metric_name, metric_fn in trial_metrics.items():
                try:
                    value = metric_fn(result)
                except Exception:
                    value = float("nan")
                accumulated[blender_name][metric_name].append(value)

    summary: Dict[str, Dict[str, Tuple[float, float]]] = {}
    for blender_name, metric_values in accumulated.items():
        summary[blender_name] = {
            metric_name: (
                float(np.nanmean(values)),
                float(np.nanstd(values)),
            )
            for metric_name, values in metric_values.items()
        }

    return summary
