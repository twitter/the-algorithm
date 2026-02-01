//! ═══════════════════════════════════════════════════════════════════════════════
//! ΔØ WEIGHTED SCORER - DROP-IN REPLACEMENT
//! For X Algorithm home-mixer/scorers/weighted_scorer.rs
//! ═══════════════════════════════════════════════════════════════════════════════
//!
//! Copyright 2026 K. Fain (ThēÆrchītēcť)
//! Licensed under Apache 2.0 — See LICENSE
//!
//! Author: K. Fain aka ThēÆrchītēcť
//! Framework: Equilibrium Constraint Theory
//!
//! This file is a DROP-IN REPLACEMENT for the existing weighted_scorer.rs.
//! Simply replace the original file with this one to enable ΔØ equilibrium.
//!
//! Licensed under Apache 2.0
//! ═══════════════════════════════════════════════════════════════════════════════

use crate::candidate_pipeline::candidate::{PhoenixScores, PostCandidate};
use crate::candidate_pipeline::query::ScoredPostsQuery;
use crate::util::score_normalizer::normalize_score;
use std::sync::{Arc, RwLock};
use tonic::async_trait;
use xai_candidate_pipeline::scorer::Scorer;

// ═══════════════════════════════════════════════════════════════════════════════
// ΔØ CONFIGURATION
// ═══════════════════════════════════════════════════════════════════════════════

/// ΔØ equilibrium configuration
/// These can be loaded from environment or config service for runtime tuning
struct DeltaNullConfig {
    /// Base sensitivity to equilibrium deviation (adjusts via self-adaptation)
    base_sensitivity: f64,
    
    /// Minimum rejection signal before equilibrium enforcement
    rejection_floor: f64,
    
    /// Enable self-adaptive sensitivity learning
    adaptive_learning: bool,
    
    /// Learning rate for sensitivity adaptation
    learning_rate: f64,
    
    /// EMA decay for adaptive statistics
    ema_decay: f64,
    
    /// Maximum boost for pure engagement content
    max_engagement_boost: f64,
}

impl Default for DeltaNullConfig {
    fn default() -> Self {
        Self {
            base_sensitivity: 5.0,
            rejection_floor: 0.001,
            adaptive_learning: true,
            learning_rate: 0.01,
            ema_decay: 0.95,
            max_engagement_boost: 0.05,
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// ADAPTIVE STATISTICS
// ═══════════════════════════════════════════════════════════════════════════════

/// Running statistics for self-adaptation
#[derive(Default)]
struct AdaptiveStats {
    ema_delta_positive: f64,
    ema_delta_negative: f64,
    ema_equilibrium_ratio: f64,
    sample_count: u64,
    adaptive_sensitivity: f64,
}

lazy_static::lazy_static! {
    static ref ADAPTIVE_STATS: Arc<RwLock<AdaptiveStats>> = Arc::new(RwLock::new(
        AdaptiveStats {
            adaptive_sensitivity: 5.0,
            ..Default::default()
        }
    ));
    
    static ref CONFIG: DeltaNullConfig = DeltaNullConfig::default();
}

// ═══════════════════════════════════════════════════════════════════════════════
// WEIGHTED SCORER WITH ΔØ EQUILIBRIUM
// ═══════════════════════════════════════════════════════════════════════════════

pub struct WeightedScorer;

#[async_trait]
impl Scorer<ScoredPostsQuery, PostCandidate> for WeightedScorer {
    #[xai_stats_macro::receive_stats]
    async fn score(
        &self,
        _query: &ScoredPostsQuery,
        candidates: &[PostCandidate],
    ) -> Result<Vec<PostCandidate>, String> {
        let scored = candidates
            .iter()
            .map(|c| {
                // Compute ΔØ equilibrium score instead of simple weighted sum
                let equilibrium_score = Self::compute_delta_null_score(c);
                let normalized_score = normalize_score(c, equilibrium_score);

                PostCandidate {
                    weighted_score: Some(normalized_score),
                    ..Default::default()
                }
            })
            .collect();

        Ok(scored)
    }

    fn update(&self, candidate: &mut PostCandidate, scored: PostCandidate) {
        candidate.weighted_score = scored.weighted_score;
    }
}

impl WeightedScorer {
    // ═══════════════════════════════════════════════════════════════════════════
    // ΔØ CORE ALGORITHM
    // ═══════════════════════════════════════════════════════════════════════════
    
    /// Compute ΔØ equilibrium-constrained score
    /// 
    /// Instead of: Final Score = Σ (weight_i × P(action_i))
    /// We compute: Final Score = raw_engagement × equilibrium_factor
    /// 
    /// Where equilibrium_factor collapses when rejection signals are present.
    fn compute_delta_null_score(candidate: &PostCandidate) -> f64 {
        let s: &PhoenixScores = &candidate.phoenix_scores;
        
        // Step 1: Compute triadic deltas
        let delta_positive = Self::compute_delta_positive(s, candidate);
        let delta_negative = Self::compute_delta_negative(s);
        
        // Step 2: Compute raw engagement (for backwards compatibility)
        let raw_engagement = Self::compute_raw_engagement(s, candidate);
        
        // Step 3: Compute equilibrium ratio
        let equilibrium_ratio = Self::compute_equilibrium_ratio(delta_positive, delta_negative);
        
        // Step 4: Get adaptive sensitivity
        let sensitivity = {
            let stats = ADAPTIVE_STATS.read().unwrap();
            stats.adaptive_sensitivity
        };
        
        // Step 5: Compute equilibrium factor
        let equilibrium_factor = Self::compute_equilibrium_factor(equilibrium_ratio, sensitivity);
        
        // Step 6: Apply ΔØ constraint
        let final_score = raw_engagement * equilibrium_factor;
        
        // Step 7: Update adaptive stats (if enabled)
        if CONFIG.adaptive_learning {
            Self::update_adaptive_stats(delta_positive, delta_negative, equilibrium_ratio);
        }
        
        // Apply offset for backwards compatibility with downstream systems
        Self::offset_score(final_score)
    }
    
    // ═══════════════════════════════════════════════════════════════════════════
    // CONSTRUCTIVE DELTA (Δ⁺)
    // ═══════════════════════════════════════════════════════════════════════════
    
    /// Compute aggregate constructive signals
    fn compute_delta_positive(s: &PhoenixScores, candidate: &PostCandidate) -> f64 {
        let vqv_weight = Self::vqv_weight_eligibility(candidate);
        
        Self::apply(s.favorite_score, 1.0)
            + Self::apply(s.reply_score, 1.5)
            + Self::apply(s.retweet_score, 1.3)
            + Self::apply(s.quote_score, 1.4)
            + Self::apply(s.share_score, 1.6)
            + Self::apply(s.share_via_dm_score, 1.5)
            + Self::apply(s.share_via_copy_link_score, 1.4)
            + Self::apply(s.follow_author_score, 2.5)
            + Self::apply(s.click_score, 0.3)
            + Self::apply(s.profile_click_score, 0.4)
            + Self::apply(s.photo_expand_score, 0.4)
            + Self::apply(s.dwell_score, 0.6)
            + Self::apply(s.vqv_score, vqv_weight)
            + Self::apply(s.quoted_click_score, 0.3)
            + Self::apply(s.dwell_time, 0.1)
    }
    
    // ═══════════════════════════════════════════════════════════════════════════
    // DESTRUCTIVE DELTA (Δ⁻)
    // ═══════════════════════════════════════════════════════════════════════════
    
    /// Compute aggregate destructive/rejection signals
    fn compute_delta_negative(s: &PhoenixScores) -> f64 {
        let raw = Self::apply(s.not_interested_score, 1.0)
            + Self::apply(s.mute_author_score, 2.5)
            + Self::apply(s.block_author_score, 4.0)
            + Self::apply(s.report_score, 5.0);
        
        // Apply rejection floor
        raw.max(CONFIG.rejection_floor)
    }
    
    // ═══════════════════════════════════════════════════════════════════════════
    // RAW ENGAGEMENT (for backwards compatibility metrics)
    // ═══════════════════════════════════════════════════════════════════════════
    
    fn compute_raw_engagement(s: &PhoenixScores, candidate: &PostCandidate) -> f64 {
        let vqv_weight = Self::vqv_weight_eligibility(candidate);
        
        Self::apply(s.favorite_score, 1.0)
            + Self::apply(s.reply_score, 1.0)
            + Self::apply(s.retweet_score, 1.0)
            + Self::apply(s.click_score, 0.5)
            + Self::apply(s.share_score, 1.0)
            + Self::apply(s.dwell_score, 0.5)
            + Self::apply(s.vqv_score, vqv_weight)
            + Self::apply(s.follow_author_score, 4.0)
    }
    
    // ═══════════════════════════════════════════════════════════════════════════
    // EQUILIBRIUM COMPUTATIONS
    // ═══════════════════════════════════════════════════════════════════════════
    
    /// Compute equilibrium ratio: ρ = Δ⁺ / (Δ⁺ + Δ⁻)
    fn compute_equilibrium_ratio(delta_pos: f64, delta_neg: f64) -> f64 {
        let total = delta_pos + delta_neg;
        if total == 0.0 {
            0.5 // No signal = neutral
        } else {
            delta_pos / total
        }
    }
    
    /// Compute equilibrium enforcement factor
    /// 
    /// This is the core of ΔØ: multiplicative penalty that collapses
    /// when rejection signals are present.
    fn compute_equilibrium_factor(ratio: f64, sensitivity: f64) -> f64 {
        let rejection_presence = 1.0 - ratio;
        
        if rejection_presence < 0.01 {
            // Pure engagement: small boost
            1.0 + CONFIG.max_engagement_boost
        } else {
            // Rejection present: exponential penalty
            // φ = exp(-rejection_presence × sensitivity)
            (-rejection_presence * sensitivity).exp()
        }
    }
    
    // ═══════════════════════════════════════════════════════════════════════════
    // SELF-ADAPTIVE LEARNING
    // ═══════════════════════════════════════════════════════════════════════════
    
    fn update_adaptive_stats(delta_pos: f64, delta_neg: f64, ratio: f64) {
        let mut stats = ADAPTIVE_STATS.write().unwrap();
        
        let decay = CONFIG.ema_decay;
        let lr = CONFIG.learning_rate;
        
        // Update EMAs
        stats.ema_delta_positive = decay * stats.ema_delta_positive + (1.0 - decay) * delta_pos;
        stats.ema_delta_negative = decay * stats.ema_delta_negative + (1.0 - decay) * delta_neg;
        stats.ema_equilibrium_ratio = decay * stats.ema_equilibrium_ratio + (1.0 - decay) * ratio;
        stats.sample_count += 1;
        
        // Adapt sensitivity: target healthy ratio of ~0.75
        let target_ratio = 0.75;
        let ratio_error = stats.ema_equilibrium_ratio - target_ratio;
        let adjustment = ratio_error * lr;
        stats.adaptive_sensitivity += adjustment;
        stats.adaptive_sensitivity = stats.adaptive_sensitivity.clamp(1.0, 15.0);
    }
    
    // ═══════════════════════════════════════════════════════════════════════════
    // UTILITY FUNCTIONS (preserved from original)
    // ═══════════════════════════════════════════════════════════════════════════
    
    fn apply(score: Option<f64>, weight: f64) -> f64 {
        score.unwrap_or(0.0) * weight
    }

    fn vqv_weight_eligibility(candidate: &PostCandidate) -> f64 {
        // Minimum video duration threshold (from original params)
        const MIN_VIDEO_DURATION_MS: i32 = 10000;
        const VQV_WEIGHT: f64 = 0.8;
        
        if candidate
            .video_duration_ms
            .is_some_and(|ms| ms > MIN_VIDEO_DURATION_MS)
        {
            VQV_WEIGHT
        } else {
            0.0
        }
    }

    fn offset_score(score: f64) -> f64 {
        // Ensure non-negative output for downstream compatibility
        score.max(0.0)
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// PUBLIC API FOR MONITORING
// ═══════════════════════════════════════════════════════════════════════════════

/// Get current adaptive statistics for monitoring dashboards
pub fn get_delta_null_stats() -> (f64, f64, f64, u64, f64) {
    let stats = ADAPTIVE_STATS.read().unwrap();
    (
        stats.ema_delta_positive,
        stats.ema_delta_negative,
        stats.ema_equilibrium_ratio,
        stats.sample_count,
        stats.adaptive_sensitivity,
    )
}

/// Reset adaptive statistics (useful for A/B testing)
pub fn reset_delta_null_stats() {
    let mut stats = ADAPTIVE_STATS.write().unwrap();
    *stats = AdaptiveStats {
        adaptive_sensitivity: CONFIG.base_sensitivity,
        ..Default::default()
    };
}

// ═══════════════════════════════════════════════════════════════════════════════
// END OF FILE
//
// Open Source Release
// Copyright 2026 K. Fain (ThēÆrchītēcť)
// Author: K. Fain aka ThēÆrchītēcť
// ═══════════════════════════════════════════════════════════════════════════════
