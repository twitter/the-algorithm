"""
Entry point for the blending strategy A/B eval.

Usage:
    cd experiments
    python blending_eval/run.py [--n-trials N] [--n-sources N] [--cands-per-source N]
"""

import argparse
import sys
import os

sys.path.insert(0, os.path.dirname(__file__))

from blenders import ALL_BLENDERS
from metrics import ALL_METRICS
from ab_eval import run_ab_eval
from report import render
from synthetic_data import generate_candidate_pool, describe_pool


def main():
    parser = argparse.ArgumentParser(description="Blending Strategy A/B Eval")
    parser.add_argument("--n-trials", type=int, default=50, help="Trials to average over")
    parser.add_argument("--n-sources", type=int, default=5, help="Candidate sources per trial")
    parser.add_argument("--cands-per-source", type=int, default=40, help="Candidates per source")
    args = parser.parse_args()

    print("Sample candidate pool (trial 0):")
    describe_pool(generate_candidate_pool(n_sources=args.n_sources, candidates_per_source=args.cands_per_source, seed=0))

    print(f"\nRunning A/B eval: {len(ALL_BLENDERS)} blenders × {args.n_trials} trials "
          f"({args.n_sources} sources × {args.cands_per_source} cands each) ...")

    results = run_ab_eval(
        blenders=ALL_BLENDERS,
        metrics=ALL_METRICS,
        n_trials=args.n_trials,
        n_sources=args.n_sources,
        candidates_per_source=args.cands_per_source,
    )

    render(results)


if __name__ == "__main__":
    main()
