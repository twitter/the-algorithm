-- date_labels is 1 day after date_candidates (which is the current batch run's start date)
DECLARE date_candidates, date_labels DATE;
DECLARE positive_rate FLOAT64;
SET date_candidates = (SELECT DATE(TIMESTAMP_MILLIS($start_time$)));
SET date_labels = DATE_ADD(date_candidates, INTERVAL 1 DAY);

CREATE TABLE IF NOT EXISTS `twttr-recos-ml-prod.realgraph.labeled_candidates$table_suffix$` AS
SELECT
  0 AS source_id,
  1 AS destination_id,
  1 AS label,
  1 AS num_days,
  1 AS num_tweets,
  1 AS num_follows,
  1 AS num_favorites,
  1 AS num_tweet_clicks,
  1 AS num_profile_views,
  1 AS days_since_last_interaction,
  1 AS label_types,
  DATE("2023-01-08") AS ds;

-- delete any prior data to avoid double writing
DELETE
FROM `twttr-recos-ml-prod.realgraph.labeled_candidates$table_suffix$`
WHERE ds = date_candidates;

-- join labels with candidates with 1 day attribution delay and insert new segment
INSERT INTO `twttr-recos-ml-prod.realgraph.labeled_candidates$table_suffix$` 
WITH label_positive AS (
  SELECT source_id, destination_id
  FROM `twttr-bq-cassowary-prod.user.interaction_graph_labels_daily`
  WHERE DATE(dateHour)=date_labels
), label_negative AS (
  SELECT source_id, destination_id
  FROM `twttr-bq-cassowary-prod.user.interaction_graph_agg_negative_edge_snapshot`
) SELECT 
  F.source_id,
  F.destination_id,
  CASE WHEN P.source_id IS NULL THEN 0 ELSE 1 END AS label,
  num_days,
  num_tweets,
  num_follows,
  num_favorites,
  num_tweet_clicks,
  num_profile_views,
  days_since_last_interaction,
  label_types,
  date_candidates AS ds
FROM `twttr-recos-ml-prod.realgraph.candidates_sampled` F
LEFT JOIN label_positive P USING(source_id, destination_id)
LEFT JOIN label_negative N USING(source_id, destination_id)
WHERE N.source_id IS NULL AND N.destination_id IS NULL
AND F.ds=date_candidates
;

-- get positive rate 
SET positive_rate = 
(SELECT SUM(label)/COUNT(label) AS pct_positive
FROM `twttr-recos-ml-prod.realgraph.labeled_candidates$table_suffix$`
);

-- create training dataset with negative downsampling (should get ~50-50 split)
-- this spans over the cumulative date range of the labeled candidates table.
CREATE OR REPLACE TABLE `twttr-recos-ml-prod.realgraph.train$table_suffix$` AS
SELECT * FROM `twttr-recos-ml-prod.realgraph.labeled_candidates$table_suffix$`
WHERE CASE WHEN label = 0 AND RAND() < positive_rate THEN true WHEN label = 1 AND RAND() < (1-positive_rate) THEN true ELSE false END
;
