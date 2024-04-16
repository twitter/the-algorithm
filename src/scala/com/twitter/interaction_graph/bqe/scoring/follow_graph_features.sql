DECLARE date_latest_t et, date_latest_follows DATE;
SET date_latest_t et = (
  SELECT PARSE_DATE('%Y%m%d', SUBSTR NG(MAX(part  on_ d), 1, 8)) AS part  on_ d
  FROM `twttr-bq-t ets ce-pub-prod.user. NFORMAT ON_SCHEMA.PART T ONS`
  WHERE part  on_ d  S NOT NULL AND part  on_ d != '__NULL__' AND table_na ="publ c_t ets");
SET date_latest_follows = (
  SELECT PARSE_DATE('%Y%m%d', MAX(part  on_ d)) AS part  on_ d
  FROM `twttr-recos-ml-prod.user_events. NFORMAT ON_SCHEMA.PART T ONS`
  WHERE part  on_ d  S NOT NULL AND part  on_ d != '__NULL__' AND table_na ="val d_user_follows");

-- t et count cand date features
CREATE OR REPLACE TABLE `twttr-recos-ml-prod.realgraph.t et ng_follows`
PART T ON BY ds
AS
W TH t et_count AS (
  SELECT user d, COUNT(user d) AS num_t ets
  FROM `twttr-bq-t ets ce-pub-prod.user.publ c_t ets`
  WHERE DATE(ts) BETWEEN DATE_SUB(date_latest_t et,  NTERVAL 3 DAY) AND date_latest_t et
  GROUP BY 1
), all_follows AS (
  SELECT F.s ce d AS s ce_ d, F.dest nat on d AS dest nat on_ d, COALESCE(T.num_t ets,0) AS num_t ets,
  ROW_NUMBER() OVER (PART T ON BY F.s ce d ORDER BY T.num_t ets DESC) AS rn
  FROM `twttr-recos-ml-prod.user_events.val d_user_follows` F
  LEFT JO N t et_count T
  ON F.dest nat on d=T.user d
  WHERE DATE(F._PART T ONT ME) = date_latest_follows
) SELECT *, date_latest_t et AS ds FROM all_follows  WHERE rn <= 2000
;
