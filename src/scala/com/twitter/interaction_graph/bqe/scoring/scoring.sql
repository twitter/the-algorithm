DECLARE date_end, date_latest_follows DATE;
SET date_end = (
  SELECT PARSE_DATE('%Y%m%d', MAX(partition_id)) AS partition_id
  FROM `twttr-bq-cassowary-prod.user.INFORMATION_SCHEMA.PARTITIONS`
  WHERE partition_id IS NOT NULL AND partition_id != '__NULL__' AND table_name="interaction_graph_labels_daily"
);
SET date_latest_follows = (
  SELECT PARSE_DATE('%Y%m%d', MAX(partition_id)) AS partition_id
  FROM `twttr-recos-ml-prod.user_events.INFORMATION_SCHEMA.PARTITIONS`
  WHERE partition_id IS NOT NULL AND partition_id != '__NULL__' AND table_name="valid_user_follows");

DELETE
FROM `twttr-recos-ml-prod.realgraph.scores`
WHERE ds = date_end;

-- score candidates (59m)
INSERT INTO `twttr-recos-ml-prod.realgraph.scores`
WITH predicted_scores AS (
  SELECT
    source_id, 
    destination_id, 
    p1.prob AS prob, 
    p2.prob AS prob_explicit
  FROM ML.PREDICT(MODEL `twttr-recos-ml-prod.realgraph.prod`,
      (
      SELECT
        *
      FROM
        `twttr-recos-ml-prod.realgraph.candidates` ) ) S1
  CROSS JOIN UNNEST(S1.predicted_label_probs) AS p1
  JOIN ML.PREDICT(MODEL `twttr-recos-ml-prod.realgraph.prod_explicit`,
      (
      SELECT
        *
      FROM
        `twttr-recos-ml-prod.realgraph.candidates` ) ) S2
  USING (source_id, destination_id)
  CROSS JOIN UNNEST(S2.predicted_label_probs) AS p2
  WHERE p1.label=1 AND p2.label=1
)
SELECT 
  COALESCE(predicted_scores.source_id, tweeting_follows.source_id) AS source_id,
  COALESCE(predicted_scores.destination_id, tweeting_follows.destination_id) AS destination_id,
  COALESCE(prob, 0.0) AS prob,
  COALESCE(prob_explicit, 0.0) AS prob_explicit,
  (tweeting_follows.source_id IS NOT NULL) AND (tweeting_follows.destination_id IS NOT NULL) AS followed,
  date_end AS ds
FROM
  predicted_scores
  FULL JOIN
  `twttr-recos-ml-prod.realgraph.tweeting_follows` tweeting_follows
  USING (source_id, destination_id)
