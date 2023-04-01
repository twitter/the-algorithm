WITH tweet_embedding AS (
-- Expected columns:
-- tweetId, clusterId, tweetScore
  {TWEET_EMBEDDING_SQL}
),
clusters_top_k_tweets AS (
  SELECT clusterId, ARRAY_AGG(STRUCT(tweetId, tweetScore) ORDER BY tweetScore DESC LIMIT {CLUSTER_TOP_K_TWEETS}) AS topKTweetsForClusterKey
  FROM tweet_embedding
  GROUP BY clusterId
)
SELECT
  clusterId,
  topKTweetsForClusterKey
FROM clusters_top_k_tweets

