-- This SQL query generate the cluster to top k tweets index based on tweet engagements.
-- The engagement type is decided by USER_TWEET_ENGAGEMENT_TABLE_SQL.

with vars as (
        SELECT {HALF_LIFE} AS halfLife, -- Default: 8 hour halfLife in millis
        UNIX_MILLIS("{CURRENT_TS}") AS currentTs,
    ),

  user_tweet_engagement_pairs AS (
      {USER_TWEET_ENGAGEMENT_TABLE_SQL}
  ),

  -- A sequence of filters to get eligible tweetIds for tweet embedding generation
  -- Apply min interaction count filter
 user_tweet_interaction_with_min_interaction_count_filter AS (
      SELECT userId, user_tweet_engagement_pairs.tweetId, tsMillis
      FROM user_tweet_engagement_pairs, vars
      JOIN (
          SELECT tweetId, COUNT(DISTINCT(userId)) AS interactionCount
          FROM user_tweet_engagement_pairs
          GROUP BY tweetId
          HAVING interactionCount >= {MIN_INTERACTION_COUNT} -- Only generate tweet embeddings for tweets with >= {MIN_INTERACTION_COUNT} interactions
      ) eligible_tweets USING(tweetId)
  ),

  -- Apply min fav count filter
  user_tweet_interaction_with_fav_count_filter AS (
    {TWEET_INTERACTION_WITH_FAV_COUNT_FILTER_SQL}
  ),

  -- Apply health and video filter
  user_tweet_interaction_with_health_filter AS (
    {TWEET_INTERACTION_WITH_HEALTH_FILTER_SQL}
  ),

  -- Final filtered user tweet interaction table
  -- Read the result from the last filter
  user_tweet_interaction_processed_table AS (
    SELECT *
    FROM user_tweet_interaction_with_health_filter
  ),

  -- Read consumer embeddings
  consumer_embeddings AS (
     {CONSUMER_EMBEDDINGS_SQL}
  ),

  -- Update tweet cluster scores based on interaction events
  tweet_cluster_scores AS (
      SELECT tweetId,
          STRUCT(
              clusterId,
              CASE vars.halfLife
                -- halfLife = -1 means there is no half life decay and we directly take the sum as the score
                WHEN -1 THEN SUM(clusterNormalizedLogFavScore)
                ELSE SUM(clusterNormalizedLogFavScore * POW(0.5, (currentTs - tsMillis) / vars.halfLife))
                END AS normalizedScore,
              COUNT(*) AS engagementCount)
          AS clusterIdToScores
      FROM user_tweet_interaction_processed_table, vars
      JOIN consumer_embeddings USING(userId)
      GROUP BY tweetId, clusterId, vars.halfLife
  ),

  -- Generate tweet embeddings
  tweet_embeddings_with_top_clusters AS (
      SELECT tweetId, ARRAY_AGG(
          clusterIdToScores
          ORDER BY clusterIdToScores.normalizedScore DESC
          LIMIT {TWEET_EMBEDDING_LENGTH}
      ) AS clusterIdToScores
      FROM tweet_cluster_scores
      GROUP BY tweetId
  ),

  clusters_top_k_tweets AS (
    SELECT clusterId, ARRAY_AGG(STRUCT(tweetId, normalizedScore AS tweetScore) ORDER BY normalizedScore DESC LIMIT {CLUSTER_TOP_K_TWEETS}) AS topKTweetsForClusterKey
    FROM tweet_embeddings_with_top_clusters, UNNEST(clusterIdToScores) AS clusterIdToScores
    WHERE engagementCount >= {MIN_ENGAGEMENT_PER_CLUSTER}
    GROUP BY clusterId
  )

SELECT *
FROM clusters_top_k_tweets

