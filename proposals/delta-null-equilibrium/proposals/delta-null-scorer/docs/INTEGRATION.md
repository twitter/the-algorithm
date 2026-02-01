# ═══════════════════════════════════════════════════════════════════════════════
# ΔØ INTEGRATION GUIDE
# Step-by-Step Implementation for X Recommendation Algorithm
# ═══════════════════════════════════════════════════════════════════════════════
#
# Open Source Release
# Copyright 2026 K. Fain (ThēÆrchītēcť)
# Author: K. Fain aka ThēÆrchītēcť
#
# ═══════════════════════════════════════════════════════════════════════════════

## Executive Summary

This guide provides comprehensive instructions for integrating ΔØ equilibrium
enforcement into the X recommendation algorithm. The integration replaces manual
weight tuning with a mathematical constraint that ensures optimization for
**true user satisfaction**, not engagement theater.

**Estimated integration time:**
- Proof-of-concept: 2-4 hours
- Production deployment: 1-2 weeks

---

## Phase 1: Assessment & Preparation

### 1.1 Verify Source Structure

Confirm your codebase matches the expected structure:

```
x-algorithm/
├── home-mixer/
│   ├── scorers/
│   │   ├── weighted_scorer.rs    ← PRIMARY INTEGRATION POINT
│   │   ├── phoenix_scorer.rs
│   │   ├── author_diversity_scorer.rs
│   │   └── oon_scorer.rs
│   ├── candidate_pipeline/
│   │   ├── candidate.rs          ← PhoenixScores struct
│   │   └── query.rs
│   └── lib.rs
├── phoenix/
└── candidate-pipeline/
```

### 1.2 Backup Critical Files

Before any modifications:

```bash
cp home-mixer/scorers/weighted_scorer.rs home-mixer/scorers/weighted_scorer.rs.backup
```

### 1.3 Add Dependencies

Add to your `Cargo.toml`:

```toml
[dependencies]
lazy_static = "1.4"
```

---

## Phase 2: Drop-In Replacement (Fastest Path)

### 2.1 Replace Weighted Scorer

The fastest integration path: replace the existing weighted scorer entirely.

```bash
cp delta-null-integration/src/weighted_scorer_delta_null.rs \
   home-mixer/scorers/weighted_scorer.rs
```

### 2.2 Verify Imports

Ensure the following imports are available in the module:

```rust
use crate::candidate_pipeline::candidate::{PhoenixScores, PostCandidate};
use crate::candidate_pipeline::query::ScoredPostsQuery;
use crate::util::score_normalizer::normalize_score;
use std::sync::{Arc, RwLock};
use tonic::async_trait;
use xai_candidate_pipeline::scorer::Scorer;
```

### 2.3 Build and Test

```bash
cargo build --release
cargo test
```

---

## Phase 3: Wrapper Integration (Lower Risk)

For more conservative deployment, wrap the existing scorer:

### 3.1 Create Wrapper Module

Add `home-mixer/scorers/delta_null_wrapper.rs`:

```rust
use super::weighted_scorer::WeightedScorer as OriginalScorer;
use crate::candidate_pipeline::candidate::PostCandidate;

pub struct DeltaNullWrapper {
    original: OriginalScorer,
    config: DeltaNullConfig,
}

impl DeltaNullWrapper {
    pub fn wrap_score(&self, candidate: &mut PostCandidate) {
        // Get original score
        let original_score = candidate.weighted_score.unwrap_or(0.0);
        
        // Compute equilibrium factor
        let equilibrium_factor = self.compute_equilibrium_factor(candidate);
        
        // Apply ΔØ constraint
        candidate.weighted_score = Some(original_score * equilibrium_factor);
    }
}
```

### 3.2 Inject in Pipeline

Modify `home-mixer/candidate_pipeline/phoenix_candidate_pipeline.rs` to include
the wrapper after the weighted scorer stage.

---

## Phase 4: Configuration & Tuning

### 4.1 Load Configuration

The ΔØ scorer can be configured via environment variables or config file:

```rust
// From environment
let sensitivity = std::env::var("DELTA_NULL_SENSITIVITY")
    .unwrap_or("5.0".into())
    .parse::<f64>()
    .unwrap();

// From config file
let config: DeltaNullConfig = toml::from_str(
    &std::fs::read_to_string("config/delta_null.toml")?
)?;
```

### 4.2 Key Parameters

| Parameter | Default | Range | Effect |
|-----------|---------|-------|--------|
| `base_sensitivity` | 5.0 | 1.0-15.0 | Penalty strength for rejection |
| `rejection_floor` | 0.001 | 0.0001-0.01 | Minimum rejection before penalty |
| `learning_rate` | 0.01 | 0.001-0.1 | Speed of self-adaptation |
| `ema_decay` | 0.95 | 0.9-0.99 | Memory length for adaptation |

### 4.3 Tuning Guidelines

**If feed becomes too conservative (low engagement):**
- Decrease `base_sensitivity` (e.g., 3.0)
- Increase `rejection_floor` (e.g., 0.01)

**If engagement bait still ranks high:**
- Increase `base_sensitivity` (e.g., 7.0)
- Increase rejection signal weights

---

## Phase 5: Monitoring & Observability

### 5.1 Export Metrics

The scorer exposes metrics via the public API:

```rust
use home_mixer::scorers::weighted_scorer::{get_delta_null_stats, reset_delta_null_stats};

// Get current statistics
let (ema_pos, ema_neg, ema_ratio, sample_count, sensitivity) = get_delta_null_stats();

// Log to metrics system
metrics::gauge!("delta_null.ema_delta_positive", ema_pos);
metrics::gauge!("delta_null.ema_delta_negative", ema_neg);
metrics::gauge!("delta_null.ema_equilibrium_ratio", ema_ratio);
metrics::counter!("delta_null.sample_count", sample_count);
metrics::gauge!("delta_null.adaptive_sensitivity", sensitivity);
```

### 5.2 Dashboard Panels

Create monitoring dashboards for:

1. **Equilibrium Distribution**
   - Histogram of `equilibrium_ratio` across scored content
   - Should center around 0.75 for healthy feed

2. **Penalty Impact**
   - Average `equilibrium_factor` by content type
   - Track how much scores are being adjusted

3. **Adaptive Sensitivity**
   - Time series of `adaptive_sensitivity`
   - Should stabilize after initial learning period

4. **State Classification**
   - Pie chart of content by `EquilibriumState`
   - Monitor for toxic content percentage

### 5.3 Alerts

Configure alerts for:

| Metric | Condition | Action |
|--------|-----------|--------|
| `ema_equilibrium_ratio` | < 0.5 | System too aggressive |
| `ema_equilibrium_ratio` | > 0.95 | System too permissive |
| `adaptive_sensitivity` | < 2.0 or > 12.0 | Bounds reached |

---

## Phase 6: A/B Testing & Rollout

### 6.1 Feature Flag

Implement gradual rollout via feature flag:

```rust
pub fn should_use_delta_null(user_id: u64) -> bool {
    let rollout_percentage = CONFIG.rollout_percentage;
    (user_id % 100) < rollout_percentage
}
```

### 6.2 A/B Test Metrics

Compare control vs. treatment:

| Metric | Expected Change |
|--------|-----------------|
| Engagement rate | Slight decrease (~5%) |
| Block/mute rate | Significant decrease (~20-30%) |
| Session duration | Increase (~10-15%) |
| DAU retention | Improvement (~5-10%) |
| Report rate | Significant decrease (~30-40%) |

### 6.3 Rollout Schedule

1. **Week 1:** 1% rollout, monitor stability
2. **Week 2:** 5% rollout, compare A/B metrics
3. **Week 3:** 25% rollout, validate at scale
4. **Week 4:** 50% rollout, final validation
5. **Week 5:** 100% rollout

---

## Phase 7: Troubleshooting

### Common Issues

**Issue:** Scores are all near zero
**Cause:** Sensitivity too high
**Fix:** Decrease `base_sensitivity` or increase `rejection_floor`

**Issue:** Engagement bait still ranking high
**Cause:** Sensitivity too low or rejection weights too low
**Fix:** Increase `base_sensitivity` or rejection signal weights

**Issue:** Adaptive sensitivity oscillating
**Cause:** Learning rate too high
**Fix:** Decrease `learning_rate` to 0.001

**Issue:** Self-adaptation not converging
**Cause:** EMA decay too low (short memory)
**Fix:** Increase `ema_decay` to 0.98

---

## Support

For questions or contributions:

**Contact:**
K. Fain aka ThēÆrchītēcť

K. Fain (ThēÆrchītēcť)

---

# ═══════════════════════════════════════════════════════════════════════════════
# END OF INTEGRATION GUIDE
#
# Open Source Release
# Copyright 2026 K. Fain (ThēÆrchītēcť)
# ═══════════════════════════════════════════════════════════════════════════════
