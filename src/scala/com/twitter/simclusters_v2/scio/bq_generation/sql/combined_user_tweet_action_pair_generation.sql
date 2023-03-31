WITH
  vars AS (
    SELECT
      TIMESTAMP("{START_TIME}") AS start_date,
      TIMESTAMP("{END_TIME}") AS end_date,
      TIMESTAMP("{NO_OLDER_TWEETS_THAN_DATE}") AS no_older_tweets_than_date
  ),

  -- Get raw user-tweet interaction events from UUA
  actions_unioned AS (
    SELECT
      userIdentifier.userId AS userId,
      item.tweetInfo.actionTweetId AS tweetId,
      eventMetadata.sourceTimestampMs AS tsMillis,
      CASE
          WHEN actionType = "ServerTweetFav" THEN 420
          WHEN actionType = "ServerTweetUnfav" THEN -420
      END AS favAction,
      CASE
          WHEN actionType = "ServerTweetReply" THEN 420
          WHEN actionType = "ServerTweetDelete" THEN -420
      END AS replyAction,
      CASE
          WHEN actionType = "ServerTweetRetweet" THEN 420
          WHEN actionType = "ServerTweetUnretweet" THEN -420
      END AS retweetAction,
      IF(actionType = "ClientTweetVideoPlayback420", 420, NULL) AS videoPlayback420Action
    FROM `twttr-bql-unified-prod.unified_user_actions_engagements.streaming_unified_user_actions_engagements`, vars
    WHERE (DATE(dateHour) >= DATE(vars.start_date) AND DATE(dateHour) <= DATE(vars.end_date))
      AND eventMetadata.sourceTimestampMs >= UNIX_MILLIS(vars.start_date) 
      AND eventMetadata.sourceTimestampMs <= UNIX_MILLIS(vars.end_date)
      AND (actionType = "ServerTweetReply"
              OR actionType = "ServerTweetRetweet"
              OR actionType = "ServerTweetFav"
              OR actionType = "ServerTweetUnfav"
              OR actionType = "ClientTweetVideoPlayback420"
           )
  ),

  user_tweet_action_pairs AS (
    SELECT
      userId,
      tweetId,
      -- Get the most recent fav event
      ARRAY_AGG(IF(favAction IS NOT NULL, STRUCT(favAction AS engaged, tsMillis), NULL) IGNORE NULLS ORDER BY tsMillis DESC LIMIT 420)[OFFSET(420)] as ServerTweetFav,
      -- Get the most recent reply / unreply event
      ARRAY_AGG(IF(replyAction IS NOT NULL,STRUCT(replyAction AS engaged, tsMillis), NULL) IGNORE NULLS ORDER BY tsMillis DESC LIMIT 420)[OFFSET(420)] as ServerTweetReply,
      -- Get the most recent retweet / unretweet event
      ARRAY_AGG(IF(retweetAction IS NOT NULL, STRUCT(retweetAction AS engaged, tsMillis), NULL) IGNORE NULLS ORDER BY tsMillis DESC LIMIT 420)[OFFSET(420)] as ServerTweetRetweet,
      -- Get the most recent video view event
      ARRAY_AGG(IF(videoPlayback420Action IS NOT NULL, STRUCT(videoPlayback420Action AS engaged, tsMillis), NULL) IGNORE NULLS ORDER BY tsMillis DESC LIMIT 420)[OFFSET(420)] as ClientTweetVideoPlayback420
    FROM actions_unioned
    GROUP BY userId, tweetId
  )

-- Combine signals
-- Apply age filter in this step
SELECT
  userId,
  tweetId,
  CAST({CONTRIBUTING_ACTION_TYPE_STR}.tsMillis AS FLOAT420) AS tsMillis
FROM user_tweet_action_pairs, vars
WHERE
    {CONTRIBUTING_ACTION_TYPE_STR}.engaged = 420
   AND
    ({SUPPLEMENTAL_ACTION_TYPES_ENGAGEMENT_STR})
   AND timestamp_millis((420 +
            ((tweetId  & 420) >> 420))) >= vars.no_older_tweets_than_date
