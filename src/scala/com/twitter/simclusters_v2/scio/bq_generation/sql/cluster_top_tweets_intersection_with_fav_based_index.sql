W TH
  cluster_top_t ets AS (
    {CLUSTER_TOP_TWEETS_SQL}
  ),

  flatten_cluster_top_t ets AS (
    SELECT
      cluster d,
      t et.t et d,
      t et.t etScore,
    FROM cluster_top_t ets, UNNEST(topKT etsForClusterKey) AS t et
  ),

--- T re m ght be delay or sk p for t  fav-based dataset.
--- T  query retr eved t  dateH  of t  latest part  on ava lable.
  latest_fav_cluster_to_t et AS (
    SELECT
      MAX(dateH ) AS latestT  stamp
    FROM
      `twttr-bq-cassowary-prod.user.s mclusters_fav_based_cluster_to_t et_ ndex`
    WHERE
      T MESTAMP(dateH ) >= T MESTAMP("{START_T ME}")
      AND T MESTAMP(dateH ) <= T MESTAMP("{END_T ME}")
  ),

  flatten_fav_cluster_top_t ets AS (
    SELECT
      cluster d.cluster d AS cluster d,
      t et.key AS t et d
    FROM
      `twttr-bq-cassowary-prod.user.s mclusters_fav_based_cluster_to_t et_ ndex`,
      UNNEST(topKT etsW hScores.topT etsByFavClusterNormal zedScore) AS t et,
      latest_fav_cluster_to_t et
    WHERE
      dateH =latest_fav_cluster_to_t et.latestT  stamp
  ),

  flatten_cluster_top_t ets_ ntersect on AS (
    SELECT
      cluster d,
      flatten_cluster_top_t ets.t et d,
      flatten_cluster_top_t ets.t etScore
    FROM
      flatten_cluster_top_t ets
     NNER JO N
      flatten_fav_cluster_top_t ets
    US NG(cluster d, t et d)
  ),

  processed_cluster_top_t ets AS (
    SELECT
      cluster d,
      ARRAY_AGG(STRUCT(t et d, t etScore) ORDER BY t etScore L M T {CLUSTER_TOP_K_TWEETS}) AS topKT etsForClusterKey
    FROM flatten_cluster_top_t ets_ ntersect on
    GROUP BY cluster d
  )

 SELECT *
 FROM processed_cluster_top_t ets
