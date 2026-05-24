WITH
  cluster_top_tweets AS (
    {CLUSTER_TOP_TWEETS_SQL}
  ),

  flatten_cluster_top_tweets AS (
    SELECT
      clusterId,
      tweet.tweetId,
      tweet.tweetScore,
    FROM cluster_top_tweets, UNNEST(topKTweetsForClusterKey) AS tweet
  ),

--- There might be delay or skip for the fav-based dataset.
--- This query retrieved the dateHour of the latest partition available.
  latest_fav_cluster_to_tweet AS (
    SELECT
      MAX(dateHour) AS latestTimestamp
    FROM
      `twttr-bq-cassowary-prod.user.simclusters_fav_based_cluster_to_tweet_index`
    WHERE
      TIMESTAMP(dateHour) >= TIMESTAMP("{START_TIME}")
      AND TIMESTAMP(dateHour) <= TIMESTAMP("{END_TIME}")
  ),

  flatten_fav_cluster_top_tweets AS (
    SELECT
      clusterId.clusterId AS clusterId,
      tweet.key AS tweetId
    FROM
      `twttr-bq-cassowary-prod.user.simclusters_fav_based_cluster_to_tweet_index`,
      UNNEST(topKTweetsWithScores.topTweetsByFavClusterNormalizedScore) AS tweet,
      latest_fav_cluster_to_tweet
    WHERE
      dateHour=latest_fav_cluster_to_tweet.latestTimestamp
  ),

  flatten_cluster_top_tweets_intersection AS (
    SELECT
      clusterId,
      flatten_cluster_top_tweets.tweetId,
      flatten_cluster_top_tweets.tweetScore
    FROM
      flatten_cluster_top_tweets
    INNER JOIN
      flatten_fav_cluster_top_tweets
    USING(clusterId, tweetId)
  ),

  processed_cluster_top_tweets AS (
    SELECT
      clusterId,
      ARRAY_AGG(STRUCT(tweetId, tweetScore) ORDER BY tweetScore LIMIT {CLUSTER_TOP_K_TWEETS}) AS topKTweetsForClusterKey
    FROM flatten_cluster_top_tweets_intersection
    GROUP BY clusterId
  )

 SELECT *
 FROM processed_cluster_top_tweets
