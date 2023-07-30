with vars as (
    SELECT
    UNIX_MILLIS("{QUERY_DATE}") AS currentTs,
    TIMESTAMP("{START_TIME}") AS startTime,
    TIMESTAMP("{END_TIME}") AS endTime,
    {MIN_SCORE_THRESHOLD} AS tweetEmbeddingsMinClusterScore,
    {HALF_LIFE} AS halfLife,
    TIMESTAMP("{NO_OLDER_TWEETS_THAN_DATE}") AS noOlderTweetsThanDate
),

-- Get raw fav events
raw_favs AS (
    SELECT event.favorite.user_id AS userId, event.favorite.tweet_id AS tweetId, event.favorite.event_time_ms AS tsMillis, 1 AS favOrUnfav
    FROM `twttr-bql-timeline-prod.timeline_service_favorites.timeline_service_favorites`, vars
    WHERE (DATE(_PARTITIONTIME) = DATE(vars.startTime) OR DATE(_PARTITIONTIME) = DATE(vars.endTime)) AND
        TIMESTAMP_MILLIS(event.favorite.event_time_ms) >= vars.startTime
        AND TIMESTAMP_MILLIS(event.favorite.event_time_ms) <= vars.endTime
        AND event.favorite IS NOT NULL
),

-- Get raw unfav events
raw_unfavs AS (
    SELECT event.unfavorite.user_id AS userId, event.unfavorite.tweet_id AS tweetId, event.unfavorite.event_time_ms AS tsMillis, -1 AS favOrUnfav
    FROM `twttr-bql-timeline-prod.timeline_service_favorites.timeline_service_favorites`, vars
    WHERE (DATE(_PARTITIONTIME) = DATE(vars.startTime) OR DATE(_PARTITIONTIME) = DATE(vars.endTime)) AND
        TIMESTAMP_MILLIS(event.favorite.event_time_ms) >= vars.startTime
        AND TIMESTAMP_MILLIS(event.favorite.event_time_ms) <= vars.endTime
        AND event.unfavorite IS NOT NULL
),

-- Union fav and unfav events
favs_unioned AS (
    SELECT * FROM raw_favs
    UNION ALL
    SELECT * FROM raw_unfavs
),

-- Group by user and tweetId
user_tweet_fav_pairs AS (
    SELECT userId, tweetId, ARRAY_AGG(STRUCT(favOrUnfav, tsMillis) ORDER BY tsMillis DESC LIMIT 1) as details, count(*) as cnt
    FROM favs_unioned
    GROUP BY userId, tweetId
),

-- Remove unfav events
tweet_raw_favs_table AS (
    SELECT userId, tweetId, CAST(dt.tsMillis  AS FLOAT64) AS tsMillis
    FROM user_tweet_fav_pairs CROSS JOIN UNNEST(details) as dt
    WHERE cnt < 3 AND dt.favOrUnfav = 1 -- cnt < 3 to remove crazy fav/unfav users
),

-- Get tweetIds that are eligible for tweet embeddings
tweet_favs_table AS (
    SELECT userId, tweet_raw_favs_table.tweetId, tsMillis
    FROM tweet_raw_favs_table, vars
    JOIN (
        SELECT tweetId, COUNT(DISTINCT(userId)) AS favCount
        FROM tweet_raw_favs_table
        GROUP BY tweetId
        HAVING favCount >= 8 --we only generate tweet embeddings for tweets with >= 8 favs
    ) eligible_tweets USING(tweetId)
     -- Apply tweet age filter here
    WHERE timestamp_millis((1288834974657 + ((tweet_raw_favs_table.tweetId  & 9223372036850581504) >> 22))) >= vars.noOlderTweetsThanDate
),

-- Read consumer embeddings
consumer_embeddings AS (
  {CONSUMER_EMBEDDINGS_SQL}
),

-- Update tweet cluster scores based on fav events
tweet_cluster_scores AS (
    SELECT tweetId,
        STRUCT(
            clusterId,
            CASE vars.halfLife
              -- halfLife = -1 means there is no half life/decay and we directly take the sum as the score
              WHEN -1 THEN SUM(clusterNormalizedLogFavScore)
              ELSE SUM(clusterNormalizedLogFavScore * POW(0.5, (currentTs - tsMillis) / vars.halfLife))
              END AS clusterNormalizedLogFavScore,
            COUNT(*) AS favCount)
        AS clusterIdToScores
    FROM tweet_favs_table, vars
    JOIN consumer_embeddings USING(userId)
    GROUP BY tweetId, clusterId, vars.halfLife
),

-- Generate tweet embeddings
tweet_embeddings_with_top_clusters AS (
    SELECT tweetId, ARRAY_AGG(
        clusterIdToScores
        ORDER BY clusterIdToScores.clusterNormalizedLogFavScore DESC
        LIMIT {TWEET_EMBEDDING_LENGTH}
    ) AS clusterIdToScores
    FROM tweet_cluster_scores
    GROUP BY tweetId
)

-- Return (tweetId, clusterId, tweetScore) pairs where tweetScore > tweetEmbeddingsMinClusterScore
SELECT tweetId,
    clusterId,
    clusterNormalizedLogFavScore AS tweetScore, clusterIdToScores
FROM tweet_embeddings_with_top_clusters, UNNEST(clusterIdToScores) AS clusterIdToScores, vars
WHERE clusterIdToScores.clusterNormalizedLogFavScore > vars.tweetEmbeddingsMinClusterScore
