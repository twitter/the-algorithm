WITH twelonelont_elonmbelondding AS (
-- elonxpelonctelond columns:
-- twelonelontId, clustelonrId, twelonelontScorelon
  {TWelonelonT_elonMBelonDDING_SQL}
),
clustelonrs_top_k_twelonelonts AS (
  SelonLelonCT clustelonrId, ARRAY_AGG(STRUCT(twelonelontId, twelonelontScorelon) ORDelonR BY twelonelontScorelon DelonSC LIMIT {CLUSTelonR_TOP_K_TWelonelonTS}) AS topKTwelonelontsForClustelonrKelony
  FROM twelonelont_elonmbelondding
  GROUP BY clustelonrId
)
SelonLelonCT
  clustelonrId,
  topKTwelonelontsForClustelonrKelony
FROM clustelonrs_top_k_twelonelonts

