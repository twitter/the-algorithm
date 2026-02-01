#!/usr/bin/env python3
"""
ΔØ Equilibrium Scorer — Interactive Demo
Copyright 2026 K. Fain (ThēÆrchītēcť) — Apache 2.0

Run: python demo.py
"""

import math

def compute_delta_null_score(signals: dict, sensitivity: float = 5.0) -> dict:
    """Compute ΔØ equilibrium-constrained score for a content item."""
    
    # Constructive signals (Δ⁺)
    constructive_weights = {
        'p_like': 1.0, 'p_reply': 1.5, 'p_retweet': 1.3,
        'p_quote': 1.4, 'p_share': 1.6, 'p_follow': 2.5,
        'p_click': 0.3, 'p_dwell': 0.6,
    }
    
    # Destructive signals (Δ⁻)
    destructive_weights = {
        'p_not_interested': 1.0, 'p_mute': 2.5,
        'p_block': 4.0, 'p_report': 5.0,
    }
    
    # Step 1: Compute deltas
    delta_pos = sum(signals.get(k, 0) * w for k, w in constructive_weights.items())
    delta_neg = max(sum(signals.get(k, 0) * w for k, w in destructive_weights.items()), 0.001)
    
    # Step 2: Raw engagement (what X currently uses, roughly)
    raw = sum(signals.get(k, 0) * w for k, w in constructive_weights.items())
    
    # Step 3: Equilibrium ratio
    rho = delta_pos / (delta_pos + delta_neg)
    
    # Step 4: Equilibrium factor
    rejection = 1.0 - rho
    if rejection < 0.01:
        phi = 1.05  # small boost for pure engagement
    else:
        phi = math.exp(-rejection * sensitivity)
    
    # Step 5: Final score
    final = raw * phi
    
    # Old-style score (additive, what X roughly does)
    old_score = delta_pos - delta_neg
    
    return {
        'delta_positive': round(delta_pos, 3),
        'delta_negative': round(delta_neg, 3),
        'equilibrium_ratio': round(rho, 3),
        'equilibrium_factor': round(phi, 4),
        'raw_engagement': round(raw, 3),
        'old_score_additive': round(old_score, 3),
        'delta_null_score': round(final, 3),
    }


# ═══════════════════════════════════════════════════════════════════════════════
# TEST SCENARIOS
# ═══════════════════════════════════════════════════════════════════════════════

scenarios = {
    "Quality Content": {
        "desc": "Informative post, good engagement, minimal rejection",
        "signals": {
            'p_like': 0.35, 'p_reply': 0.12, 'p_retweet': 0.08,
            'p_quote': 0.04, 'p_share': 0.06, 'p_follow': 0.02,
            'p_click': 0.45, 'p_dwell': 0.60,
            'p_not_interested': 0.02, 'p_mute': 0.001, 'p_block': 0.0005, 'p_report': 0.0001,
        }
    },
    "Engagement Bait": {
        "desc": "Rage bait — high engagement BUT also high rejection",
        "signals": {
            'p_like': 0.30, 'p_reply': 0.25, 'p_retweet': 0.10,
            'p_quote': 0.08, 'p_share': 0.03, 'p_follow': 0.01,
            'p_click': 0.50, 'p_dwell': 0.40,
            'p_not_interested': 0.15, 'p_mute': 0.08, 'p_block': 0.05, 'p_report': 0.03,
        }
    },
    "Toxic Viral": {
        "desc": "Viral outrage — massive engagement, massive rejection",
        "signals": {
            'p_like': 0.40, 'p_reply': 0.35, 'p_retweet': 0.15,
            'p_quote': 0.12, 'p_share': 0.05, 'p_follow': 0.03,
            'p_click': 0.55, 'p_dwell': 0.50,
            'p_not_interested': 0.20, 'p_mute': 0.12, 'p_block': 0.08, 'p_report': 0.06,
        }
    },
    "Small Creator (Genuine)": {
        "desc": "Low reach, but people who see it love it",
        "signals": {
            'p_like': 0.15, 'p_reply': 0.08, 'p_retweet': 0.03,
            'p_quote': 0.01, 'p_share': 0.02, 'p_follow': 0.05,
            'p_click': 0.30, 'p_dwell': 0.55,
            'p_not_interested': 0.01, 'p_mute': 0.0, 'p_block': 0.0, 'p_report': 0.0,
        }
    },
    "Spam / Scam": {
        "desc": "Some clicks from curiosity, but everyone blocks/reports",
        "signals": {
            'p_like': 0.02, 'p_reply': 0.01, 'p_retweet': 0.005,
            'p_quote': 0.002, 'p_share': 0.001, 'p_follow': 0.0,
            'p_click': 0.15, 'p_dwell': 0.05,
            'p_not_interested': 0.30, 'p_mute': 0.15, 'p_block': 0.20, 'p_report': 0.25,
        }
    },
}


if __name__ == "__main__":
    print("=" * 72)
    print("  ΔØ EQUILIBRIUM SCORER — DEMO")
    print("  Constraint: ΣΔ = 0")
    print("=" * 72)
    print()
    
    # Summary table
    print(f"{'Content Type':<22} {'Raw Eng':>8} {'Old Score':>10} {'ΔØ Score':>9} {'ρ':>6} {'φ':>7}")
    print("-" * 72)
    
    for name, scenario in scenarios.items():
        result = compute_delta_null_score(scenario['signals'])
        print(f"{name:<22} {result['raw_engagement']:>8.3f} {result['old_score_additive']:>10.3f} "
              f"{result['delta_null_score']:>9.3f} {result['equilibrium_ratio']:>6.3f} "
              f"{result['equilibrium_factor']:>7.4f}")
    
    print("-" * 72)
    print()
    
    # Detailed breakdown
    for name, scenario in scenarios.items():
        result = compute_delta_null_score(scenario['signals'])
        print(f"▸ {name}: {scenario['desc']}")
        print(f"  Δ⁺ = {result['delta_positive']:.3f}  |  "
              f"Δ⁻ = {result['delta_negative']:.3f}  |  "
              f"ρ = {result['equilibrium_ratio']:.3f}  |  "
              f"φ = {result['equilibrium_factor']:.4f}")
        print(f"  Old score: {result['old_score_additive']:.3f}  →  "
              f"ΔØ score: {result['delta_null_score']:.3f}")
        
        if result['delta_null_score'] < result['old_score_additive'] * 0.5:
            pct = (1 - result['delta_null_score'] / max(result['raw_engagement'], 0.001)) * 100
            print(f"  ⚡ ΔØ PENALTY: -{pct:.0f}% — rejection signals collapsed the score")
        print()
    
    print("=" * 72)
    print("  The math is simple: ΣΔ = 0")
    print("  No engagement can overcome rejection. That's the constraint.")
    print("=" * 72)
