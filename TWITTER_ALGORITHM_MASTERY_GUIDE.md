# Twitter/X Algorithm Mastery Guide
## A Deep Technical Analysis for Account Growth

*Based on comprehensive analysis of Twitter's open-sourced recommendation algorithm*

---

## Table of Contents
1. [Executive Summary](#executive-summary)
2. [How Your Tweets Get Recommended](#how-your-tweets-get-recommended)
3. [The Scoring Formula Decoded](#the-scoring-formula-decoded)
4. [Engagement Signals: What Matters Most](#engagement-signals-what-matters-most)
5. [Positive Signals (Ranked by Impact)](#positive-signals-ranked-by-impact)
6. [Negative Signals (Avoid These)](#negative-signals-avoid-these)
7. [The Four Pillars of Recommendation](#the-four-pillars-of-recommendation)
8. [Actionable Optimization Strategies](#actionable-optimization-strategies)
9. [API-Based Analysis Opportunities](#api-based-analysis-opportunities)
10. [Technical Deep Dives](#technical-deep-dives)

---

## Executive Summary

Twitter's recommendation algorithm operates through a **multi-stage pipeline** that narrows ~1 billion tweets down to ~1,500 candidates, then ranks them using ML models that predict **17 different engagement types**. Your final score is a weighted sum of these predictions.

**Key Insight**: The algorithm favors content that generates **"quality" engagement** - not just likes, but replies that get responses from authors, profile visits that lead to follows, and video views with high completion rates.

---

## How Your Tweets Get Recommended

### The Pipeline (4 Stages)

```
Stage 1: Candidate Retrieval (~1B → ~1,500 tweets)
├── In-Network (50%): Tweets from accounts you follow
│   └── Fetched via Earlybird search index (600 tweets)
├── Out-of-Network (50%): Tweets from accounts you don't follow
│   ├── UTEG - "Liked by" (300 tweets): What your network liked
│   ├── SimClusters (200 tweets): Community/topic matching
│   ├── TwHIN (200 tweets): Knowledge graph embeddings
│   └── FRS (100 tweets): Follow recommendations
└── Backfill: Reverse chronological fallback

Stage 2: Feature Hydration (~6,000 features per tweet)
├── Author features (reputation, follower/following ratio)
├── Tweet content features (text, media, hashtags)
├── Engagement features (real-time counts)
├── Relationship features (RealGraph scores)
└── Embedding features (SimClusters, TwHIN)

Stage 3: ML Scoring (Navi model)
├── Predicts probability of 17 engagement types
├── Each prediction multiplied by configurable weight
└── Weighted sum = raw score

Stage 4: Heuristic Rescoring
├── Out-of-network penalty: 0.75x multiplier
├── Reply penalty: 0.75x multiplier
├── Author diversity decay: 0.5x for repeated authors
├── Feedback fatigue: Penalizes recently shown content
└── Content diversity rescoring
```

---

## The Scoring Formula Decoded

### Base Score Calculation

```
Final Score = Σ(predicted_engagement[i] × weight[i]) + ε

Where ε = 0.001 (prevents zero scores)
```

### The 17 Engagement Predictions

| Engagement Type | What It Means | Relative Impact |
|-----------------|---------------|-----------------|
| **Favorite (Like)** | User likes the tweet | HIGH |
| **Retweet** | User retweets | HIGH |
| **Reply** | User replies | MEDIUM |
| **Reply Engaged by Author** | User replies AND author engages back | VERY HIGH |
| **Good Click V1** | Click → dwell → fav/reply | VERY HIGH |
| **Good Click V2** | Click → 2+ actions taken | VERY HIGH |
| **Good Profile Click** | Click profile → engage with profile | HIGH |
| **Video Playback 50%** | Watch ≥50% of video | HIGH |
| **Tweet Detail Dwell 15s** | View tweet detail for 15+ seconds | HIGH |
| **Profile Dwell 20s** | View profile for 20+ seconds | MEDIUM |
| **Bookmark** | User bookmarks tweet | MEDIUM |
| **Share** | User shares tweet | HIGH |
| **Share Menu Click** | User clicks share menu | LOW |
| **Negative Feedback V2** | User clicks "Not interested" | VERY NEGATIVE |
| **Report** | User reports tweet | EXTREMELY NEGATIVE |
| **Strong Negative Feedback** | Block, mute, or report | EXTREMELY NEGATIVE |
| **Weak Negative Feedback** | See fewer, don't like | NEGATIVE |

### Weight Ranges (from code)

```scala
// Positive engagement weights: 0 to +10,000
ModelWeights.FavParam (min: -10000, max: 10000)
ModelWeights.RetweetParam (min: -10000, max: 10000)
ModelWeights.ShareParam (min: -10000, max: 10000)

// Negative engagement weights: -20,000 to 0
ModelWeights.ReportParam (min: -20000, max: 0)
ModelWeights.StrongNegativeFeedbackParam (min: -1000, max: 0)
ModelWeights.WeakNegativeFeedbackParam (min: -1000, max: 0)
```

---

## Engagement Signals: What Matters Most

### Tier 1: Maximum Impact Signals

1. **Reply Engaged by Author** (`PredictedReplyEngagedByAuthorScoreFeature`)
   - When someone replies AND YOU REPLY BACK
   - This is a multiplier on engagement quality
   - **Action**: Always respond to replies, especially within first hour

2. **Good Click** (`PredictedGoodClickConvoDescFavoritedOrRepliedScoreFeature`)
   - User clicks your tweet → views detail → favorites OR replies
   - Indicates content that hooks and converts
   - **Action**: Write compelling first lines; deliver value in thread

3. **Video Playback 50%** (`PredictedVideoPlayback50ScoreFeature`)
   - User watches ≥50% of your video
   - Stronger signal than just a like
   - **Action**: Front-load value; keep videos concise; use captions

### Tier 2: High Impact Signals

4. **Tweet Detail Dwell 15s** (`PredictedTweetDetailDwellScoreFeature`)
   - User spends 15+ seconds viewing your tweet detail
   - Indicates substantive, valuable content
   - **Action**: Write longer-form content worth reading

5. **Share** (`PredictedShareScoreFeature`)
   - User shares your tweet (DM, copy link, external)
   - Strong viral signal
   - **Action**: Create shareable insights, memes, or data

6. **Good Profile Click** (`PredictedGoodProfileClickScoreFeature`)
   - User clicks profile → engages with profile (follow, like, etc.)
   - Indicates you're building reputation
   - **Action**: Maintain consistent, quality profile presence

### Tier 3: Standard Signals

7. **Favorite** - Basic engagement
8. **Retweet** - Distribution signal
9. **Reply** - Conversation starter
10. **Bookmark** - Value indicator (save for later)
11. **Profile Dwell 20s** - Extended profile interest

---

## Positive Signals (Ranked by Impact)

### Creating Content That Scores High

| Signal | How to Optimize |
|--------|-----------------|
| **Reply + Author Reply** | Engage back with every reply within 1 hour |
| **Good Click** | Strong opening hook → valuable thread → clear CTA |
| **Video 50% Completion** | 15-45 second videos, hook in first 3 seconds, captions |
| **15s Tweet Dwell** | Long-form threads, data visualizations, deep analysis |
| **Share** | Controversial takes, unique data, meme-worthy insights |
| **Profile Click → Engage** | Consistent posting, clear bio, pinned quality tweet |
| **Bookmark** | Tutorials, resources, reference material |

### Real-Time Aggregation Windows

The algorithm uses **real-time engagement signals** with specific lookback windows:

```
Signal                          Lookback Window
────────────────────────────────────────────────
Tweet Favorite (90D)            90 days
Retweet (90D)                   90 days
Reply (90D)                     90 days
Good Tweet Click                Recent (real-time)
Video Quality View              90 days
Account Follow                  Infinite
Repeated Profile Visit (14D)    14 days (min 2 visits)
Repeated Profile Visit (90D)    90 days (min 6 visits)
```

---

## Negative Signals (Avoid These)

### The Algorithm Penalizes These Heavily

| Signal | Weight Range | How It Hurts |
|--------|--------------|--------------|
| **Report Tweet** | -20,000 to 0 | Massive score reduction |
| **Strong Negative Feedback** | -1,000 to 0 | Block/mute/report |
| **Weak Negative Feedback** | -1,000 to 0 | "Not interested" clicks |
| **Don't Like** | Negative | "See fewer" clicks |
| **Unfollow** | Tracked | Reduces RealGraph score |
| **Unfavorite** | Tracked | Counted in SimClusters |

### Content That Triggers Negative Feedback

From the visibility library and trust & safety models:

1. **NSFW content** (when not expected)
2. **Spam patterns** (repetitive posting, link farms)
3. **Low-quality media** (pixelated, watermarked)
4. **Engagement bait** without delivery
5. **Misleading content** that doesn't match promise
6. **Reply spam** to high-follower accounts

---

## The Four Pillars of Recommendation

### 1. RealGraph (Relationship Scoring)

**What it does**: Predicts likelihood you'll interact with another user

**Key threshold**: Score ≥ 0.26 needed for recommendations

**How it's calculated**:
- Favorites exchanged
- Retweets exchanged
- Replies exchanged
- Profile visits
- Follow relationship
- Address book data

**Optimization**: Build genuine relationships with diverse, engaged accounts

### 2. SimClusters (Community Detection)

**What it does**: Places you and your content into ~145,000 communities

**Key thresholds**:
- Minimum 8 favorites to enter cluster
- Score ≥ 0.3 to be retained
- Cosine similarity ≥ 0.7 for high match

**Half-life decay**: 8 hours (recent engagement matters most)

**How tweets enter clusters**:
```
Tweet Embedding = Σ(InterestedIn vectors of users who favorited)
```

**Optimization**:
- Get favorited by users in your target communities
- Post about topics your audience cares about
- Engage with content in your niche

### 3. TwHIN (Knowledge Graph Embeddings)

**What it does**: Dense vector representations for users and tweets

**Models used**:
- `TweetBasedTwHINRegularUpdateAll20221024`
- `ConsumerBasedTwHINRegularUpdateAll20221024`
- Collaborative filtering models for follows/engagement

**How it works**:
- Your user embedding = your engagement history
- Tweet embedding = aggregated user interactions
- ANN search finds similar content

**Optimization**: Engage consistently with accounts similar to your target audience

### 4. UTEG (User Tweet Entity Graph)

**What it does**: Real-time "liked by" recommendations

**Key constraints**:
- Only keeps 24-48 hours of data
- Max 800 out-of-network candidates
- Min 1 favorited-by user

**How it works**:
- Traverses weighted follow graph
- Finds tweets engaged by your network
- Weights by influencer status

**Optimization**:
- Get liked by accounts with large, engaged followers
- Timing matters (recency crucial)
- Diverse engagement types help (likes + retweets + quotes)

---

## Actionable Optimization Strategies

### Strategy 1: Maximize Reply Engagement

The `ReplyEngagedByAuthor` signal is one of the highest-weighted:

```
1. Respond to EVERY reply within 60 minutes
2. Ask follow-up questions in responses
3. Create threads that invite discussion
4. Use polls to generate reply activity
5. End tweets with questions
```

### Strategy 2: Optimize for Dwell Time

Both `TweetDetailDwell15s` and `ProfileDwell20s` are tracked:

```
1. Write substantive threads (5-10 tweets)
2. Include data visualizations
3. Use carousel images
4. Create content worth re-reading
5. Pin your best content for profile dwell
```

### Strategy 3: Video Completion Rate

`VideoPlayback50` and `VideoQualityWatch` matter:

```
1. Hook in first 3 seconds
2. Keep videos 15-45 seconds optimal
3. Add captions (85% watch without sound)
4. Front-load value
5. Use native upload (not links)
```

### Strategy 4: Trigger Good Clicks

`GoodClickV1` and `GoodClickV2` are high-value:

```
GoodClickV1: Click → View Detail → Favorite OR Reply
GoodClickV2: Click → 2+ user actions taken

1. Write compelling opening lines
2. Promise value, deliver in thread
3. Use curiosity gaps appropriately
4. Include clear calls to action
5. Make threads scannable with structure
```

### Strategy 5: Build RealGraph Strength

Your relationship scores affect in-network visibility:

```
1. Engage authentically with 20-30 key accounts
2. Reply to their content regularly
3. Retweet with comments
4. Exchange DM conversations
5. Cross-platform interaction (if address book shared)
```

### Strategy 6: Enter the Right SimClusters

Community placement affects out-of-network reach:

```
1. Identify which clusters your target audience is in
2. Get favorited by users in those clusters
3. Post consistently about cluster topics
4. Engage with content in your niche
5. Build relationships with cluster influencers
```

### Strategy 7: Timing for UTEG Visibility

The 24-48 hour window is critical:

```
1. Post when your network is most active
2. Engage with high-follower accounts before posting
3. Get early engagement from influential followers
4. Coordinate with friends for initial boost
5. Reply to trending topics in your niche
```

### Strategy 8: Avoid Negative Signals

One report can tank your score:

```
1. Don't post controversial content without value
2. Avoid engagement bait that doesn't deliver
3. Don't spam replies to celebrities
4. Remove/delete poor-performing content
5. Block trolls before they report you
```

---

## API-Based Analysis Opportunities

With your Twitter API access, you can perform several powerful analyses:

### 1. Engagement Pattern Analysis

```python
# Analyze which of your tweets got the most engagement
# Correlate with posting time, content type, length, etc.
metrics_to_track = [
    'like_count',
    'retweet_count',
    'reply_count',
    'quote_count',
    'impression_count',
    'bookmark_count'
]
```

### 2. Audience Cluster Analysis

```python
# Analyze who engages with your content
# Map to potential SimCluster communities
audience_analysis = {
    'top_engagers': [...],
    'their_interests': [...],
    'shared_clusters': [...]
}
```

### 3. Optimal Posting Time

```python
# Analyze when your audience is most active
# When do your tweets get the fastest initial engagement?
time_analysis = {
    'best_hours': [...],
    'best_days': [...],
    'engagement_velocity': [...]
}
```

### 4. Content Type Performance

```python
# Which content types perform best for your audience?
content_types = [
    'text_only',
    'with_image',
    'with_video',
    'thread',
    'poll',
    'quote_tweet'
]
```

### 5. Network Analysis

```python
# Who should you engage with more?
# Which relationships drive the most value?
network_analysis = {
    'high_value_connections': [...],
    'reciprocal_engagement': [...],
    'potential_collaborators': [...]
}
```

---

## Technical Deep Dives

### Key Files in the Algorithm

| Component | Path | Purpose |
|-----------|------|---------|
| Main Pipeline | `home-mixer/server/.../ScoredTweetsRecommendationPipelineConfig.scala` | Orchestrates everything |
| ML Scorer | `home-mixer/.../scorer/NaviModelScorer.scala` | Calculates weighted scores |
| Engagement Signals | `home-mixer/.../scorer/PredictedScoreFeature.scala` | Defines 17 predictions |
| Weight Params | `home-mixer/param/HomeGlobalParams.scala` | Model weight configuration |
| Heuristic Rescoring | `home-mixer/.../scorer/HeuristicScorer.scala` | Post-ML adjustments |
| SimClusters | `src/scala/com/twitter/simclusters_v2/` | Community embeddings |
| RealGraph | `src/scala/com/twitter/interaction_graph/` | Relationship scoring |
| UTEG | `src/scala/com/twitter/recos/user_tweet_entity_graph/` | Liked-by graph |
| User Signals | `user-signal-service/` | Engagement tracking |

### Heuristic Multipliers Applied

```scala
val rescorers = Seq(
  RescoreOutOfNetwork,           // 0.75x for out-of-network
  RescoreReplies,                // 0.75x for replies
  RescoreMTLNormalization(...),  // Multi-task learning normalization
  RescoreListwise(ContentExploration...),
  RescoreListwise(DeepRetrieval...),
  RescoreListwise(AuthorBased...),
  RescoreListwise(ImpressedAuthorDecay...), // 0.5x decay for same author
  RescoreListwise(MediaClusterDedup...),
  RescoreFeedbackFatigue(...),   // Penalize recently shown
  RescoreLiveContent             // Boost live/trending
)

// Score = baseScore × Π(rescorer multipliers)
```

### SimCluster Score Calculation

```
Tweet enters cluster when:
1. Favorited by ≥8 users
2. Score ≥ 0.3 in cluster
3. Within 8-hour half-life window

Score = Σ(engaged_user_cluster_weight × time_decay)
Time decay = exp(-t / 8 hours)
```

---

## Summary: The Growth Formula

1. **Create content that earns "good clicks"** (click → dwell → engage)
2. **Reply to every comment** to boost ReplyEngagedByAuthor signal
3. **Post videos with 50%+ completion** optimization
4. **Build genuine relationships** for RealGraph strength
5. **Engage in your niche** to enter right SimClusters
6. **Time posts for UTEG** visibility (first 24-48h crucial)
7. **Avoid negative signals** at all costs
8. **Optimize for dwell time** with substantive content

---

## Next Steps

1. **Audit your recent content** against these signals
2. **Set up API tracking** for pattern analysis
3. **Identify your target SimClusters** through audience analysis
4. **Build 20-30 key relationships** strategically
5. **Create content calendar** optimized for these signals

---

*This analysis is based on Twitter's open-sourced algorithm repository as of the commit history in this codebase. Algorithm weights and parameters may be adjusted by Twitter without public disclosure.*
