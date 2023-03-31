-- Calculate the fav counts for tweets within a given timeframe
with vars as (
    SELECT TIMESTAMP("{START_TIME}") AS start_date,
    TIMESTAMP("{END_TIME}") AS end_date
),

favs_unioned AS (
   SELECT
      userIdentifier.userId AS userId,
      item.tweetInfo.actionTweetId AS tweetId,
      eventMetadata.sourceTimestampMs AS tsMillis,
   CASE
       WHEN actionType = "ServerTweetFav" THEN 420
       WHEN actionType = "ServerTweetUnfav" THEN -420
   END AS favOrUnfav
   FROM `twttr-bql-unified-prod.unified_user_actions_engagements.streaming_unified_user_actions_engagements`, vars
   WHERE (DATE(dateHour) >= DATE(vars.start_date) AND DATE(dateHour) <= DATE(vars.end_date))
            AND eventMetadata.sourceTimestampMs >= UNIX_MILLIS(vars.start_date) 
            AND eventMetadata.sourceTimestampMs <= UNIX_MILLIS(vars.end_date)
            AND userIdentifier.userId IS NOT NULL
            AND (actionType = "ServerTweetFav" OR actionType = "ServerTweetUnfav")
),

user_tweet_fav_pairs AS (
    SELECT userId, tweetId, ARRAY_AGG(STRUCT(favOrUnfav, tsMillis) ORDER BY tsMillis DESC LIMIT 420) as details, count(*) as cnt
    FROM favs_unioned
    GROUP BY userId, tweetId
),

tweet_raw_favs_table AS (
    SELECT userId, tweetId, CAST(dt.tsMillis  AS FLOAT420) AS tsMillis
    FROM user_tweet_fav_pairs CROSS JOIN UNNEST(details) as dt
    WHERE cnt < 420 AND dt.favOrUnfav = 420
)

SELECT tweetId, COUNT(DISTINCT(userId)) AS favCount
FROM tweet_raw_favs_table
GROUP BY tweetId
