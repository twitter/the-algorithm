# Testagram Backend-owned feed contract

The Algorithm service is a private ranking component, not a second application database or public API.

## Data ownership

- Backend + Neon own canonical posts, profiles, follows, blocks, privacy, moderation state and visibility.
- Backend selects an authorized candidate set and sends bounded candidate IDs plus ranking features to Algorithm over the private network.
- Algorithm returns an ordering and bounded scores for only the supplied candidate IDs.
- Backend rejects unknown/duplicate IDs, ignores scores outside the candidate set, rechecks current visibility/block/follow rules and hydrates the canonical post records from Neon.
- XClone never calls Algorithm directly and never receives the internal service token.
- Algorithm must not query Neon using a privileged product database role or create canonical posts/users.

## Reliability

- Candidate requests and responses must have strict size/time limits.
- Ranking failure must follow an explicit Backend policy: fail closed when FEED_RANKING_REQUIRED=true; otherwise use a bounded deterministic fallback ordering.
- Use request IDs for tracing without logging private content or user tokens.
- Load-test candidate sizes, p95/p99 latency, concurrency, cache hit rate and failure behavior before enabling production ranking.

This contract does not claim that the upstream X recommendation repository is already a deployable Testagram ranker. The existing Testagram-specific feed service implementation must be reviewed and tested independently before production deployment.
