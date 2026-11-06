# Testagram Feed Ranking Service

This is a standalone Testagram-owned ranking service added alongside (not inside or in place of) the upstream X recommendation code. It is intentionally a small Go module so Testagram can ship a useful feed service without attempting to deploy X's entire internal service graph.

## Ownership and request flow

1. **Testagram Backend** authenticates the user and remains authoritative for posts, account state, follows, blocks, mutes, visibility, and pagination cursors.
2. Backend candidate adapters gather a bounded candidate set from local posts, followed accounts, recommendations, and eligible federated posts.
3. Backend sends candidates plus server-derived user signals to this ranker over a private authenticated network.
4. This service removes excluded candidates, scores the remainder, applies author diversity, and returns ordered IDs with explainable reasons.
5. Backend rechecks visibility/authorization and hydrates canonical post objects before returning the feed to XClone.

The ranker must never become the source of truth for users, posts, follows, blocks, likes, or moderation decisions. Do not expose this internal endpoint directly to browsers or Android clients.

## Ranking principles

- Hard exclusions before scoring: hidden posts, seen IDs, blocked/muted authors, disallowed sensitive content, and high report-risk candidates.
- Bounded, normalized signals: recency, follow relationship, mutual affinity, interest affinity, quality, constructive conversation, negative feedback and report risk.
- Diversity cap: no more than two posts from one author in a single response.
- Stable tie-breaking for reproducible tests.
- Maximum 200 candidates per request and maximum 50 returned items.
- Explainable score reasons; no raw personal-message content or sensitive user attributes are accepted as ranking features.

This is a deterministic baseline, not a trained ML model. Keep weights versioned and evaluate changes with offline replay and online A/B tests before rollout.

## Run

Go 1.23+:

```sh
cd testagram-feed
go test ./...
INTERNAL_TOKEN='replace-with-a-long-random-secret' go run ./cmd/feed-api
```

Health: `GET /healthz` and `GET /readyz`.

Ranking: `POST /v1/feed/rank` with `Authorization: Bearer $INTERNAL_TOKEN`.

## Contract example

```json
{
  "now": "2026-11-06T12:00:00Z",
  "limit": 20,
  "seenIds": ["post-already-seen"],
  "blockedAuthorIds": ["blocked-account"],
  "mutedAuthorIds": [],
  "allowSensitive": false,
  "candidates": [{
    "id": "post-123",
    "authorId": "account-456",
    "createdAt": "2026-11-06T11:00:00Z",
    "visible": true,
    "sensitive": false,
    "features": {
      "following": true,
      "mutualAffinity": 0.4,
      "interestAffinity": 0.8,
      "quality": 0.7,
      "conversation": 0.5,
      "negativeFeedback": 0,
      "reportRisk": 0
    }
  }]
}
```

## Production gates

Before enabling this service in production: run tests and race detector; add request-level metrics and tracing; persist ranking-version metadata in backend logs; enforce mTLS or rotated service credentials; add offline evaluation datasets and fairness/quality checks; verify backend rechecks every candidate's visibility; and load-test with realistic candidate counts. A ranker timeout must fall back to a safe chronological feed, not block publishing or feed availability.

## Container deployment

The standalone service image is built from this directory using testagram-feed/Dockerfile and published as ghcr.io/trendyzima/testagram-feed when the module is merged to main. It listens on port 8092. Run it only on a private service network; do not publish 8092 to the Internet. The Data Centre Compose runtime injects INTERNAL_TOKEN and the Backend connects through ALGORITHM_URL=http://algorithm:8092.
