WITH
  vars AS (
    SELECT
      TIMESTAMP("{START_TIME}") AS start_date,
      TIMESTAMP("{END_TIME}") AS end_date,
  ),

  -- Get raw user-tweet interaction events from UUA (We will use fav engagements here)
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

  -- Get video tweet ids
  video_tweet_ids AS (
      WITH vars AS (
        SELECT
          TIMESTAMP("{START_TIME}") AS start_date,
          TIMESTAMP("{END_TIME}") AS end_date
      ),

      -- Get raw user-tweet interaction events from UUA
      video_view_engagements AS (
        SELECT item.tweetInfo.actionTweetId AS tweetId
        FROM `twttr-bql-unified-prod.unified_user_actions_engagements.streaming_unified_user_actions_engagements`, vars
        WHERE (DATE(dateHour) >= DATE(vars.start_date) AND DATE(dateHour) <= DATE(vars.end_date))
          AND eventMetadata.sourceTimestampMs >= UNIX_MILLIS(start_date)
          AND eventMetadata.sourceTimestampMs <= UNIX_MILLIS(end_date)
          AND (actionType IN ("ClientTweetVideoPlayback50")
                OR actionType IN ("ClientTweetVideoPlayback95"))
      )

      SELECT DISTINCT(tweetId)
      FROM video_view_engagements
  ),

  -- Join video tweet ids
  video_tweets_engagements AS (
      SELECT raw_engagements.*
      FROM raw_engagements JOIN video_tweet_ids USING(tweetId)
  ),

  -- Group by userId and tweetId
  user_tweet_engagement_pairs AS (
    SELECT userId, tweetId, ARRAY_AGG(STRUCT(doOrUndo, tsMillis) ORDER BY tsMillis DESC LIMIT 1) AS details, COUNT(*) AS cnt
    FROM video_tweets_engagements
    GROUP BY userId, tweetId
  )

-- Remove undo events
SELECT userId, tweetId, CAST(dt.tsMillis  AS FLOAT64) AS tsMillis
FROM user_tweet_engagement_pairs, vars
CROSS JOIN UNNEST(details) AS dt
WHERE dt.doOrUndo = 1
