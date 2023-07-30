WITH
  vars AS (
    SELECT
      TIMESTAMP("{START_TIME}") AS start_date,
      TIMESTAMP("{END_TIME}") AS end_date,
      TIMESTAMP("{NO_OLDER_TWEETS_THAN_DATE}") AS no_older_tweets_than_date
  ),

  -- Get raw user-tweet interaction events from UUA
  interactions_unioned AS (
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

  -- Group by userId and tweetId
  user_tweet_interaction_pairs AS (
    SELECT userId, tweetId, ARRAY_AGG(STRUCT(doOrUndo, tsMillis) ORDER BY tsMillis DESC LIMIT 1) AS details, COUNT(*) AS cnt
    FROM interactions_unioned
    GROUP BY userId, tweetId
  )

-- Remove undo events
-- Apply age filter in this step
SELECT userId, tweetId, CAST(dt.tsMillis  AS FLOAT64) AS tsMillis
FROM user_tweet_interaction_pairs, vars
CROSS JOIN UNNEST(details) AS dt
WHERE cnt < 3
  AND dt.doOrUndo = 1
  AND timestamp_millis((1288834974657 +
            ((tweetId  & 9223372036850581504) >> 22))) >= vars.no_older_tweets_than_date
