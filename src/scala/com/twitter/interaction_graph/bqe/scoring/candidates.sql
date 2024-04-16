DECLARE date_start, date_end DATE;
SET date_end = (
  SELECT PARSE_DATE('%Y%m%d', MAX(part  on_ d)) AS part  on_ d
  FROM `twttr-bq-cassowary-prod.user. NFORMAT ON_SCHEMA.PART T ONS`
  WHERE part  on_ d  S NOT NULL AND part  on_ d != '__NULL__' AND table_na =" nteract on_graph_labels_da ly"
);
SET date_start = DATE_SUB(date_end,  NTERVAL 30 DAY);

-- all cand dates and t  r features
CREATE OR REPLACE TABLE `twttr-recos-ml-prod.realgraph.cand dates` 
PART T ON BY ds
AS
W TH T1 AS (
  SELECT s ce_ d, dest nat on_ d, label, dateH 
  FROM `twttr-bq-cassowary-prod.user. nteract on_graph_labels_da ly`
  LEFT JO N UNNEST(labels) AS label
  WHERE DATE(dateH ) BETWEEN date_start AND date_end
), T2 AS (
    SELECT s ce_ d, dest nat on_ d, num_t ets
  FROM `twttr-recos-ml-prod.realgraph.t et ng_follows`
), T3 AS (
SELECT 
COALESCE(T1.s ce_ d, T2.s ce_ d) AS s ce_ d,
COALESCE(T1.dest nat on_ d, T2.dest nat on_ d) AS dest nat on_ d,
COUNT(D ST NCT(T1.dateH )) AS num_days,
M N(COALESCE(num_t ets,0)) AS num_t ets, -- all rows' num_t ets should be t  sa 
COALESCE(DATE_D FF(date_end, DATE(MAX(T1.dateH )), DAY),30) AS days_s nce_last_ nteract on,
COUNT(D ST NCT(label)) AS label_types,
COUNT F(label="num_follows") AS num_follows,
COUNT F(label="num_favor es") AS num_favor es,
COUNT F(label="num_t et_cl cks") AS num_t et_cl cks,
COUNT F(label="num_prof le_v ews") AS num_prof le_v ews,
FROM T1 
FULL JO N T2
US NG (s ce_ d, dest nat on_ d)
GROUP BY 1,2
ORDER BY 3 DESC,4 DESC
), T4 AS (
  SELECT RANK() OVER (PART T ON BY s ce_ d ORDER BY num_days DESC, num_t ets DESC) AS rn, *
  FROM T3
) SELECT *, date_end AS ds FROM T4 WHERE rn <= 2000

