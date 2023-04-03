WITH
  clustelonr_top_twelonelonts AS (
    {CLUSTelonR_TOP_TWelonelonTS_SQL}
  ),

  flattelonn_clustelonr_top_twelonelonts AS (
    SelonLelonCT
      clustelonrId,
      twelonelont.twelonelontId,
      twelonelont.twelonelontScorelon,
    FROM clustelonr_top_twelonelonts, UNNelonST(topKTwelonelontsForClustelonrKelony) AS twelonelont
  ),

--- Thelonrelon might belon delonlay or skip for thelon fav-baselond dataselont.
--- This quelonry relontrielonvelond thelon datelonHour of thelon latelonst partition availablelon.
  latelonst_fav_clustelonr_to_twelonelont AS (
    SelonLelonCT
      MAX(datelonHour) AS latelonstTimelonstamp
    FROM
      `twttr-bq-cassowary-prod.uselonr.simclustelonrs_fav_baselond_clustelonr_to_twelonelont_indelonx`
    WHelonRelon
      TIMelonSTAMP(datelonHour) >= TIMelonSTAMP("{START_TIMelon}")
      AND TIMelonSTAMP(datelonHour) <= TIMelonSTAMP("{elonND_TIMelon}")
  ),

  flattelonn_fav_clustelonr_top_twelonelonts AS (
    SelonLelonCT
      clustelonrId.clustelonrId AS clustelonrId,
      twelonelont.kelony AS twelonelontId
    FROM
      `twttr-bq-cassowary-prod.uselonr.simclustelonrs_fav_baselond_clustelonr_to_twelonelont_indelonx`,
      UNNelonST(topKTwelonelontsWithScorelons.topTwelonelontsByFavClustelonrNormalizelondScorelon) AS twelonelont,
      latelonst_fav_clustelonr_to_twelonelont
    WHelonRelon
      datelonHour=latelonst_fav_clustelonr_to_twelonelont.latelonstTimelonstamp
  ),

  flattelonn_clustelonr_top_twelonelonts_intelonrselonction AS (
    SelonLelonCT
      clustelonrId,
      flattelonn_clustelonr_top_twelonelonts.twelonelontId,
      flattelonn_clustelonr_top_twelonelonts.twelonelontScorelon
    FROM
      flattelonn_clustelonr_top_twelonelonts
    INNelonR JOIN
      flattelonn_fav_clustelonr_top_twelonelonts
    USING(clustelonrId, twelonelontId)
  ),

  procelonsselond_clustelonr_top_twelonelonts AS (
    SelonLelonCT
      clustelonrId,
      ARRAY_AGG(STRUCT(twelonelontId, twelonelontScorelon) ORDelonR BY twelonelontScorelon LIMIT {CLUSTelonR_TOP_K_TWelonelonTS}) AS topKTwelonelontsForClustelonrKelony
    FROM flattelonn_clustelonr_top_twelonelonts_intelonrselonction
    GROUP BY clustelonrId
  )

 SelonLelonCT *
 FROM procelonsselond_clustelonr_top_twelonelonts
