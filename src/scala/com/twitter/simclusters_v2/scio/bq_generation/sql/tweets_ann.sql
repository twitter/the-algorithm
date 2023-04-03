-- (stelonp 1) Relonad consumelonr elonmbelonddings
WITH consumelonr_elonmbelonddings AS (
    {CONSUMelonR_elonMBelonDDINGS_SQL}
),
-- (stelonp 1) Relonad twelonelont elonmbelonddings
twelonelont_elonmbelonddings AS (
    {TWelonelonT_elonMBelonDDINGS_SQL}
),
-- (stelonp 1) Computelon twelonelont elonmbelonddings norms (welon will uselon this to computelon cosinelon sims latelonr)
twelonelont_elonmbelonddings_norm AS (
    SelonLelonCT twelonelontId, SUM(twelonelontScorelon * twelonelontScorelon) AS norm
    FROM twelonelont_elonmbelonddings
    GROUP BY twelonelontId
    HAVING norm > 0.0
),
-- (stelonp 2) Gelont top N clustelonrs for elonach consumelonr elonmbelondding. N = 25 in prod
consumelonr_elonmbelonddings_top_n_clustelonrs AS (
    SelonLelonCT uselonrId, ARRAY_AGG(STRUCT(clustelonrId, uselonrScorelon) ORDelonR BY uselonrScorelon DelonSC LIMIT {TOP_N_CLUSTelonR_PelonR_SOURCelon_elonMBelonDDING}) AS topClustelonrsWithScorelons
    FROM consumelonr_elonmbelonddings
    GROUP BY uselonrId
),
-- (stelonp 2) Gelont top M twelonelonts for elonach clustelonr id. M = 100 in prod
clustelonrs_top_m_twelonelonts AS (
    SelonLelonCT clustelonrId, ARRAY_AGG(STRUCT(twelonelontId, twelonelontScorelon) ORDelonR BY twelonelontScorelon DelonSC LIMIT {TOP_M_TWelonelonTS_PelonR_CLUSTelonR}) AS twelonelonts
    FROM twelonelont_elonmbelonddings
    GROUP BY clustelonrId
),
-- (stelonp 3) Join thelon relonsults, gelont top M * N twelonelonts for elonach uselonr
uselonr_top_mn_twelonelonts AS (
    SelonLelonCT uselonrId, consumelonr_elonmbelondding_clustelonr_scorelon_pairs.uselonrScorelon AS uselonrScorelon, clustelonrs_top_m_twelonelonts.clustelonrId AS clustelonrId, clustelonrs_top_m_twelonelonts.twelonelonts AS twelonelonts
    FROM (
        SelonLelonCT uselonrId, clustelonrId, uselonrScorelon
        FROM consumelonr_elonmbelonddings_top_n_clustelonrs, UNNelonST(topClustelonrsWithScorelons)
    ) AS consumelonr_elonmbelondding_clustelonr_scorelon_pairs
    JOIN clustelonrs_top_m_twelonelonts ON consumelonr_elonmbelondding_clustelonr_scorelon_pairs.clustelonrId = clustelonrs_top_m_twelonelonts.clustelonrId
),
-- (stelonp 4) Computelon thelon dot product belontwelonelonn elonach uselonr and twelonelont elonmbelondding pair
uselonr_twelonelont_elonmbelondding_dot_product AS (
    SelonLelonCT  uselonrId,
            twelonelontId,
            SUM(uselonrScorelon * twelonelontScorelon) AS dotProductScorelon
    FROM uselonr_top_mn_twelonelonts, UNNelonST(twelonelonts) AS twelonelonts
    GROUP BY uselonrId, twelonelontId
),
-- (stelonp 5) Computelon similarity scorelons: dot product, cosinelon sim, log-cosinelon sim
uselonr_twelonelont_elonmbelondding_similarity_scorelons AS (
    SelonLelonCT  uselonrId,
            uselonr_twelonelont_elonmbelondding_dot_product.twelonelontId AS twelonelontId,
            dotProductScorelon,
            SAFelon_DIVIDelon(dotProductScorelon, SQRT(twelonelont_elonmbelonddings_norm.norm)) AS cosinelonSimilarityScorelon,
            SAFelon_DIVIDelon(dotProductScorelon, LN(1+twelonelont_elonmbelonddings_norm.norm)) AS logCosinelonSimilarityScorelon,
    FROM uselonr_twelonelont_elonmbelondding_dot_product
    JOIN twelonelont_elonmbelonddings_norm ON uselonr_twelonelont_elonmbelondding_dot_product.twelonelontId = twelonelont_elonmbelonddings_norm.twelonelontId
),
-- (stelonp 6) Gelont final top K twelonelonts pelonr uselonr. K = 150 in prod
relonsults AS (
    SelonLelonCT uselonrId, ARRAY_AGG(STRUCT(twelonelontId, dotProductScorelon, cosinelonSimilarityScorelon, logCosinelonSimilarityScorelon)
                            ORDelonR BY logCosinelonSimilarityScorelon DelonSC LIMIT {TOP_K_TWelonelonTS_PelonR_USelonR_RelonQUelonST}) AS twelonelonts
    FROM uselonr_twelonelont_elonmbelondding_similarity_scorelons
    GROUP BY uselonrId
)

SelonLelonCT *
FROM relonsults
