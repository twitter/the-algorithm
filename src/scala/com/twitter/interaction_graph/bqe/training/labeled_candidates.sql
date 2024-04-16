-- date_labels  s 1 day after date_cand dates (wh ch  s t  current batch run's start date)
DECLARE date_cand dates, date_labels DATE;
DECLARE pos  ve_rate FLOAT64;
SET date_cand dates = (SELECT DATE(T MESTAMP_M LL S($start_t  $)));
SET date_labels = DATE_ADD(date_cand dates,  NTERVAL 1 DAY);

CREATE TABLE  F NOT EX STS `twttr-recos-ml-prod.realgraph.labeled_cand dates$table_suff x$` AS
SELECT
  0 AS s ce_ d,
  1 AS dest nat on_ d,
  1 AS label,
  1 AS num_days,
  1 AS num_t ets,
  1 AS num_follows,
  1 AS num_favor es,
  1 AS num_t et_cl cks,
  1 AS num_prof le_v ews,
  1 AS days_s nce_last_ nteract on,
  1 AS label_types,
  DATE("2023-01-08") AS ds;

-- delete any pr or data to avo d double wr  ng
DELETE
FROM `twttr-recos-ml-prod.realgraph.labeled_cand dates$table_suff x$`
WHERE ds = date_cand dates;

-- jo n labels w h cand dates w h 1 day attr but on delay and  nsert new seg nt
 NSERT  NTO `twttr-recos-ml-prod.realgraph.labeled_cand dates$table_suff x$` 
W TH label_pos  ve AS (
  SELECT s ce_ d, dest nat on_ d
  FROM `twttr-bq-cassowary-prod.user. nteract on_graph_labels_da ly`
  WHERE DATE(dateH )=date_labels
), label_negat ve AS (
  SELECT s ce_ d, dest nat on_ d
  FROM `twttr-bq-cassowary-prod.user. nteract on_graph_agg_negat ve_edge_snapshot`
) SELECT 
  F.s ce_ d,
  F.dest nat on_ d,
  CASE WHEN P.s ce_ d  S NULL THEN 0 ELSE 1 END AS label,
  num_days,
  num_t ets,
  num_follows,
  num_favor es,
  num_t et_cl cks,
  num_prof le_v ews,
  days_s nce_last_ nteract on,
  label_types,
  date_cand dates AS ds
FROM `twttr-recos-ml-prod.realgraph.cand dates_sampled` F
LEFT JO N label_pos  ve P US NG(s ce_ d, dest nat on_ d)
LEFT JO N label_negat ve N US NG(s ce_ d, dest nat on_ d)
WHERE N.s ce_ d  S NULL AND N.dest nat on_ d  S NULL
AND F.ds=date_cand dates
;

-- get pos  ve rate 
SET pos  ve_rate = 
(SELECT SUM(label)/COUNT(label) AS pct_pos  ve
FROM `twttr-recos-ml-prod.realgraph.labeled_cand dates$table_suff x$`
);

-- create tra n ng dataset w h negat ve downsampl ng (should get ~50-50 spl )
-- t  spans over t  cumulat ve date range of t  labeled cand dates table.
CREATE OR REPLACE TABLE `twttr-recos-ml-prod.realgraph.tra n$table_suff x$` AS
SELECT * FROM `twttr-recos-ml-prod.realgraph.labeled_cand dates$table_suff x$`
WHERE CASE WHEN label = 0 AND RAND() < pos  ve_rate THEN true WHEN label = 1 AND RAND() < (1-pos  ve_rate) THEN true ELSE false END
;
