-- This SQL quelonry gelonnelonratelon thelon clustelonr to top k twelonelonts indelonx baselond on twelonelont elonngagelonmelonnts.
-- Thelon elonngagelonmelonnt typelon is deloncidelond by USelonR_TWelonelonT_elonNGAGelonMelonNT_TABLelon_SQL.

with vars as (
        SelonLelonCT {HALF_LIFelon} AS halfLifelon, -- Delonfault: 8 hour halfLifelon in millis
        UNIX_MILLIS("{CURRelonNT_TS}") AS currelonntTs,
    ),

  uselonr_twelonelont_elonngagelonmelonnt_pairs AS (
      {USelonR_TWelonelonT_elonNGAGelonMelonNT_TABLelon_SQL}
  ),

  -- A selonquelonncelon of filtelonrs to gelont elonligiblelon twelonelontIds for twelonelont elonmbelondding gelonnelonration
  -- Apply min intelonraction count filtelonr
 uselonr_twelonelont_intelonraction_with_min_intelonraction_count_filtelonr AS (
      SelonLelonCT uselonrId, uselonr_twelonelont_elonngagelonmelonnt_pairs.twelonelontId, tsMillis
      FROM uselonr_twelonelont_elonngagelonmelonnt_pairs, vars
      JOIN (
          SelonLelonCT twelonelontId, COUNT(DISTINCT(uselonrId)) AS intelonractionCount
          FROM uselonr_twelonelont_elonngagelonmelonnt_pairs
          GROUP BY twelonelontId
          HAVING intelonractionCount >= {MIN_INTelonRACTION_COUNT} -- Only gelonnelonratelon twelonelont elonmbelonddings for twelonelonts with >= {MIN_INTelonRACTION_COUNT} intelonractions
      ) elonligiblelon_twelonelonts USING(twelonelontId)
  ),

  -- Apply min fav count filtelonr
  uselonr_twelonelont_intelonraction_with_fav_count_filtelonr AS (
    {TWelonelonT_INTelonRACTION_WITH_FAV_COUNT_FILTelonR_SQL}
  ),

  -- Apply helonalth and videlono filtelonr
  uselonr_twelonelont_intelonraction_with_helonalth_filtelonr AS (
    {TWelonelonT_INTelonRACTION_WITH_HelonALTH_FILTelonR_SQL}
  ),

  -- Final filtelonrelond uselonr twelonelont intelonraction tablelon
  -- Relonad thelon relonsult from thelon last filtelonr
  uselonr_twelonelont_intelonraction_procelonsselond_tablelon AS (
    SelonLelonCT *
    FROM uselonr_twelonelont_intelonraction_with_helonalth_filtelonr
  ),

  -- Relonad consumelonr elonmbelonddings
  consumelonr_elonmbelonddings AS (
     {CONSUMelonR_elonMBelonDDINGS_SQL}
  ),

  -- Updatelon twelonelont clustelonr scorelons baselond on intelonraction elonvelonnts
  twelonelont_clustelonr_scorelons AS (
      SelonLelonCT twelonelontId,
          STRUCT(
              clustelonrId,
              CASelon vars.halfLifelon
                -- halfLifelon = -1 melonans thelonrelon is no half lifelon deloncay and welon direlonctly takelon thelon sum as thelon scorelon
                WHelonN -1 THelonN SUM(clustelonrNormalizelondLogFavScorelon)
                elonLSelon SUM(clustelonrNormalizelondLogFavScorelon * POW(0.5, (currelonntTs - tsMillis) / vars.halfLifelon))
                elonND AS normalizelondScorelon,
              COUNT(*) AS elonngagelonmelonntCount)
          AS clustelonrIdToScorelons
      FROM uselonr_twelonelont_intelonraction_procelonsselond_tablelon, vars
      JOIN consumelonr_elonmbelonddings USING(uselonrId)
      GROUP BY twelonelontId, clustelonrId, vars.halfLifelon
  ),

  -- Gelonnelonratelon twelonelont elonmbelonddings
  twelonelont_elonmbelonddings_with_top_clustelonrs AS (
      SelonLelonCT twelonelontId, ARRAY_AGG(
          clustelonrIdToScorelons
          ORDelonR BY clustelonrIdToScorelons.normalizelondScorelon DelonSC
          LIMIT {TWelonelonT_elonMBelonDDING_LelonNGTH}
      ) AS clustelonrIdToScorelons
      FROM twelonelont_clustelonr_scorelons
      GROUP BY twelonelontId
  ),

  clustelonrs_top_k_twelonelonts AS (
    SelonLelonCT clustelonrId, ARRAY_AGG(STRUCT(twelonelontId, normalizelondScorelon AS twelonelontScorelon) ORDelonR BY normalizelondScorelon DelonSC LIMIT {CLUSTelonR_TOP_K_TWelonelonTS}) AS topKTwelonelontsForClustelonrKelony
    FROM twelonelont_elonmbelonddings_with_top_clustelonrs, UNNelonST(clustelonrIdToScorelons) AS clustelonrIdToScorelons
    WHelonRelon elonngagelonmelonntCount >= {MIN_elonNGAGelonMelonNT_PelonR_CLUSTelonR}
    GROUP BY clustelonrId
  )

SelonLelonCT *
FROM clustelonrs_top_k_twelonelonts

