W TH t et_embedd ng AS (
-- Expected columns:
-- t et d, cluster d, t etScore
  {TWEET_EMBEDD NG_SQL}
),
clusters_top_k_t ets AS (
  SELECT cluster d, ARRAY_AGG(STRUCT(t et d, t etScore) ORDER BY t etScore DESC L M T {CLUSTER_TOP_K_TWEETS}) AS topKT etsForClusterKey
  FROM t et_embedd ng
  GROUP BY cluster d
)
SELECT
  cluster d,
  topKT etsForClusterKey
FROM clusters_top_k_t ets

