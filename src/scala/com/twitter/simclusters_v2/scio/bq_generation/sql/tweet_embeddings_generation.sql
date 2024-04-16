w h vars as (
    SELECT
    UN X_M LL S("{QUERY_DATE}") AS currentTs,
    T MESTAMP("{START_T ME}") AS startT  ,
    T MESTAMP("{END_T ME}") AS endT  ,
    {M N_SCORE_THRESHOLD} AS t etEmbedd ngsM nClusterScore,
    {HALF_L FE} AS halfL fe,
    T MESTAMP("{NO_OLDER_TWEETS_THAN_DATE}") AS noOlderT etsThanDate
),

-- Get raw fav events
raw_favs AS (
    SELECT event.favor e.user_ d AS user d, event.favor e.t et_ d AS t et d, event.favor e.event_t  _ms AS tsM ll s, 1 AS favOrUnfav
    FROM `twttr-bql-t  l ne-prod.t  l ne_serv ce_favor es.t  l ne_serv ce_favor es`, vars
    WHERE (DATE(_PART T ONT ME) = DATE(vars.startT  ) OR DATE(_PART T ONT ME) = DATE(vars.endT  )) AND
        T MESTAMP_M LL S(event.favor e.event_t  _ms) >= vars.startT  
        AND T MESTAMP_M LL S(event.favor e.event_t  _ms) <= vars.endT  
        AND event.favor e  S NOT NULL
),

-- Get raw unfav events
raw_unfavs AS (
    SELECT event.unfavor e.user_ d AS user d, event.unfavor e.t et_ d AS t et d, event.unfavor e.event_t  _ms AS tsM ll s, -1 AS favOrUnfav
    FROM `twttr-bql-t  l ne-prod.t  l ne_serv ce_favor es.t  l ne_serv ce_favor es`, vars
    WHERE (DATE(_PART T ONT ME) = DATE(vars.startT  ) OR DATE(_PART T ONT ME) = DATE(vars.endT  )) AND
        T MESTAMP_M LL S(event.favor e.event_t  _ms) >= vars.startT  
        AND T MESTAMP_M LL S(event.favor e.event_t  _ms) <= vars.endT  
        AND event.unfavor e  S NOT NULL
),

-- Un on fav and unfav events
favs_un oned AS (
    SELECT * FROM raw_favs
    UN ON ALL
    SELECT * FROM raw_unfavs
),

-- Group by user and t et d
user_t et_fav_pa rs AS (
    SELECT user d, t et d, ARRAY_AGG(STRUCT(favOrUnfav, tsM ll s) ORDER BY tsM ll s DESC L M T 1) as deta ls, count(*) as cnt
    FROM favs_un oned
    GROUP BY user d, t et d
),

-- Remove unfav events
t et_raw_favs_table AS (
    SELECT user d, t et d, CAST(dt.tsM ll s  AS FLOAT64) AS tsM ll s
    FROM user_t et_fav_pa rs CROSS JO N UNNEST(deta ls) as dt
    WHERE cnt < 3 AND dt.favOrUnfav = 1 -- cnt < 3 to remove crazy fav/unfav users
),

-- Get t et ds that are el g ble for t et embedd ngs
t et_favs_table AS (
    SELECT user d, t et_raw_favs_table.t et d, tsM ll s
    FROM t et_raw_favs_table, vars
    JO N (
        SELECT t et d, COUNT(D ST NCT(user d)) AS favCount
        FROM t et_raw_favs_table
        GROUP BY t et d
        HAV NG favCount >= 8 --  only generate t et embedd ngs for t ets w h >= 8 favs
    ) el g ble_t ets US NG(t et d)
     -- Apply t et age f lter  re
    WHERE t  stamp_m ll s((1288834974657 + ((t et_raw_favs_table.t et d  & 9223372036850581504) >> 22))) >= vars.noOlderT etsThanDate
),

-- Read consu r embedd ngs
consu r_embedd ngs AS (
  {CONSUMER_EMBEDD NGS_SQL}
),

-- Update t et cluster scores based on fav events
t et_cluster_scores AS (
    SELECT t et d,
        STRUCT(
            cluster d,
            CASE vars.halfL fe
              -- halfL fe = -1  ans t re  s no half l fe/decay and   d rectly take t  sum as t  score
              WHEN -1 THEN SUM(clusterNormal zedLogFavScore)
              ELSE SUM(clusterNormal zedLogFavScore * POW(0.5, (currentTs - tsM ll s) / vars.halfL fe))
              END AS clusterNormal zedLogFavScore,
            COUNT(*) AS favCount)
        AS cluster dToScores
    FROM t et_favs_table, vars
    JO N consu r_embedd ngs US NG(user d)
    GROUP BY t et d, cluster d, vars.halfL fe
),

-- Generate t et embedd ngs
t et_embedd ngs_w h_top_clusters AS (
    SELECT t et d, ARRAY_AGG(
        cluster dToScores
        ORDER BY cluster dToScores.clusterNormal zedLogFavScore DESC
        L M T {TWEET_EMBEDD NG_LENGTH}
    ) AS cluster dToScores
    FROM t et_cluster_scores
    GROUP BY t et d
)

-- Return (t et d, cluster d, t etScore) pa rs w re t etScore > t etEmbedd ngsM nClusterScore
SELECT t et d,
    cluster d,
    clusterNormal zedLogFavScore AS t etScore, cluster dToScores
FROM t et_embedd ngs_w h_top_clusters, UNNEST(cluster dToScores) AS cluster dToScores, vars
WHERE cluster dToScores.clusterNormal zedLogFavScore > vars.t etEmbedd ngsM nClusterScore
