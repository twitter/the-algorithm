//! ═══════════════════════════════════════════════════════════════════════════════
//! ΔØ EQUILIBRIUM SCORING ENGINE
//! Self-Adapting Balance Constraint Architecture
//! ═══════════════════════════════════════════════════════════════════════════════
//!
//! Copyright 2026 K. Fain (ThēÆrchītēcť)
//! Licensed under Apache 2.0 — See LICENSE
//!
//! Author: K. Fain aka ThēÆrchītēcť
//! Framework: Equilibrium Constraint Theory
//! Architecture: Triadic Equilibrium Enforcement System
//!
//! This software implements equilibrium-constrained
//! optimization derived from control theory and thermodynamic principles.
//!
//! Licensed under Apache 2.0
//! ═══════════════════════════════════════════════════════════════════════════════

use std::collections::HashMap;
use std::sync::{Arc, RwLock};

// ═══════════════════════════════════════════════════════════════════════════════
// CORE PRINCIPLE: ΣΔ = 0
//
// The ΔØ constraint enforces that sustainable optimization requires equilibrium
// between constructive (engagement) and destructive (rejection) signal classes.
// Content that maximizes engagement AT THE COST OF satisfaction is penalized.
// ═══════════════════════════════════════════════════════════════════════════════

/// Signal classification for triadic equilibrium computation
#[derive(Debug, Clone, Copy, PartialEq)]
pub enum SignalClass {
    /// Constructive signals (Δ⁺): User engagement, positive interaction
    Constructive,
    /// Destructive signals (Δ⁻): User rejection, negative feedback
    Destructive,
    /// Neutral signals: Context, not affecting equilibrium
    Neutral,
}

/// Individual signal definition with adaptive weight
#[derive(Debug, Clone)]
pub struct SignalDefinition {
    pub name: &'static str,
    pub class: SignalClass,
    pub base_weight: f64,
    pub adaptive_weight: f64,
    pub decay_factor: f64,
}

/// Phoenix prediction scores from transformer model
#[derive(Debug, Clone, Default)]
pub struct PhoenixScores {
    pub favorite_score: Option<f64>,
    pub reply_score: Option<f64>,
    pub retweet_score: Option<f64>,
    pub photo_expand_score: Option<f64>,
    pub click_score: Option<f64>,
    pub profile_click_score: Option<f64>,
    pub vqv_score: Option<f64>,
    pub share_score: Option<f64>,
    pub share_via_dm_score: Option<f64>,
    pub share_via_copy_link_score: Option<f64>,
    pub dwell_score: Option<f64>,
    pub quote_score: Option<f64>,
    pub quoted_click_score: Option<f64>,
    pub follow_author_score: Option<f64>,
    pub not_interested_score: Option<f64>,
    pub block_author_score: Option<f64>,
    pub mute_author_score: Option<f64>,
    pub report_score: Option<f64>,
    pub dwell_time: Option<f64>,
}

/// Configuration for ΔØ equilibrium enforcement
#[derive(Debug, Clone)]
pub struct DeltaNullConfig {
    /// Equilibrium target (0.0 = perfect balance)
    pub equilibrium_target: f64,
    
    /// Base sensitivity to equilibrium deviation
    pub base_sensitivity: f64,
    
    /// Minimum rejection threshold before penalty activates
    pub rejection_floor: f64,
    
    /// Enable self-adaptive weight learning
    pub adaptive_learning: bool,
    
    /// Learning rate for adaptive weights
    pub learning_rate: f64,
    
    /// Exponential moving average decay for adaptation
    pub ema_decay: f64,
    
    /// Maximum equilibrium boost for pure engagement
    pub max_engagement_boost: f64,
}

impl Default for DeltaNullConfig {
    fn default() -> Self {
        Self {
            equilibrium_target: 0.0,
            base_sensitivity: 5.0,
            rejection_floor: 0.001,
            adaptive_learning: true,
            learning_rate: 0.01,
            ema_decay: 0.95,
            max_engagement_boost: 0.05,
        }
    }
}

/// Equilibrium state classification
#[derive(Debug, Clone, Copy, PartialEq)]
pub enum EquilibriumState {
    /// ρ > 0.95: Pure engagement, no rejection
    PureEngagement,
    /// ρ ∈ (0.8, 0.95]: Strong engagement dominance
    EngagementDominant,
    /// ρ ∈ (0.6, 0.8]: Healthy positive balance
    BalancedPositive,
    /// ρ ∈ (0.4, 0.6]: Neutral zone
    Neutral,
    /// ρ ∈ (0.2, 0.4]: Rejection signals present
    BalancedNegative,
    /// ρ ∈ (0.05, 0.2]: Strong rejection dominance
    RejectionDominant,
    /// ρ ≤ 0.05: Toxic content
    Toxic,
}

impl EquilibriumState {
    pub fn from_ratio(ratio: f64) -> Self {
        match ratio {
            r if r > 0.95 => Self::PureEngagement,
            r if r > 0.80 => Self::EngagementDominant,
            r if r > 0.60 => Self::BalancedPositive,
            r if r > 0.40 => Self::Neutral,
            r if r > 0.20 => Self::BalancedNegative,
            r if r > 0.05 => Self::RejectionDominant,
            _ => Self::Toxic,
        }
    }
    
    pub fn is_healthy(&self) -> bool {
        matches!(
            self,
            Self::PureEngagement | Self::EngagementDominant | Self::BalancedPositive
        )
    }
}

/// Output from ΔØ equilibrium scoring
#[derive(Debug, Clone)]
pub struct DeltaNullScore {
    /// Final equilibrium-constrained score (use for ranking)
    pub final_score: f64,
    
    /// Raw engagement score before equilibrium
    pub raw_engagement: f64,
    
    /// Aggregate constructive delta (Δ⁺)
    pub delta_positive: f64,
    
    /// Aggregate destructive delta (Δ⁻)
    pub delta_negative: f64,
    
    /// Equilibrium ratio: Δ⁺ / (Δ⁺ + Δ⁻)
    pub equilibrium_ratio: f64,
    
    /// Equilibrium enforcement factor
    pub equilibrium_factor: f64,
    
    /// Classified equilibrium state
    pub state: EquilibriumState,
    
    /// Adaptive sensitivity used for this score
    pub adaptive_sensitivity: f64,
}

/// ═══════════════════════════════════════════════════════════════════════════════
/// ΔØ SELF-ADAPTING EQUILIBRIUM SCORER
/// ═══════════════════════════════════════════════════════════════════════════════
///
/// Core innovation: Instead of fixed weights, the scorer LEARNS optimal balance
/// from the signal distribution in real-time. The equilibrium constraint is
/// universal, but the sensitivity adapts to the content domain.
/// ═══════════════════════════════════════════════════════════════════════════════
pub struct DeltaNullScorer {
    config: DeltaNullConfig,
    
    /// Signal definitions with adaptive weights
    signals: Vec<SignalDefinition>,
    
    /// Running statistics for adaptive learning
    stats: Arc<RwLock<AdaptiveStats>>,
}

/// Running statistics for self-adaptation
#[derive(Debug, Default)]
struct AdaptiveStats {
    /// Exponential moving average of delta_positive
    ema_delta_positive: f64,
    
    /// Exponential moving average of delta_negative
    ema_delta_negative: f64,
    
    /// Exponential moving average of equilibrium ratio
    ema_equilibrium_ratio: f64,
    
    /// Sample count
    sample_count: u64,
    
    /// Adaptive sensitivity (learned)
    adaptive_sensitivity: f64,
    
    /// Per-signal adaptive multipliers
    signal_multipliers: HashMap<String, f64>,
}

impl DeltaNullScorer {
    /// Create new scorer with default signal definitions
    pub fn new(config: DeltaNullConfig) -> Self {
        let signals = Self::default_signal_definitions();
        let stats = Arc::new(RwLock::new(AdaptiveStats {
            adaptive_sensitivity: config.base_sensitivity,
            ..Default::default()
        }));
        
        Self { config, signals, stats }
    }
    
    /// Default signal definitions based on X algorithm structure
    fn default_signal_definitions() -> Vec<SignalDefinition> {
        vec![
            // ═══════════════════════════════════════════════════════════════════
            // CONSTRUCTIVE SIGNALS (Δ⁺)
            // ═══════════════════════════════════════════════════════════════════
            SignalDefinition {
                name: "favorite",
                class: SignalClass::Constructive,
                base_weight: 1.0,
                adaptive_weight: 1.0,
                decay_factor: 1.0,
            },
            SignalDefinition {
                name: "reply",
                class: SignalClass::Constructive,
                base_weight: 1.5,  // Higher: deeper engagement
                adaptive_weight: 1.5,
                decay_factor: 1.0,
            },
            SignalDefinition {
                name: "retweet",
                class: SignalClass::Constructive,
                base_weight: 1.3,
                adaptive_weight: 1.3,
                decay_factor: 1.0,
            },
            SignalDefinition {
                name: "quote",
                class: SignalClass::Constructive,
                base_weight: 1.4,  // Quote = thought engagement
                adaptive_weight: 1.4,
                decay_factor: 1.0,
            },
            SignalDefinition {
                name: "share",
                class: SignalClass::Constructive,
                base_weight: 1.6,  // External sharing = very strong
                adaptive_weight: 1.6,
                decay_factor: 1.0,
            },
            SignalDefinition {
                name: "share_via_dm",
                class: SignalClass::Constructive,
                base_weight: 1.5,
                adaptive_weight: 1.5,
                decay_factor: 1.0,
            },
            SignalDefinition {
                name: "share_via_copy_link",
                class: SignalClass::Constructive,
                base_weight: 1.4,
                adaptive_weight: 1.4,
                decay_factor: 1.0,
            },
            SignalDefinition {
                name: "follow_author",
                class: SignalClass::Constructive,
                base_weight: 2.5,  // Highest: commitment signal
                adaptive_weight: 2.5,
                decay_factor: 1.0,
            },
            SignalDefinition {
                name: "click",
                class: SignalClass::Constructive,
                base_weight: 0.3,  // Weak: curiosity not value
                adaptive_weight: 0.3,
                decay_factor: 0.9,
            },
            SignalDefinition {
                name: "profile_click",
                class: SignalClass::Constructive,
                base_weight: 0.4,
                adaptive_weight: 0.4,
                decay_factor: 0.9,
            },
            SignalDefinition {
                name: "photo_expand",
                class: SignalClass::Constructive,
                base_weight: 0.4,
                adaptive_weight: 0.4,
                decay_factor: 0.9,
            },
            SignalDefinition {
                name: "dwell",
                class: SignalClass::Constructive,
                base_weight: 0.6,
                adaptive_weight: 0.6,
                decay_factor: 0.95,
            },
            SignalDefinition {
                name: "vqv",  // Video quality view
                class: SignalClass::Constructive,
                base_weight: 0.8,
                adaptive_weight: 0.8,
                decay_factor: 1.0,
            },
            
            // ═══════════════════════════════════════════════════════════════════
            // DESTRUCTIVE SIGNALS (Δ⁻)
            // ═══════════════════════════════════════════════════════════════════
            SignalDefinition {
                name: "not_interested",
                class: SignalClass::Destructive,
                base_weight: 1.0,
                adaptive_weight: 1.0,
                decay_factor: 1.0,
            },
            SignalDefinition {
                name: "mute_author",
                class: SignalClass::Destructive,
                base_weight: 2.5,  // Strong rejection
                adaptive_weight: 2.5,
                decay_factor: 1.0,
            },
            SignalDefinition {
                name: "block_author",
                class: SignalClass::Destructive,
                base_weight: 4.0,  // Severe rejection
                adaptive_weight: 4.0,
                decay_factor: 1.0,
            },
            SignalDefinition {
                name: "report",
                class: SignalClass::Destructive,
                base_weight: 5.0,  // Most severe
                adaptive_weight: 5.0,
                decay_factor: 1.0,
            },
        ]
    }
    
    /// ═══════════════════════════════════════════════════════════════════════════
    /// CORE SCORING ALGORITHM
    /// ═══════════════════════════════════════════════════════════════════════════
    pub fn score(&self, phoenix: &PhoenixScores) -> DeltaNullScore {
        // Step 1: Extract signal values from Phoenix predictions
        let signal_values = self.extract_signals(phoenix);
        
        // Step 2: Compute triadic deltas
        let (delta_positive, delta_negative) = self.compute_deltas(&signal_values);
        
        // Step 3: Compute raw engagement (for backwards compatibility metrics)
        let raw_engagement = self.compute_raw_engagement(&signal_values);
        
        // Step 4: Compute equilibrium ratio
        let equilibrium_ratio = self.compute_equilibrium_ratio(delta_positive, delta_negative);
        
        // Step 5: Get adaptive sensitivity
        let adaptive_sensitivity = {
            let stats = self.stats.read().unwrap();
            stats.adaptive_sensitivity
        };
        
        // Step 6: Compute equilibrium factor
        let equilibrium_factor = self.compute_equilibrium_factor(
            equilibrium_ratio,
            adaptive_sensitivity,
        );
        
        // Step 7: Apply ΔØ constraint
        let final_score = raw_engagement * equilibrium_factor;
        
        // Step 8: Classify state
        let state = EquilibriumState::from_ratio(equilibrium_ratio);
        
        // Step 9: Update adaptive statistics (if enabled)
        if self.config.adaptive_learning {
            self.update_adaptive_stats(delta_positive, delta_negative, equilibrium_ratio);
        }
        
        DeltaNullScore {
            final_score,
            raw_engagement,
            delta_positive,
            delta_negative,
            equilibrium_ratio,
            equilibrium_factor,
            state,
            adaptive_sensitivity,
        }
    }
    
    /// Extract signal values from Phoenix predictions into named map
    fn extract_signals(&self, phoenix: &PhoenixScores) -> HashMap<&'static str, f64> {
        let mut signals = HashMap::new();
        
        signals.insert("favorite", phoenix.favorite_score.unwrap_or(0.0));
        signals.insert("reply", phoenix.reply_score.unwrap_or(0.0));
        signals.insert("retweet", phoenix.retweet_score.unwrap_or(0.0));
        signals.insert("quote", phoenix.quote_score.unwrap_or(0.0));
        signals.insert("share", phoenix.share_score.unwrap_or(0.0));
        signals.insert("share_via_dm", phoenix.share_via_dm_score.unwrap_or(0.0));
        signals.insert("share_via_copy_link", phoenix.share_via_copy_link_score.unwrap_or(0.0));
        signals.insert("follow_author", phoenix.follow_author_score.unwrap_or(0.0));
        signals.insert("click", phoenix.click_score.unwrap_or(0.0));
        signals.insert("profile_click", phoenix.profile_click_score.unwrap_or(0.0));
        signals.insert("photo_expand", phoenix.photo_expand_score.unwrap_or(0.0));
        signals.insert("dwell", phoenix.dwell_score.unwrap_or(0.0));
        signals.insert("vqv", phoenix.vqv_score.unwrap_or(0.0));
        signals.insert("not_interested", phoenix.not_interested_score.unwrap_or(0.0));
        signals.insert("mute_author", phoenix.mute_author_score.unwrap_or(0.0));
        signals.insert("block_author", phoenix.block_author_score.unwrap_or(0.0));
        signals.insert("report", phoenix.report_score.unwrap_or(0.0));
        
        signals
    }
    
    /// Compute triadic deltas: Δ⁺ (constructive) and Δ⁻ (destructive)
    fn compute_deltas(&self, signal_values: &HashMap<&'static str, f64>) -> (f64, f64) {
        let mut delta_positive = 0.0;
        let mut delta_negative = 0.0;
        
        for signal_def in &self.signals {
            let value = signal_values.get(signal_def.name).copied().unwrap_or(0.0);
            let weighted = value * signal_def.adaptive_weight * signal_def.decay_factor;
            
            match signal_def.class {
                SignalClass::Constructive => delta_positive += weighted,
                SignalClass::Destructive => delta_negative += weighted,
                SignalClass::Neutral => {}
            }
        }
        
        // Apply rejection floor
        delta_negative = delta_negative.max(self.config.rejection_floor);
        
        (delta_positive, delta_negative)
    }
    
    /// Compute raw engagement score (sum of constructive signals)
    fn compute_raw_engagement(&self, signal_values: &HashMap<&'static str, f64>) -> f64 {
        self.signals
            .iter()
            .filter(|s| s.class == SignalClass::Constructive)
            .map(|s| signal_values.get(s.name).copied().unwrap_or(0.0) * s.adaptive_weight)
            .sum()
    }
    
    /// Compute equilibrium ratio: ρ = Δ⁺ / (Δ⁺ + Δ⁻)
    fn compute_equilibrium_ratio(&self, delta_pos: f64, delta_neg: f64) -> f64 {
        let total = delta_pos + delta_neg;
        if total == 0.0 {
            0.5  // No signal = neutral
        } else {
            delta_pos / total
        }
    }
    
    /// ═══════════════════════════════════════════════════════════════════════════
    /// EQUILIBRIUM FACTOR COMPUTATION
    /// ═══════════════════════════════════════════════════════════════════════════
    ///
    /// This is the core of ΔØ: the multiplicative penalty that collapses
    /// scores when destructive signals are present.
    ///
    /// Unlike additive penalty (current X approach), multiplication means:
    /// - High engagement + high rejection = LOW final score
    /// - High engagement + no rejection = HIGH final score
    /// - No amount of engagement can "overcome" significant rejection
    /// ═══════════════════════════════════════════════════════════════════════════
    fn compute_equilibrium_factor(&self, ratio: f64, sensitivity: f64) -> f64 {
        // Deviation from pure engagement (1.0)
        let rejection_presence = 1.0 - ratio;
        
        if rejection_presence < 0.01 {
            // Pure engagement: small boost
            1.0 + self.config.max_engagement_boost
        } else {
            // Rejection present: exponential penalty
            // φ = exp(-rejection_presence × sensitivity)
            (-rejection_presence * sensitivity).exp()
        }
    }
    
    /// ═══════════════════════════════════════════════════════════════════════════
    /// SELF-ADAPTIVE LEARNING
    /// ═══════════════════════════════════════════════════════════════════════════
    ///
    /// The system learns optimal sensitivity from the signal distribution:
    /// - If equilibrium ratios are consistently high, INCREASE sensitivity
    ///   (the system is too permissive)
    /// - If equilibrium ratios are consistently low, DECREASE sensitivity
    ///   (the system is too aggressive)
    ///
    /// This allows the scorer to calibrate itself to different content domains
    /// without manual tuning.
    /// ═══════════════════════════════════════════════════════════════════════════
    fn update_adaptive_stats(&self, delta_pos: f64, delta_neg: f64, ratio: f64) {
        let mut stats = self.stats.write().unwrap();
        
        let decay = self.config.ema_decay;
        let lr = self.config.learning_rate;
        
        // Update EMAs
        stats.ema_delta_positive = decay * stats.ema_delta_positive + (1.0 - decay) * delta_pos;
        stats.ema_delta_negative = decay * stats.ema_delta_negative + (1.0 - decay) * delta_neg;
        stats.ema_equilibrium_ratio = decay * stats.ema_equilibrium_ratio + (1.0 - decay) * ratio;
        stats.sample_count += 1;
        
        // Adapt sensitivity based on equilibrium distribution
        // Target: healthy equilibrium ratio should be ~0.7-0.8 on average
        let target_ratio = 0.75;
        let ratio_error = stats.ema_equilibrium_ratio - target_ratio;
        
        // If average ratio is too high (>0.75), increase sensitivity
        // If average ratio is too low (<0.75), decrease sensitivity
        let sensitivity_adjustment = ratio_error * lr;
        stats.adaptive_sensitivity += sensitivity_adjustment;
        
        // Clamp sensitivity to reasonable range
        stats.adaptive_sensitivity = stats.adaptive_sensitivity.clamp(1.0, 15.0);
    }
    
    /// Get current adaptive statistics (for monitoring/debugging)
    pub fn get_stats(&self) -> (f64, f64, f64, u64, f64) {
        let stats = self.stats.read().unwrap();
        (
            stats.ema_delta_positive,
            stats.ema_delta_negative,
            stats.ema_equilibrium_ratio,
            stats.sample_count,
            stats.adaptive_sensitivity,
        )
    }
    
    /// Reset adaptive statistics
    pub fn reset_stats(&self) {
        let mut stats = self.stats.write().unwrap();
        stats.ema_delta_positive = 0.0;
        stats.ema_delta_negative = 0.0;
        stats.ema_equilibrium_ratio = 0.0;
        stats.sample_count = 0;
        stats.adaptive_sensitivity = self.config.base_sensitivity;
        stats.signal_multipliers.clear();
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// BATCH SCORING API
// ═══════════════════════════════════════════════════════════════════════════════

/// Score multiple candidates in batch
pub fn score_batch(
    scorer: &DeltaNullScorer,
    candidates: &[PhoenixScores],
) -> Vec<DeltaNullScore> {
    candidates.iter().map(|c| scorer.score(c)).collect()
}

/// Rank candidates by ΔØ equilibrium score, return top K
pub fn rank_top_k(
    scorer: &DeltaNullScorer,
    candidates: &[PhoenixScores],
    k: usize,
) -> Vec<(usize, DeltaNullScore)> {
    let scores = score_batch(scorer, candidates);
    
    let mut indexed: Vec<(usize, DeltaNullScore)> = scores
        .into_iter()
        .enumerate()
        .collect();
    
    indexed.sort_by(|a, b| {
        b.1.final_score.partial_cmp(&a.1.final_score).unwrap()
    });
    
    indexed.into_iter().take(k).collect()
}

// ═══════════════════════════════════════════════════════════════════════════════
// TESTS
// ═══════════════════════════════════════════════════════════════════════════════

#[cfg(test)]
mod tests {
    use super::*;
    
    fn make_scores(
        favorite: f64,
        reply: f64,
        block: f64,
        report: f64,
    ) -> PhoenixScores {
        PhoenixScores {
            favorite_score: Some(favorite),
            reply_score: Some(reply),
            block_author_score: Some(block),
            report_score: Some(report),
            ..Default::default()
        }
    }
    
    #[test]
    fn test_pure_engagement_high_score() {
        let scorer = DeltaNullScorer::new(DeltaNullConfig::default());
        let scores = make_scores(0.8, 0.3, 0.0, 0.0);
        let result = scorer.score(&scores);
        
        assert!(result.equilibrium_ratio > 0.95);
        assert!(result.state == EquilibriumState::PureEngagement);
        assert!(result.state.is_healthy());
    }
    
    #[test]
    fn test_engagement_bait_penalized() {
        let scorer = DeltaNullScorer::new(DeltaNullConfig::default());
        
        let quality = make_scores(0.8, 0.3, 0.0, 0.0);
        let bait = make_scores(0.8, 0.3, 0.2, 0.1);
        
        let quality_score = scorer.score(&quality);
        let bait_score = scorer.score(&bait);
        
        assert!(quality_score.final_score > bait_score.final_score);
        assert!(quality_score.equilibrium_factor > bait_score.equilibrium_factor);
    }
    
    #[test]
    fn test_toxic_content_collapsed() {
        let scorer = DeltaNullScorer::new(DeltaNullConfig::default());
        
        // High engagement but also high rejection
        let toxic = make_scores(0.9, 0.5, 0.3, 0.2);
        let result = scorer.score(&toxic);
        
        // Despite high raw engagement, final score should be low
        assert!(result.raw_engagement > 1.0);
        assert!(result.equilibrium_factor < 0.5);
        assert!(!result.state.is_healthy());
    }
    
    #[test]
    fn test_adaptive_sensitivity() {
        let config = DeltaNullConfig {
            adaptive_learning: true,
            ..Default::default()
        };
        let scorer = DeltaNullScorer::new(config);
        
        // Score many candidates to trigger adaptation
        for _ in 0..100 {
            let scores = make_scores(0.5, 0.2, 0.05, 0.02);
            scorer.score(&scores);
        }
        
        let (_, _, _, count, sensitivity) = scorer.get_stats();
        assert!(count == 100);
        // Sensitivity should have adapted from base
        assert!(sensitivity != 5.0);
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// END OF FILE
// 
// Open Source Release
// Copyright 2026 K. Fain (ThēÆrchītēcť)
// Author: K. Fain aka ThēÆrchītēcť
// ═══════════════════════════════════════════════════════════════════════════════
