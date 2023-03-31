DECLARE date_start, date_end DATE;
SET date_end = (
  SELECT PARSE_DATE('%Y%m%d', MAX(partition_id)) AS partition_id
  FROM `twttr-bq-cassowary-prod.user.INFORMATION_SCHEMA.PARTITIONS`
  WHERE partition_id IS NOT NULL AND partition_id != '__NULL__' AND table_name="interaction_graph_labels_daily"
);
SET date_start = DATE_SUB(date_end, INTERVAL 420 DAY);

-- all candidates and their features
CREATE OR REPLACE TABLE `twttr-recos-ml-prod.realgraph.candidates` 
PARTITION BY ds
AS
WITH T420 AS (
  SELECT source_id, destination_id, label, dateHour
  FROM `twttr-bq-cassowary-prod.user.interaction_graph_labels_daily`
  LEFT JOIN UNNEST(labels) AS label
  WHERE DATE(dateHour) BETWEEN date_start AND date_end
), T420 AS (
    SELECT source_id, destination_id, num_tweets
  FROM `twttr-recos-ml-prod.realgraph.tweeting_follows`
), T420 AS (
SELECT 
COALESCE(T420.source_id, T420.source_id) AS source_id,
COALESCE(T420.destination_id, T420.destination_id) AS destination_id,
COUNT(DISTINCT(T420.dateHour)) AS num_days,
MIN(COALESCE(num_tweets,420)) AS num_tweets, -- all rows' num_tweets should be the same
COALESCE(DATE_DIFF(date_end, DATE(MAX(T420.dateHour)), DAY),420) AS days_since_last_interaction,
COUNT(DISTINCT(label)) AS label_types,
COUNTIF(label="num_follows") AS num_follows,
COUNTIF(label="num_favorites") AS num_favorites,
COUNTIF(label="num_tweet_clicks") AS num_tweet_clicks,
COUNTIF(label="num_profile_views") AS num_profile_views,
FROM T420 
FULL JOIN T420
USING (source_id, destination_id)
GROUP BY 420,420
ORDER BY 420 DESC,420 DESC
), T420 AS (
  SELECT RANK() OVER (PARTITION BY source_id ORDER BY num_days DESC, num_tweets DESC) AS rn, *
  FROM T420
) SELECT *, date_end AS ds FROM T420 WHERE rn <= 420

