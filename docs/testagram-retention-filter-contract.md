# Testagram retention-aware feed eligibility contract

The Algorithm ranks only candidate content that Backend has authorized and marked eligible. It does not own post retention, story expiry, media deletion, or the canonical lifecycle ledger.

## Required candidate fields
- `content_id`, `content_type`, `author_id`
- `visibility`, `moderation_state`, `content_state`
- `created_at`, and for stories `expires_at`
- `lifecycle_state` / `is_feed_eligible` supplied by Backend

## Filtering rules
1. Exclude stories at or after `expires_at`, even if an upstream cache or candidate batch still contains them.
2. Exclude deleted, delete-pending, blocked, moderated, private-to-other-viewer, and otherwise unauthorized content before scoring.
3. A permanent post with archived media remains eligible as a post. Media archive state must not remove the post text or engagement from ranking.
4. A post whose attachment is temporarily restoring may remain eligible only if Backend explicitly marks the post eligible; ranker must not infer availability.
5. Candidate caches must respect Backend lifecycle version/updated timestamp and have bounded TTL. Invalidation events are hints, not authorization.
6. Algorithm must not query Appwrite or object providers directly and must not use legacy Supabase records as fallback.

## Events and consistency
Backend may publish `story.expired`, `media.deleted`, `media.restored`, and `post.visibility_changed` events with stable event IDs and policy version. Consumers deduplicate by event ID. On event gaps, candidate fetch must revalidate eligibility with Backend. Ranking is never the enforcement boundary.

This is a service contract; no automatic deletion or retention schedule is implemented in this repository by this document.
