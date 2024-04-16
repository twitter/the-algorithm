-- (step 1) Read consu r embedd ngs
W TH consu r_embedd ngs AS (
    {CONSUMER_EMBEDD NGS_SQL}
),
-- (step 1) Read t et embedd ngs
t et_embedd ngs AS (
    {TWEET_EMBEDD NGS_SQL}
),
-- (step 1) Compute t et embedd ngs norms (  w ll use t  to compute cos ne s ms later)
t et_embedd ngs_norm AS (
    SELECT t et d, SUM(t etScore * t etScore) AS norm
    FROM t et_embedd ngs
    GROUP BY t et d
    HAV NG norm > 0.0
),
-- (step 2) Get top N clusters for each consu r embedd ng. N = 25  n prod
consu r_embedd ngs_top_n_clusters AS (
    SELECT user d, ARRAY_AGG(STRUCT(cluster d, userScore) ORDER BY userScore DESC L M T {TOP_N_CLUSTER_PER_SOURCE_EMBEDD NG}) AS topClustersW hScores
    FROM consu r_embedd ngs
    GROUP BY user d
),
-- (step 2) Get top M t ets for each cluster  d. M = 100  n prod
clusters_top_m_t ets AS (
    SELECT cluster d, ARRAY_AGG(STRUCT(t et d, t etScore) ORDER BY t etScore DESC L M T {TOP_M_TWEETS_PER_CLUSTER}) AS t ets
    FROM t et_embedd ngs
    GROUP BY cluster d
),
-- (step 3) Jo n t  results, get top M * N t ets for each user
user_top_mn_t ets AS (
    SELECT user d, consu r_embedd ng_cluster_score_pa rs.userScore AS userScore, clusters_top_m_t ets.cluster d AS cluster d, clusters_top_m_t ets.t ets AS t ets
    FROM (
        SELECT user d, cluster d, userScore
        FROM consu r_embedd ngs_top_n_clusters, UNNEST(topClustersW hScores)
    ) AS consu r_embedd ng_cluster_score_pa rs
    JO N clusters_top_m_t ets ON consu r_embedd ng_cluster_score_pa rs.cluster d = clusters_top_m_t ets.cluster d
),
-- (step 4) Compute t  dot product bet en each user and t et embedd ng pa r
user_t et_embedd ng_dot_product AS (
    SELECT  user d,
            t et d,
            SUM(userScore * t etScore) AS dotProductScore
    FROM user_top_mn_t ets, UNNEST(t ets) AS t ets
    GROUP BY user d, t et d
),
-- (step 5) Compute s m lar y scores: dot product, cos ne s m, log-cos ne s m
user_t et_embedd ng_s m lar y_scores AS (
    SELECT  user d,
            user_t et_embedd ng_dot_product.t et d AS t et d,
            dotProductScore,
            SAFE_D V DE(dotProductScore, SQRT(t et_embedd ngs_norm.norm)) AS cos neS m lar yScore,
            SAFE_D V DE(dotProductScore, LN(1+t et_embedd ngs_norm.norm)) AS logCos neS m lar yScore,
    FROM user_t et_embedd ng_dot_product
    JO N t et_embedd ngs_norm ON user_t et_embedd ng_dot_product.t et d = t et_embedd ngs_norm.t et d
),
-- (step 6) Get f nal top K t ets per user. K = 150  n prod
results AS (
    SELECT user d, ARRAY_AGG(STRUCT(t et d, dotProductScore, cos neS m lar yScore, logCos neS m lar yScore)
                            ORDER BY logCos neS m lar yScore DESC L M T {TOP_K_TWEETS_PER_USER_REQUEST}) AS t ets
    FROM user_t et_embedd ng_s m lar y_scores
    GROUP BY user d
)

SELECT *
FROM results
