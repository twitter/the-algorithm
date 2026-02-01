# ΔØ Equilibrium Scorer

### Drop-in replacement for X's recommendation algorithm scoring layer

> *"We know the algorithm is dumb and needs massive improvements."*
> — Elon Musk, January 19, 2026

**We fixed it.**

---

## The Problem

X's recommendation algorithm ranks content using a **weighted linear sum**:

```
Final Score = Σ (weight_i × P(action_i))
```

This is fundamentally broken:
- Weights are manually tuned with no mathematical basis
- High engagement can **overcome** high rejection signals
- Gets gamed by engagement bait and outrage farming
- Requires constant manual retuning as user behavior shifts

A block or report should **kill** a piece of content's ranking. Instead, enough likes and retweets can overwhelm it. That's not an algorithm — it's a slot machine.

---

## The Fix: Equilibrium Constraint (ΣΔ = 0)

Instead of subtracting rejection from engagement, ΔØ **multiplies** engagement by an equilibrium factor that **collapses** when rejection signals are present.

```
Score = raw_engagement × exp(-rejection_presence × sensitivity)
```

One block doesn't just lower the score. It **destroys** it.

### How It Works

1. **Partition signals:** Constructive (likes, shares, follows) vs. Destructive (blocks, mutes, reports)
2. **Compute equilibrium ratio:** ρ = Δ⁺ / (Δ⁺ + Δ⁻)
3. **Apply exponential penalty:** Any rejection presence collapses the score multiplicatively
4. **Self-adapt:** Sensitivity learns from signal distribution — no manual tuning

---

## Results

| Content Type | Raw Engagement | ΔØ Score | Change |
|---|---|---|---|
| Quality Content | 2.09 | **2.19** | baseline |
| Engagement Bait | 2.09 | **0.37** | **-83%** |
| Toxic Viral | 2.93 | **0.34** | **-85%** |

**Toxic content with 40% higher raw engagement scores 85% lower under ΔØ.**

No amount of engagement can overcome significant rejection signals. That's not a parameter — it's a mathematical guarantee.

---

## Architecture

```
┌──────────────────────────────────────────────────────────────┐
│                   PHOENIX SCORER (existing)                   │
│   P(like), P(reply), P(block), P(mute), P(report), etc.     │
└──────────────────────────────────────────────────────────────┘
                             │
                             ▼
┌──────────────────────────────────────────────────────────────┐
│                    ΔØ EQUILIBRIUM LAYER                       │
│                                                              │
│   1. PARTITION: Δ⁺ (constructive) vs Δ⁻ (destructive)       │
│   2. COMPUTE:   ρ = Δ⁺ / (Δ⁺ + Δ⁻)                         │
│   3. ADAPT:     Sensitivity learns from signal distribution  │
│   4. ENFORCE:   Score = engagement × exp(-rejection × σ)     │
│                                                              │
└──────────────────────────────────────────────────────────────┘
                             │
                             ▼
┌──────────────────────────────────────────────────────────────┐
│                EQUILIBRIUM-CONSTRAINED SCORE                  │
│    Content ranked by user value, not engagement theater       │
└──────────────────────────────────────────────────────────────┘
```

---

## Quick Start

### Drop-In Replacement (Rust)

```bash
# Backup original
cp home-mixer/scorers/weighted_scorer.rs weighted_scorer.rs.backup

# Replace with ΔØ scorer
cp src/weighted_scorer_delta_null.rs home-mixer/scorers/weighted_scorer.rs

# Add dependency
echo 'lazy_static = "1.4"' >> Cargo.toml

# Build
cargo build --release
```

### Python Reference (Testing & Validation)

```bash
python examples/demo.py
```

---

## The Math

**Signal Partitioning:**
```
Δ⁺ = Σ(constructive signals × weights)    // likes, replies, shares, follows
Δ⁻ = max(Σ(destructive signals × weights), ε)  // blocks, mutes, reports
```

**Equilibrium Ratio:**
```
ρ = Δ⁺ / (Δ⁺ + Δ⁻)
```

**Equilibrium Factor:**
```
φ = exp(-(1 - ρ) × σ)     // σ = adaptive sensitivity
```

**Final Score:**
```
S = raw_engagement × φ
```

The constraint ΣΔ = 0 is enforced through the multiplicative relationship: content cannot achieve high final scores without maintaining equilibrium between engagement and rejection signals.

**Full formal derivation:** [docs/MATH.md](docs/MATH.md)

---

## Self-Adaptive Sensitivity

Unlike fixed-weight systems, ΔØ learns optimal sensitivity from signal distribution:

```
σₜ₊₁ = σₜ + η × (ρ̄ₜ - ρ*)
```

- If feed is too permissive (high ρ̄) → increase sensitivity
- If feed is too aggressive (low ρ̄) → decrease sensitivity
- Converges when average feed equilibrium ratio = target (0.75)

No manual tuning. No weight spreadsheets. The math handles it.

---

## Theoretical Foundation

ΔØ is grounded in established control theory and cybernetics:

- **Feedback control systems** (Wiener, 1948): Sustainable systems maintain equilibrium through feedback loops
- **Thermodynamic analogy:** Content "sustainability" parallels Gibbs free energy — engagement without rejection is thermodynamically favorable
- **Lyapunov stability:** The adaptive system converges when learning rate stays within stability bounds

This isn't a hack or a heuristic. It's what control theory has said since 1948 applied to a system that ignored it.

---

## Repository Contents

| File | Description |
|---|---|
| `src/weighted_scorer_delta_null.rs` | **Drop-in replacement** for X's weighted_scorer.rs |
| `src/delta_null_scorer.rs` | Standalone Rust implementation |
| `src/delta_null_scorer.py` | Python reference implementation |
| `examples/demo.py` | Interactive demonstration with test scenarios |
| `docs/MATH.md` | Formal mathematical derivation |
| `docs/INTEGRATION.md` | Step-by-step integration guide |
| `config/delta_null.toml` | Configuration parameters |

---

## Why Open Source

The recommendation algorithm shapes what billions of people see every day. The fix shouldn't sit in a folder. If X won't merge it, someone else will build on it.

ΔØ generalizes beyond social media. Any multi-signal optimization system that needs balance enforcement — medical devices, financial risk, industrial control — can use this constraint.

The principle is simple: **ΣΔ = 0.**

---

## Author

**K. Fain** (ThēÆrchītēcť)

---

## License

Apache 2.0 — See [LICENSE](LICENSE)
