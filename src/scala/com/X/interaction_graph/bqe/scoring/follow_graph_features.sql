DECLARE date_latest_tweet, date_latest_follows DATE;
SET date_latest_tweet = (
  SELECT PARSE_DATE('%Y%m%d', SUBSTRING(MAX(partition_id), 1, 8)) AS partition_id
  FROM `twttr-bq-tweetsource-pub-prod.user.INFORMATION_SCHEMA.PARTITIONS`
  WHERE partition_id IS NOT NULL AND partition_id != '__NULL__' AND table_name="public_tweets");
SET date_latest_follows = (
  SELECT PARSE_DATE('%Y%m%d', MAX(partition_id)) AS partition_id
  FROM `twttr-recos-ml-prod.user_events.INFORMATION_SCHEMA.PARTITIONS`
  WHERE partition_id IS NOT NULL AND partition_id != '__NULL__' AND table_name="valid_user_follows");

-- tweet count candidate features
CREATE OR REPLACE TABLE `twttr-recos-ml-prod.realgraph.tweeting_follows`
PARTITION BY ds
AS
WITH tweet_count AS (
  SELECT userId, COUNT(userId) AS num_tweets
  FROM `twttr-bq-tweetsource-pub-prod.user.public_tweets`
  WHERE DATE(ts) BETWEEN DATE_SUB(date_latest_tweet, INTERVAL 3 DAY) AND date_latest_tweet
  GROUP BY 1
), all_follows AS (
  SELECT F.sourceId AS source_id, F.destinationId AS destination_id, COALESCE(T.num_tweets,0) AS num_tweets,
  ROW_NUMBER() OVER (PARTITION BY F.sourceId ORDER BY T.num_tweets DESC) AS rn
  FROM `twttr-recos-ml-prod.user_events.valid_user_follows` F
  LEFT JOIN tweet_count T
  ON F.destinationId=T.userId
  WHERE DATE(F._PARTITIONTIME) = date_latest_follows
) SELECT *, date_latest_tweet AS ds FROM all_follows  WHERE rn <= 2000
;
