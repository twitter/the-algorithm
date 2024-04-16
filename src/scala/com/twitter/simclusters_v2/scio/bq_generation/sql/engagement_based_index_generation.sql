-- T  SQL query generate t  cluster to top k t ets  ndex based on t et engage nts.
-- T  engage nt type  s dec ded by USER_TWEET_ENGAGEMENT_TABLE_SQL.

w h vars as (
        SELECT {HALF_L FE} AS halfL fe, -- Default: 8 h  halfL fe  n m ll s
        UN X_M LL S("{CURRENT_TS}") AS currentTs,
    ),

  user_t et_engage nt_pa rs AS (
      {USER_TWEET_ENGAGEMENT_TABLE_SQL}
  ),

  -- A sequence of f lters to get el g ble t et ds for t et embedd ng generat on
  -- Apply m n  nteract on count f lter
 user_t et_ nteract on_w h_m n_ nteract on_count_f lter AS (
      SELECT user d, user_t et_engage nt_pa rs.t et d, tsM ll s
      FROM user_t et_engage nt_pa rs, vars
      JO N (
          SELECT t et d, COUNT(D ST NCT(user d)) AS  nteract onCount
          FROM user_t et_engage nt_pa rs
          GROUP BY t et d
          HAV NG  nteract onCount >= {M N_ NTERACT ON_COUNT} -- Only generate t et embedd ngs for t ets w h >= {M N_ NTERACT ON_COUNT}  nteract ons
      ) el g ble_t ets US NG(t et d)
  ),

  -- Apply m n fav count f lter
  user_t et_ nteract on_w h_fav_count_f lter AS (
    {TWEET_ NTERACT ON_W TH_FAV_COUNT_F LTER_SQL}
  ),

  -- Apply  alth and v deo f lter
  user_t et_ nteract on_w h_ alth_f lter AS (
    {TWEET_ NTERACT ON_W TH_HEALTH_F LTER_SQL}
  ),

  -- F nal f ltered user t et  nteract on table
  -- Read t  result from t  last f lter
  user_t et_ nteract on_processed_table AS (
    SELECT *
    FROM user_t et_ nteract on_w h_ alth_f lter
  ),

  -- Read consu r embedd ngs
  consu r_embedd ngs AS (
     {CONSUMER_EMBEDD NGS_SQL}
  ),

  -- Update t et cluster scores based on  nteract on events
  t et_cluster_scores AS (
      SELECT t et d,
          STRUCT(
              cluster d,
              CASE vars.halfL fe
                -- halfL fe = -1  ans t re  s no half l fe decay and   d rectly take t  sum as t  score
                WHEN -1 THEN SUM(clusterNormal zedLogFavScore)
                ELSE SUM(clusterNormal zedLogFavScore * POW(0.5, (currentTs - tsM ll s) / vars.halfL fe))
                END AS normal zedScore,
              COUNT(*) AS engage ntCount)
          AS cluster dToScores
      FROM user_t et_ nteract on_processed_table, vars
      JO N consu r_embedd ngs US NG(user d)
      GROUP BY t et d, cluster d, vars.halfL fe
  ),

  -- Generate t et embedd ngs
  t et_embedd ngs_w h_top_clusters AS (
      SELECT t et d, ARRAY_AGG(
          cluster dToScores
          ORDER BY cluster dToScores.normal zedScore DESC
          L M T {TWEET_EMBEDD NG_LENGTH}
      ) AS cluster dToScores
      FROM t et_cluster_scores
      GROUP BY t et d
  ),

  clusters_top_k_t ets AS (
    SELECT cluster d, ARRAY_AGG(STRUCT(t et d, normal zedScore AS t etScore) ORDER BY normal zedScore DESC L M T {CLUSTER_TOP_K_TWEETS}) AS topKT etsForClusterKey
    FROM t et_embedd ngs_w h_top_clusters, UNNEST(cluster dToScores) AS cluster dToScores
    WHERE engage ntCount >= {M N_ENGAGEMENT_PER_CLUSTER}
    GROUP BY cluster d
  )

SELECT *
FROM clusters_top_k_t ets

