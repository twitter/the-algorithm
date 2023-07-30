WITH
  vars AS (
    SELECT
      TIMESTAMP("{START_TIME}") AS start_date,
      TIMESTAMP("{END_TIME}") AS end_date,
  ),

  -- Get raw user-tweet interaction events from UUA
  raw_engagements AS (
    SELECT
      userIdentifier.userId AS userId,
      eventMetadata.sourceTimestampMs AS tsMillis,
      CASE
          WHEN actionType IN ({CONTRIBUTING_ACTION_TYPES_STR}) THEN {CONTRIBUTING_ACTION_TWEET_ID_COLUMN}
          WHEN actionType IN ({UNDO_ACTION_TYPES_STR}) THEN {UNDO_ACTION_TWEET_ID_COLUMN}
      END AS tweetId,
      CASE
        WHEN actionType IN ({CONTRIBUTING_ACTION_TYPES_STR}) THEN 1
        WHEN actionType IN ({UNDO_ACTION_TYPES_STR}) THEN -1
      END AS doOrUndo
    FROM `twttr-bql-unified-prod.unified_user_actions_engagements.streaming_unified_user_actions_engagements`, vars
    WHERE (DATE(dateHour) >= DATE(vars.start_date) AND DATE(dateHour) <= DATE(vars.end_date))
      AND eventMetadata.sourceTimestampMs >= UNIX_MILLIS(vars.start_date)
      AND eventMetadata.sourceTimestampMs <= UNIX_MILLIS(vars.end_date)
      AND (actionType IN ({CONTRIBUTING_ACTION_TYPES_STR})
            OR actionType IN ({UNDO_ACTION_TYPES_STR}))
  ),

  -- Get evergreen tweet ids
  evergreen_tweet_ids AS (
    SELECT
        tweetId
    FROM `twttr-recos-ml-prod.simclusters.evergreen_content_data`
    WHERE TIMESTAMP(ts) =
        (  -- Get latest partition time
        SELECT MAX(TIMESTAMP(ts)) latest_partition
        FROM `twttr-recos-ml-prod.simclusters.evergreen_content_data`
        WHERE DATE(ts) BETWEEN
            DATE_SUB(DATE("{END_TIME}"),
            INTERVAL 14 DAY) AND DATE("{END_TIME}")
        )
  ),

  -- Join evergreen content table
  evergreen_tweets_engagements AS (
      SELECT raw_engagements.*
      FROM raw_engagements JOIN evergreen_tweet_ids USING(tweetId)
  ),

  -- Group by userId and tweetId
  user_tweet_engagement_pairs AS (
    SELECT userId, tweetId, ARRAY_AGG(STRUCT(doOrUndo, tsMillis) ORDER BY tsMillis DESC LIMIT 1) AS details, COUNT(*) AS cnt
    FROM evergreen_tweets_engagements
    GROUP BY userId, tweetId
  )

-- Remove undo events
SELECT userId, tweetId, CAST(dt.tsMillis  AS FLOAT64) AS tsMillis
FROM user_tweet_engagement_pairs, vars
CROSS JOIN UNNEST(details) AS dt
WHERE cnt <= 10
  AND dt.doOrUndo = 1
