DECLARE date_end, date_latest_follows DATE;
SET date_end = (
  SELECT PARSE_DATE('%Y%m%d', MAX(part  on_ d)) AS part  on_ d
  FROM `twttr-bq-cassowary-prod.user. NFORMAT ON_SCHEMA.PART T ONS`
  WHERE part  on_ d  S NOT NULL AND part  on_ d != '__NULL__' AND table_na =" nteract on_graph_labels_da ly"
);
SET date_latest_follows = (
  SELECT PARSE_DATE('%Y%m%d', MAX(part  on_ d)) AS part  on_ d
  FROM `twttr-recos-ml-prod.user_events. NFORMAT ON_SCHEMA.PART T ONS`
  WHERE part  on_ d  S NOT NULL AND part  on_ d != '__NULL__' AND table_na ="val d_user_follows");

DELETE
FROM `twttr-recos-ml-prod.realgraph.scores`
WHERE ds = date_end;

-- score cand dates (59m)
 NSERT  NTO `twttr-recos-ml-prod.realgraph.scores`
W TH pred cted_scores AS (
  SELECT
    s ce_ d, 
    dest nat on_ d, 
    p1.prob AS prob, 
    p2.prob AS prob_expl c 
  FROM ML.PRED CT(MODEL `twttr-recos-ml-prod.realgraph.prod`,
      (
      SELECT
        *
      FROM
        `twttr-recos-ml-prod.realgraph.cand dates` ) ) S1
  CROSS JO N UNNEST(S1.pred cted_label_probs) AS p1
  JO N ML.PRED CT(MODEL `twttr-recos-ml-prod.realgraph.prod_expl c `,
      (
      SELECT
        *
      FROM
        `twttr-recos-ml-prod.realgraph.cand dates` ) ) S2
  US NG (s ce_ d, dest nat on_ d)
  CROSS JO N UNNEST(S2.pred cted_label_probs) AS p2
  WHERE p1.label=1 AND p2.label=1
)
SELECT 
  COALESCE(pred cted_scores.s ce_ d, t et ng_follows.s ce_ d) AS s ce_ d,
  COALESCE(pred cted_scores.dest nat on_ d, t et ng_follows.dest nat on_ d) AS dest nat on_ d,
  COALESCE(prob, 0.0) AS prob,
  COALESCE(prob_expl c , 0.0) AS prob_expl c ,
  (t et ng_follows.s ce_ d  S NOT NULL) AND (t et ng_follows.dest nat on_ d  S NOT NULL) AS follo d,
  date_end AS ds
FROM
  pred cted_scores
  FULL JO N
  `twttr-recos-ml-prod.realgraph.t et ng_follows` t et ng_follows
  US NG (s ce_ d, dest nat on_ d)
