#!/usr/bin/env python3
"""
═══════════════════════════════════════════════════════════════════════════════
ΔØ EQUILIBRIUM SCORING ENGINE
Self-Adapting Balance Constraint Architecture
Python Reference Implementation
═══════════════════════════════════════════════════════════════════════════════

Copyright 2026 K. Fain (ThēÆrchītēcť)
Licensed under Apache 2.0 — See LICENSE

Author: K. Fain aka ThēÆrchītēcť
Framework: Equilibrium Constraint Theory
Architecture: Triadic Equilibrium Enforcement System

This software implements equilibrium-constrained
optimization derived from control theory and thermodynamic principles.

Licensed under Apache 2.0
═══════════════════════════════════════════════════════════════════════════════
"""

import math
from dataclasses import dataclass, field
from typing import Dict, List, Optional, Tuple
from enum import Enum
from threading import Lock


# ═══════════════════════════════════════════════════════════════════════════════
# CORE PRINCIPLE: ΣΔ = 0
#
# The ΔØ constraint enforces that sustainable optimization requires equilibrium
# between constructive (engagement) and destructive (rejection) signal classes.
# ═══════════════════════════════════════════════════════════════════════════════


class SignalClass(Enum):
    """Signal classification for triadic equilibrium computation"""
    CONSTRUCTIVE = "constructive"  # Δ⁺: User engagement
    DESTRUCTIVE = "destructive"    # Δ⁻: User rejection
    NEUTRAL = "neutral"            # Context signals


class EquilibriumState(Enum):
    """Classified equilibrium state based on ratio ρ"""
    PURE_ENGAGEMENT = "pure_engagement"        # ρ > 0.95
    ENGAGEMENT_DOMINANT = "engagement_dominant" # ρ ∈ (0.8, 0.95]
    BALANCED_POSITIVE = "balanced_positive"     # ρ ∈ (0.6, 0.8]
    NEUTRAL = "neutral"                         # ρ ∈ (0.4, 0.6]
    BALANCED_NEGATIVE = "balanced_negative"     # ρ ∈ (0.2, 0.4]
    REJECTION_DOMINANT = "rejection_dominant"   # ρ ∈ (0.05, 0.2]
    TOXIC = "toxic"                             # ρ ≤ 0.05
    
    @classmethod
    def from_ratio(cls, ratio: float) -> "EquilibriumState":
        if ratio > 0.95:
            return cls.PURE_ENGAGEMENT
        elif ratio > 0.80:
            return cls.ENGAGEMENT_DOMINANT
        elif ratio > 0.60:
            return cls.BALANCED_POSITIVE
        elif ratio > 0.40:
            return cls.NEUTRAL
        elif ratio > 0.20:
            return cls.BALANCED_NEGATIVE
        elif ratio > 0.05:
            return cls.REJECTION_DOMINANT
        else:
            return cls.TOXIC
    
    def is_healthy(self) -> bool:
        return self in {
            EquilibriumState.PURE_ENGAGEMENT,
            EquilibriumState.ENGAGEMENT_DOMINANT,
            EquilibriumState.BALANCED_POSITIVE,
        }


@dataclass
class SignalDefinition:
    """Individual signal with adaptive weight"""
    name: str
    signal_class: SignalClass
    base_weight: float
    adaptive_weight: float = None
    decay_factor: float = 1.0
    
    def __post_init__(self):
        if self.adaptive_weight is None:
            self.adaptive_weight = self.base_weight


@dataclass
class PhoenixScores:
    """
    Phoenix prediction scores from transformer model.
    Maps directly to X algorithm's PhoenixScores structure.
    """
    # Constructive signals (Δ⁺)
    favorite_score: Optional[float] = None
    reply_score: Optional[float] = None
    retweet_score: Optional[float] = None
    quote_score: Optional[float] = None
    share_score: Optional[float] = None
    share_via_dm_score: Optional[float] = None
    share_via_copy_link_score: Optional[float] = None
    follow_author_score: Optional[float] = None
    click_score: Optional[float] = None
    profile_click_score: Optional[float] = None
    photo_expand_score: Optional[float] = None
    dwell_score: Optional[float] = None
    vqv_score: Optional[float] = None
    dwell_time: Optional[float] = None
    quoted_click_score: Optional[float] = None
    
    # Destructive signals (Δ⁻)
    not_interested_score: Optional[float] = None
    mute_author_score: Optional[float] = None
    block_author_score: Optional[float] = None
    report_score: Optional[float] = None


@dataclass
class DeltaNullConfig:
    """Configuration for ΔØ equilibrium enforcement"""
    equilibrium_target: float = 0.0
    base_sensitivity: float = 5.0
    rejection_floor: float = 0.001
    adaptive_learning: bool = True
    learning_rate: float = 0.01
    ema_decay: float = 0.95
    max_engagement_boost: float = 0.05


@dataclass
class DeltaNullScore:
    """Output from ΔØ equilibrium scoring"""
    final_score: float
    raw_engagement: float
    delta_positive: float
    delta_negative: float
    equilibrium_ratio: float
    equilibrium_factor: float
    state: EquilibriumState
    adaptive_sensitivity: float
    
    def to_dict(self) -> Dict:
        return {
            "final_score": self.final_score,
            "raw_engagement": self.raw_engagement,
            "delta_positive": self.delta_positive,
            "delta_negative": self.delta_negative,
            "equilibrium_ratio": self.equilibrium_ratio,
            "equilibrium_factor": self.equilibrium_factor,
            "state": self.state.value,
            "is_healthy": self.state.is_healthy(),
            "adaptive_sensitivity": self.adaptive_sensitivity,
        }


@dataclass
class AdaptiveStats:
    """Running statistics for self-adaptation"""
    ema_delta_positive: float = 0.0
    ema_delta_negative: float = 0.0
    ema_equilibrium_ratio: float = 0.0
    sample_count: int = 0
    adaptive_sensitivity: float = 5.0
    signal_multipliers: Dict[str, float] = field(default_factory=dict)


class DeltaNullScorer:
    """
    ═══════════════════════════════════════════════════════════════════════════
    ΔØ SELF-ADAPTING EQUILIBRIUM SCORER
    ═══════════════════════════════════════════════════════════════════════════
    
    Core innovation: Instead of fixed weights, the scorer LEARNS optimal balance
    from the signal distribution in real-time.
    
    Copyright 2026 K. Fain (ThēÆrchītēcť)
    Author: K. Fain aka ThēÆrchītēcť
    ═══════════════════════════════════════════════════════════════════════════
    """
    
    def __init__(self, config: Optional[DeltaNullConfig] = None):
        self.config = config or DeltaNullConfig()
        self.signals = self._default_signal_definitions()
        self.stats = AdaptiveStats(adaptive_sensitivity=self.config.base_sensitivity)
        self._lock = Lock()
    
    def _default_signal_definitions(self) -> List[SignalDefinition]:
        """Default signal definitions based on X algorithm structure"""
        return [
            # ═══════════════════════════════════════════════════════════════════
            # CONSTRUCTIVE SIGNALS (Δ⁺)
            # ═══════════════════════════════════════════════════════════════════
            SignalDefinition("favorite", SignalClass.CONSTRUCTIVE, 1.0),
            SignalDefinition("reply", SignalClass.CONSTRUCTIVE, 1.5),
            SignalDefinition("retweet", SignalClass.CONSTRUCTIVE, 1.3),
            SignalDefinition("quote", SignalClass.CONSTRUCTIVE, 1.4),
            SignalDefinition("share", SignalClass.CONSTRUCTIVE, 1.6),
            SignalDefinition("share_via_dm", SignalClass.CONSTRUCTIVE, 1.5),
            SignalDefinition("share_via_copy_link", SignalClass.CONSTRUCTIVE, 1.4),
            SignalDefinition("follow_author", SignalClass.CONSTRUCTIVE, 2.5),
            SignalDefinition("click", SignalClass.CONSTRUCTIVE, 0.3, decay_factor=0.9),
            SignalDefinition("profile_click", SignalClass.CONSTRUCTIVE, 0.4, decay_factor=0.9),
            SignalDefinition("photo_expand", SignalClass.CONSTRUCTIVE, 0.4, decay_factor=0.9),
            SignalDefinition("dwell", SignalClass.CONSTRUCTIVE, 0.6, decay_factor=0.95),
            SignalDefinition("vqv", SignalClass.CONSTRUCTIVE, 0.8),
            
            # ═══════════════════════════════════════════════════════════════════
            # DESTRUCTIVE SIGNALS (Δ⁻)
            # ═══════════════════════════════════════════════════════════════════
            SignalDefinition("not_interested", SignalClass.DESTRUCTIVE, 1.0),
            SignalDefinition("mute_author", SignalClass.DESTRUCTIVE, 2.5),
            SignalDefinition("block_author", SignalClass.DESTRUCTIVE, 4.0),
            SignalDefinition("report", SignalClass.DESTRUCTIVE, 5.0),
        ]
    
    def score(self, phoenix: PhoenixScores) -> DeltaNullScore:
        """
        ═══════════════════════════════════════════════════════════════════════
        CORE SCORING ALGORITHM
        ═══════════════════════════════════════════════════════════════════════
        """
        # Step 1: Extract signal values
        signal_values = self._extract_signals(phoenix)
        
        # Step 2: Compute triadic deltas
        delta_positive, delta_negative = self._compute_deltas(signal_values)
        
        # Step 3: Compute raw engagement
        raw_engagement = self._compute_raw_engagement(signal_values)
        
        # Step 4: Compute equilibrium ratio
        equilibrium_ratio = self._compute_equilibrium_ratio(delta_positive, delta_negative)
        
        # Step 5: Get adaptive sensitivity
        with self._lock:
            adaptive_sensitivity = self.stats.adaptive_sensitivity
        
        # Step 6: Compute equilibrium factor
        equilibrium_factor = self._compute_equilibrium_factor(
            equilibrium_ratio, adaptive_sensitivity
        )
        
        # Step 7: Apply ΔØ constraint
        final_score = raw_engagement * equilibrium_factor
        
        # Step 8: Classify state
        state = EquilibriumState.from_ratio(equilibrium_ratio)
        
        # Step 9: Update adaptive stats
        if self.config.adaptive_learning:
            self._update_adaptive_stats(delta_positive, delta_negative, equilibrium_ratio)
        
        return DeltaNullScore(
            final_score=final_score,
            raw_engagement=raw_engagement,
            delta_positive=delta_positive,
            delta_negative=delta_negative,
            equilibrium_ratio=equilibrium_ratio,
            equilibrium_factor=equilibrium_factor,
            state=state,
            adaptive_sensitivity=adaptive_sensitivity,
        )
    
    def _extract_signals(self, phoenix: PhoenixScores) -> Dict[str, float]:
        """Extract signal values from Phoenix predictions"""
        return {
            "favorite": phoenix.favorite_score or 0.0,
            "reply": phoenix.reply_score or 0.0,
            "retweet": phoenix.retweet_score or 0.0,
            "quote": phoenix.quote_score or 0.0,
            "share": phoenix.share_score or 0.0,
            "share_via_dm": phoenix.share_via_dm_score or 0.0,
            "share_via_copy_link": phoenix.share_via_copy_link_score or 0.0,
            "follow_author": phoenix.follow_author_score or 0.0,
            "click": phoenix.click_score or 0.0,
            "profile_click": phoenix.profile_click_score or 0.0,
            "photo_expand": phoenix.photo_expand_score or 0.0,
            "dwell": phoenix.dwell_score or 0.0,
            "vqv": phoenix.vqv_score or 0.0,
            "not_interested": phoenix.not_interested_score or 0.0,
            "mute_author": phoenix.mute_author_score or 0.0,
            "block_author": phoenix.block_author_score or 0.0,
            "report": phoenix.report_score or 0.0,
        }
    
    def _compute_deltas(self, signal_values: Dict[str, float]) -> Tuple[float, float]:
        """Compute triadic deltas: Δ⁺ and Δ⁻"""
        delta_positive = 0.0
        delta_negative = 0.0
        
        for sig in self.signals:
            value = signal_values.get(sig.name, 0.0)
            weighted = value * sig.adaptive_weight * sig.decay_factor
            
            if sig.signal_class == SignalClass.CONSTRUCTIVE:
                delta_positive += weighted
            elif sig.signal_class == SignalClass.DESTRUCTIVE:
                delta_negative += weighted
        
        delta_negative = max(delta_negative, self.config.rejection_floor)
        
        return delta_positive, delta_negative
    
    def _compute_raw_engagement(self, signal_values: Dict[str, float]) -> float:
        """Compute raw engagement score"""
        return sum(
            signal_values.get(sig.name, 0.0) * sig.adaptive_weight
            for sig in self.signals
            if sig.signal_class == SignalClass.CONSTRUCTIVE
        )
    
    def _compute_equilibrium_ratio(self, delta_pos: float, delta_neg: float) -> float:
        """Compute equilibrium ratio: ρ = Δ⁺ / (Δ⁺ + Δ⁻)"""
        total = delta_pos + delta_neg
        if total == 0.0:
            return 0.5
        return delta_pos / total
    
    def _compute_equilibrium_factor(self, ratio: float, sensitivity: float) -> float:
        """
        ═══════════════════════════════════════════════════════════════════════
        EQUILIBRIUM FACTOR COMPUTATION
        ═══════════════════════════════════════════════════════════════════════
        
        The multiplicative penalty that enforces ΔØ constraint.
        """
        rejection_presence = 1.0 - ratio
        
        if rejection_presence < 0.01:
            return 1.0 + self.config.max_engagement_boost
        else:
            return math.exp(-rejection_presence * sensitivity)
    
    def _update_adaptive_stats(
        self, 
        delta_pos: float, 
        delta_neg: float, 
        ratio: float
    ) -> None:
        """
        ═══════════════════════════════════════════════════════════════════════
        SELF-ADAPTIVE LEARNING
        ═══════════════════════════════════════════════════════════════════════
        """
        with self._lock:
            decay = self.config.ema_decay
            lr = self.config.learning_rate
            
            # Update EMAs
            self.stats.ema_delta_positive = (
                decay * self.stats.ema_delta_positive + (1 - decay) * delta_pos
            )
            self.stats.ema_delta_negative = (
                decay * self.stats.ema_delta_negative + (1 - decay) * delta_neg
            )
            self.stats.ema_equilibrium_ratio = (
                decay * self.stats.ema_equilibrium_ratio + (1 - decay) * ratio
            )
            self.stats.sample_count += 1
            
            # Adapt sensitivity
            target_ratio = 0.75
            ratio_error = self.stats.ema_equilibrium_ratio - target_ratio
            sensitivity_adjustment = ratio_error * lr
            self.stats.adaptive_sensitivity += sensitivity_adjustment
            self.stats.adaptive_sensitivity = max(1.0, min(15.0, self.stats.adaptive_sensitivity))
    
    def get_stats(self) -> Dict:
        """Get current adaptive statistics"""
        with self._lock:
            return {
                "ema_delta_positive": self.stats.ema_delta_positive,
                "ema_delta_negative": self.stats.ema_delta_negative,
                "ema_equilibrium_ratio": self.stats.ema_equilibrium_ratio,
                "sample_count": self.stats.sample_count,
                "adaptive_sensitivity": self.stats.adaptive_sensitivity,
            }
    
    def reset_stats(self) -> None:
        """Reset adaptive statistics"""
        with self._lock:
            self.stats = AdaptiveStats(adaptive_sensitivity=self.config.base_sensitivity)


# ═══════════════════════════════════════════════════════════════════════════════
# BATCH SCORING API
# ═══════════════════════════════════════════════════════════════════════════════

def score_batch(
    scorer: DeltaNullScorer,
    candidates: List[PhoenixScores],
) -> List[DeltaNullScore]:
    """Score multiple candidates in batch"""
    return [scorer.score(c) for c in candidates]


def rank_top_k(
    scorer: DeltaNullScorer,
    candidates: List[PhoenixScores],
    k: int,
) -> List[Tuple[int, DeltaNullScore]]:
    """Rank candidates by ΔØ equilibrium score, return top K"""
    scores = score_batch(scorer, candidates)
    indexed = list(enumerate(scores))
    indexed.sort(key=lambda x: x[1].final_score, reverse=True)
    return indexed[:k]


# ═══════════════════════════════════════════════════════════════════════════════
# DEMONSTRATION
# ═══════════════════════════════════════════════════════════════════════════════

def demonstrate():
    """Demonstrate ΔØ equilibrium enforcement"""
    print("═" * 72)
    print("ΔØ EQUILIBRIUM SCORING ENGINE - DEMONSTRATION")
    print("Copyright 2026 K. Fain (ThēÆrchītēcť)")
    print("Author: K. Fain aka ThēÆrchītēcť")
    print("═" * 72)
    print()
    
    scorer = DeltaNullScorer()
    
    # Scenario 1: Quality content
    quality = PhoenixScores(
        favorite_score=0.8,
        reply_score=0.3,
        retweet_score=0.4,
        share_score=0.2,
    )
    s1 = scorer.score(quality)
    
    print("SCENARIO 1: QUALITY CONTENT (pure engagement)")
    print(f"  Raw Engagement:     {s1.raw_engagement:.4f}")
    print(f"  Equilibrium Ratio:  {s1.equilibrium_ratio:.4f}")
    print(f"  Equilibrium Factor: {s1.equilibrium_factor:.4f}")
    print(f"  FINAL SCORE:        {s1.final_score:.4f}")
    print(f"  State:              {s1.state.value}")
    print()
    
    # Scenario 2: Engagement bait
    bait = PhoenixScores(
        favorite_score=0.8,
        reply_score=0.3,
        retweet_score=0.4,
        share_score=0.2,
        block_author_score=0.15,
        mute_author_score=0.10,
        report_score=0.05,
    )
    s2 = scorer.score(bait)
    
    print("SCENARIO 2: ENGAGEMENT BAIT (same engagement + rejection)")
    print(f"  Raw Engagement:     {s2.raw_engagement:.4f}")
    print(f"  Equilibrium Ratio:  {s2.equilibrium_ratio:.4f}")
    print(f"  Equilibrium Factor: {s2.equilibrium_factor:.4f}")
    print(f"  FINAL SCORE:        {s2.final_score:.4f}")
    print(f"  State:              {s2.state.value}")
    print()
    
    # Scenario 3: Toxic viral
    toxic = PhoenixScores(
        favorite_score=0.9,
        reply_score=0.6,
        retweet_score=0.5,
        share_score=0.3,
        block_author_score=0.25,
        mute_author_score=0.20,
        report_score=0.15,
    )
    s3 = scorer.score(toxic)
    
    print("SCENARIO 3: TOXIC VIRAL (very high engagement + high rejection)")
    print(f"  Raw Engagement:     {s3.raw_engagement:.4f}")
    print(f"  Equilibrium Ratio:  {s3.equilibrium_ratio:.4f}")
    print(f"  Equilibrium Factor: {s3.equilibrium_factor:.4f}")
    print(f"  FINAL SCORE:        {s3.final_score:.4f}")
    print(f"  State:              {s3.state.value}")
    print()
    
    print("═" * 72)
    print("SUMMARY: ΔØ EQUILIBRIUM ENFORCEMENT")
    print("═" * 72)
    print(f"Quality Content Final Score:  {s1.final_score:.4f}")
    print(f"Engagement Bait Final Score:  {s2.final_score:.4f} ({((s2.final_score - s1.final_score)/s1.final_score)*100:+.1f}%)")
    print(f"Toxic Viral Final Score:      {s3.final_score:.4f} ({((s3.final_score - s1.final_score)/s1.final_score)*100:+.1f}%)")
    print()
    print("Despite HIGHER raw engagement, toxic content scores LOWER.")
    print("This is the ΔØ difference: ΣΔ = 0")
    print("═" * 72)


if __name__ == "__main__":
    demonstrate()


# ═══════════════════════════════════════════════════════════════════════════════
# END OF FILE
#
# Open Source Release
# Copyright 2026 K. Fain (ThēÆrchītēcť)
# Author: K. Fain aka ThēÆrchītēcť
# ═══════════════════════════════════════════════════════════════════════════════
