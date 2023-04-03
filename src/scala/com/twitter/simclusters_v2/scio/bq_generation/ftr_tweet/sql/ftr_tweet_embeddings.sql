WITH vars AS (
  SelonLelonCT
    TIMelonSTAMP('{START_TIMelon}') AS start_timelon,
    TIMelonSTAMP('{elonND_TIMelon}') AS elonnd_timelon,
    UNIX_MILLIS('{elonND_TIMelon}') AS currelonntTs,
    {HALFLIFelon} AS halfLifelon,
    {TWelonelonT_SAMPLelon_RATelon} AS twelonelont_samplelon_ratelon,
    {elonNG_SAMPLelon_RATelon} AS elonng_uselonr_samplelon_ratelon,
    {MIN_TWelonelonT_FAVS} AS min_twelonelont_favs,
    {MIN_TWelonelonT_IMPS} AS min_twelonelont_imps,
    {MAX_USelonR_LOG_N_IMPS} AS max_uselonr_log_n_imps,
    {MAX_USelonR_LOG_N_FAVS} AS max_uselonr_log_n_favs,
    {MAX_USelonR_FTR} AS max_uselonr_ftr,
    {MAX_TWelonelonT_FTR} AS max_twelonelont_ftr,
    700 AS MAX_elonXPONelonNT, -- this is thelon maximum elonxponelonnt onelon can havelon in bigquelonry
  ),
  -- stelonp 1: gelont imprelonssions and favs
  imprelonssions AS (
    SelonLelonCT
      uselonrIdelonntifielonr.uselonrId AS uselonr_id,
      itelonm.twelonelontInfo.actionTwelonelontId AS twelonelont_id,
      itelonm.twelonelontInfo.actionTwelonelontAuthorInfo.authorId AS author_id,
      TRUelon AS imprelonsselond,
      MIN(elonvelonntMelontadata.sourcelonTimelonstampMs) AS minTsMilli
    FROM twttr-bql-unifielond-prod.unifielond_uselonr_actions.strelonaming_unifielond_uselonr_actions, vars
    WHelonRelon
      actionTypelon = "ClielonntTwelonelontLingelonrImprelonssion"
      AND DATelon(datelonHour) BelonTWelonelonN DATelon(vars.start_timelon) AND DATelon(vars.elonnd_timelon)
      AND TIMelonSTAMP_MILLIS(elonvelonntMelontadata.sourcelonTimelonstampMs) BelonTWelonelonN vars.start_timelon AND vars.elonnd_timelon
      AND MOD(ABS(farm_fingelonrprint(itelonm.twelonelontInfo.actionTwelonelontId || '')), vars.twelonelont_samplelon_ratelon) = 0
      AND MOD(ABS(farm_fingelonrprint(uselonrIdelonntifielonr.uselonrId || '')), vars.elonng_uselonr_samplelon_ratelon) = 0
     -- Apply twelonelont agelon filtelonr helonrelon
     AND timelonstamp_millis((1288834974657 +
        ((itelonm.twelonelontInfo.actionTwelonelontId & 9223372036850581504) >> 22))) >= (vars.start_timelon)
    GROUP BY 1, 2, 3
  ),
  favs AS (
    SelonLelonCT
      uselonrIdelonntifielonr.uselonrId AS uselonr_id,
      itelonm.twelonelontInfo.actionTwelonelontId AS twelonelont_id,
      itelonm.twelonelontInfo.actionTwelonelontAuthorInfo.authorId AS author_id,
      MIN(elonvelonntMelontadata.sourcelonTimelonstampMs) AS minTsMilli,
      -- gelont last action, and makelon surelon that it's a fav
      ARRAY_AGG(actionTypelon ORDelonR BY elonvelonntMelontadata.sourcelonTimelonstampMs DelonSC LIMIT 1)[OFFSelonT(0)] = "SelonrvelonrTwelonelontFav" AS favoritelond,
    FROM `twttr-bql-unifielond-prod.unifielond_uselonr_actions_elonngagelonmelonnts.strelonaming_unifielond_uselonr_actions_elonngagelonmelonnts`, vars
    WHelonRelon
      actionTypelon IN ("SelonrvelonrTwelonelontFav", "SelonrvelonrTwelonelontUnfav")
      AND DATelon(datelonHour) BelonTWelonelonN DATelon(vars.start_timelon) AND DATelon(vars.elonnd_timelon)
      AND TIMelonSTAMP_MILLIS(elonvelonntMelontadata.sourcelonTimelonstampMs) BelonTWelonelonN vars.start_timelon AND vars.elonnd_timelon
      AND MOD(ABS(farm_fingelonrprint(itelonm.twelonelontInfo.actionTwelonelontId || '')), vars.twelonelont_samplelon_ratelon) = 0
      AND MOD(ABS(farm_fingelonrprint(uselonrIdelonntifielonr.uselonrId || '')), vars.elonng_uselonr_samplelon_ratelon) = 0
       -- Apply twelonelont agelon filtelonr helonrelon
      AND timelonstamp_millis((1288834974657 +
        ((itelonm.twelonelontInfo.actionTwelonelontId & 9223372036850581504) >> 22))) >= (vars.start_timelon)
    GROUP BY 1, 2, 3
    HAVING favoritelond
  ),
  elonng_data AS (
    SelonLelonCT
      uselonr_id, twelonelont_id, author_id, imprelonssions.minTsMilli, favoritelond, imprelonsselond
    FROM imprelonssions
    LelonFT JOIN favs USING(uselonr_id, twelonelont_id, author_id)
  ),
  elonligiblelon_twelonelonts AS (
    SelonLelonCT
      twelonelont_id,
      author_id,
      COUNTIF(favoritelond) num_favs,
      COUNTIF(imprelonsselond) num_imps,
      COUNTIF(favoritelond) * 1.0 / COUNTIF(imprelonsselond) AS twelonelont_ftr,
      ANY_VALUelon(vars.min_twelonelont_favs) min_twelonelont_favs,
      ANY_VALUelon(vars.min_twelonelont_imps) min_twelonelont_imps,
      ANY_VALUelon(vars.max_twelonelont_ftr) max_twelonelont_ftr,
    FROM elonng_data, vars
    GROUP BY 1, 2
    HAVING num_favs >= min_twelonelont_favs -- this is an aggrelonssivelon filtelonr to makelon thelon workflow elonfficielonnt
      AND num_imps >= min_twelonelont_imps
      AND twelonelont_ftr <= max_twelonelont_ftr -- filtelonr to combat spam
  ),
  elonligiblelon_uselonrs AS (
    SelonLelonCT
      uselonr_id,
      CAST(LOG10(COUNTIF(imprelonsselond) + 1) AS INT64) log_n_imps,
      CAST(LOG10(COUNTIF(favoritelond) + 1) AS INT64) log_n_favs,
      ANY_VALUelon(vars.max_uselonr_log_n_imps) max_uselonr_log_n_imps,
      ANY_VALUelon(vars.max_uselonr_log_n_favs) max_uselonr_log_n_favs,
      ANY_VALUelon(vars.max_uselonr_ftr) max_uselonr_ftr,
      COUNTIF(favoritelond) * 1.0 / COUNTIF(imprelonsselond) uselonr_ftr
    from elonng_data, vars
    GROUP BY 1
    HAVING
      log_n_imps < max_uselonr_log_n_imps
      AND log_n_favs < max_uselonr_log_n_favs
      AND uselonr_ftr < max_uselonr_ftr
  ),
  elonligiblelon_elonng_data AS (
    SelonLelonCT
      uselonr_id,
      elonng_data.author_id,
      twelonelont_id,
      minTsMilli,
      favoritelond,
      imprelonsselond
    FROM elonng_data
    INNelonR JOIN elonligiblelon_twelonelonts USING(twelonelont_id)
    INNelonR JOIN elonligiblelon_uselonrs USING(uselonr_id)
  ),
  follow_graph AS (
    SelonLelonCT uselonrId, nelonighbor
    FROM `twttr-bq-cassowary-prod.uselonr.uselonr_uselonr_normalizelond_graph` uselonr_uselonr_graph, unnelonst(uselonr_uselonr_graph.nelonighbors) as nelonighbor
    WHelonRelon DATelon(_PARTITIONTIMelon) =
          (  -- Gelont latelonst partition timelon
          SelonLelonCT MAX(DATelon(_PARTITIONTIMelon)) latelonst_partition
          FROM `twttr-bq-cassowary-prod.uselonr.uselonr_uselonr_normalizelond_graph`, vars
          WHelonRelon Datelon(_PARTITIONTIMelon) BelonTWelonelonN
            DATelon_SUB(Datelon(vars.elonnd_timelon),
              INTelonRVAL 14 DAY) AND DATelon(vars.elonnd_timelon)
            )
    AND nelonighbor.isFollowelond is Truelon
  ),
  elonxtelonndelond_elonligiblelon_elonng_data AS (
      SelonLelonCT
        uselonr_id,
        twelonelont_id,
        minTsMilli,
        favoritelond,
        imprelonsselond,
        nelonighbor.nelonighborId is NULL as is_oon_elonng
      FROM elonligiblelon_elonng_data  lelonft JOIN follow_graph ON (follow_graph.uselonrId = elonligiblelon_elonng_data.uselonr_id AND follow_graph.nelonighbor.nelonighborId = elonligiblelon_elonng_data.author_id)
  ),
  -- stelonp 2: melonrgelon with iikf
  iikf AS (
  SelonLelonCT
    uselonrId AS uselonr_id,

    clustelonrIdToScorelon.kelony AS clustelonrId,
    clustelonrIdToScorelon.valuelon.favScorelon AS favScorelon,
    clustelonrIdToScorelon.valuelon.favScorelonClustelonrNormalizelondOnly AS favScorelonClustelonrNormalizelondOnly,
    clustelonrIdToScorelon.valuelon.favScorelonProducelonrNormalizelondOnly AS favScorelonProducelonrNormalizelondOnly,

    clustelonrIdToScorelon.valuelon.logFavScorelon AS logFavScorelon,
    clustelonrIdToScorelon.valuelon.logfavScorelonClustelonrNormalizelondOnly AS logfavScorelonClustelonrNormalizelondOnly, -- probably no nelonelond for clustelonr normalization anymorelon
    ROW_NUMBelonR() OVelonR (PARTITION BY uselonrId ORDelonR BY clustelonrIdToScorelon.valuelon.logFavScorelon DelonSC) AS uii_clustelonr_rank_logfavscorelon,
    ROW_NUMBelonR() OVelonR (PARTITION BY uselonrId ORDelonR BY clustelonrIdToScorelon.valuelon.logfavScorelonClustelonrNormalizelondOnly DelonSC) AS uii_clustelonr_rank_logfavscorelonclustelonrnormalizelond,
  FROM `twttr-bq-cassowary-prod.uselonr.simclustelonrs_v2_uselonr_to_intelonrelonstelond_in_20M_145K_2020`, UNNelonST(clustelonrIdToScorelons) clustelonrIdToScorelon, vars
  WHelonRelon DATelon(_PARTITIONTIMelon) =
            (-- Gelont latelonst partition timelon
            SelonLelonCT MAX(DATelon(_PARTITIONTIMelon)) latelonst_partition
            FROM `twttr-bq-cassowary-prod.uselonr.simclustelonrs_v2_uselonr_to_intelonrelonstelond_in_20M_145K_2020`
            WHelonRelon Datelon(_PARTITIONTIMelon) BelonTWelonelonN
            DATelon_SUB(Datelon(vars.elonnd_timelon),
              INTelonRVAL 14 DAY) AND DATelon(vars.elonnd_timelon)
            )
          AND MOD(ABS(farm_fingelonrprint(uselonrId || '')), vars.elonng_uselonr_samplelon_ratelon) = 0
          AND clustelonrIdToScorelon.valuelon.logFavScorelon != 0
  ),
  elonng_w_uii AS (
    SelonLelonCT
      T_IMP_FAV.uselonr_id,
      T_IMP_FAV.twelonelont_id,
      T_IMP_FAV.imprelonsselond,
      T_IMP_FAV.favoritelond,
      T_IMP_FAV.minTsMilli,
      T_IMP_FAV.is_oon_elonng,

      IIKF.clustelonrId,
      IIKF.logFavScorelon,
      IIKF.logfavScorelonClustelonrNormalizelondOnly,
      IIKF.uii_clustelonr_rank_logfavscorelon,
      IIKF.uii_clustelonr_rank_logfavscorelonclustelonrnormalizelond,
    FROM elonxtelonndelond_elonligiblelon_elonng_data T_IMP_FAV, vars
    INNelonR JOIN iikf
      ON T_IMP_FAV.uselonr_id = IIKF.uselonr_id
    WHelonRelon
        T_IMP_FAV.imprelonsselond
  ),
  -- stelonp 3: Calculatelon twelonelont elonmbelondding
  twelonelont_clustelonr_agg AS (
    SelonLelonCT
      twelonelont_id,
      clustelonrId,

      SUM(IF(imprelonsselond, logFavScorelon, 0)) delonnom_logFavScorelon,
      SUM(IF(favoritelond, logFavScorelon, 0)) nom_logFavScorelon,

      COUNTIF(imprelonsselond) n_imps,
      COUNTIF(favoritelond) n_favs,

      COUNTIF(imprelonsselond AND uii_clustelonr_rank_logfavscorelon <= 5) n_imps_at_5,
      COUNTIF(favoritelond AND uii_clustelonr_rank_logfavscorelon <= 5) n_favs_at_5,

      COUNTIF(favoritelond AND uii_clustelonr_rank_logfavscorelon <= 5 AND is_oon_elonng) n_oon_favs_at_5,
      COUNTIF(imprelonsselond AND uii_clustelonr_rank_logfavscorelon <= 5 AND is_oon_elonng) n_oon_imps_at_5,

      SUM(IF(favoritelond AND uii_clustelonr_rank_logfavscorelon <= 5, 1, 0) * POW(0.5, (currelonntTs - minTsMilli) / vars.halfLifelon)) AS deloncayelond_n_favs_at_5,
      SUM(IF(imprelonsselond AND uii_clustelonr_rank_logfavscorelon <= 5, 1, 0) * POW(0.5, (currelonntTs - minTsMilli) / vars.halfLifelon)) AS deloncayelond_n_imps_at_5,

      SUM(IF(favoritelond, logfavScorelonClustelonrNormalizelondOnly, 0) * POW(0.5, (currelonntTs - minTsMilli) / vars.halfLifelon)) AS delonc_sum_logfavScorelonClustelonrNormalizelondOnly,

      MIN(minTsMilli) minTsMilli,

    FROM elonng_w_uii, vars
    GROUP BY 1, 2
  ),
  twelonelont_clustelonr_intelonrmelondiatelon AS (
    SelonLelonCT
      twelonelont_id,
      clustelonrId,
      minTsMilli,

      n_imps,
      n_favs,

      n_favs_at_5,
      n_imps_at_5,
      n_oon_favs_at_5,
      n_oon_imps_at_5,
      deloncayelond_n_favs_at_5,
      deloncayelond_n_imps_at_5,

      delonnom_logFavScorelon,
      nom_logFavScorelon,

      delonc_sum_logfavScorelonClustelonrNormalizelondOnly,

      SAFelon_DIVIDelon(n_favs_at_5, n_imps_at_5) AS ftr_at_5,

      SAFelon_DIVIDelon(n_oon_favs_at_5,  n_oon_imps_at_5) AS ftr_oon_at_5,

      row_numbelonr() OVelonR (PARTITION BY twelonelont_id ORDelonR BY nom_logFavScorelon DelonSC) clustelonr_nom_logFavScorelon_ranking,
      row_numbelonr() OVelonR (PARTITION BY twelonelont_id ORDelonR BY delonc_sum_logfavScorelonClustelonrNormalizelondOnly DelonSC) clustelonr_deloncSumLogFavClustelonrNormalizelond_ranking,
    FROM twelonelont_clustelonr_agg
  ),
  twelonelont_elon AS (
    SelonLelonCT
      twelonelont_id,

      MIN(minTsMilli) first_selonrvelon_millis,
      DATelon(TIMelonSTAMP_MILLIS(MIN(minTsMilli))) datelon_first_selonrvelon,

      ARRAY_AGG(STRUCT(
          clustelonrId,
          -- thelon division by MAX_elonXPONelonNT is to avoid ovelonrflow opelonration
          ftr_at_5 * (2 / (1+elonXP(-1* (deloncayelond_n_favs_at_5/1000))) - 1) * IF(clustelonr_deloncSumLogFavClustelonrNormalizelond_ranking > MAX_elonXPONelonNT, 0, 1.0/(POW(1.1, clustelonr_deloncSumLogFavClustelonrNormalizelond_ranking-1))) AS ftrat5_deloncayelond_pop_bias_1000_rank_deloncay_1_1
      ) ORDelonR BY ftr_at_5 * (2 / (1+elonXP(-1* (deloncayelond_n_favs_at_5/1000))) - 1) * IF(clustelonr_deloncSumLogFavClustelonrNormalizelond_ranking > MAX_elonXPONelonNT, 0, 1.0/(POW(1.1, clustelonr_deloncSumLogFavClustelonrNormalizelond_ranking-1))) DelonSC LIMIT {TWelonelonT_elonMBelonDDING_LelonNGTH}) ftrat5_deloncayelond_pop_bias_1000_rank_deloncay_1_1_elonmbelondding,

      ARRAY_AGG(STRUCT(
          clustelonrId,
          -- thelon division by MAX_elonXPONelonNT is to avoid ovelonrflow opelonration
          ftr_at_5 * (2 / (1+elonXP(-1* (deloncayelond_n_favs_at_5/10000))) - 1) * IF(clustelonr_deloncSumLogFavClustelonrNormalizelond_ranking > MAX_elonXPONelonNT, 0, 1.0/(POW(1.1, clustelonr_deloncSumLogFavClustelonrNormalizelond_ranking-1))) AS ftrat5_deloncayelond_pop_bias_10000_rank_deloncay_1_1
      ) ORDelonR BY ftr_at_5 * (2 / (1+elonXP(-1* (deloncayelond_n_favs_at_5/1000))) - 1) * IF(clustelonr_deloncSumLogFavClustelonrNormalizelond_ranking > MAX_elonXPONelonNT, 0, 1.0/(POW(1.1, clustelonr_deloncSumLogFavClustelonrNormalizelond_ranking-1))) DelonSC LIMIT {TWelonelonT_elonMBelonDDING_LelonNGTH}) ftrat5_deloncayelond_pop_bias_10000_rank_deloncay_1_1_elonmbelondding,

      ARRAY_AGG(STRUCT(
          clustelonrId,
          -- thelon division by MAX_elonXPONelonNT is to avoid ovelonrflow opelonration
          ftr_oon_at_5 * (2 / (1+elonXP(-1* (deloncayelond_n_favs_at_5/1000))) - 1) * IF(clustelonr_nom_logFavScorelon_ranking > MAX_elonXPONelonNT, 0, 1.0/(POW(1.1, clustelonr_nom_logFavScorelon_ranking-1))) AS oon_ftrat5_deloncayelond_pop_bias_1000_rank_deloncay
      ) ORDelonR BY ftr_oon_at_5 * (2 / (1+elonXP(-1* (deloncayelond_n_favs_at_5/1000))) - 1) * IF(clustelonr_nom_logFavScorelon_ranking > MAX_elonXPONelonNT, 0, 1.0/(POW(1.1, clustelonr_nom_logFavScorelon_ranking-1))) DelonSC LIMIT {TWelonelonT_elonMBelonDDING_LelonNGTH}) oon_ftrat5_deloncayelond_pop_bias_1000_rank_deloncay_elonmbelondding,

      ARRAY_AGG(STRUCT(
          clustelonrId,
          delonc_sum_logfavScorelonClustelonrNormalizelondOnly
          ) ORDelonR BY delonc_sum_logfavScorelonClustelonrNormalizelondOnly DelonSC LIMIT {TWelonelonT_elonMBelonDDING_LelonNGTH}) delonc_sum_logfavScorelonClustelonrNormalizelondOnly_elonmbelondding,

    FROM twelonelont_clustelonr_intelonrmelondiatelon, vars
    GROUP BY 1
  ),
  twelonelont_elon_unnelonst AS (
    SelonLelonCT
        twelonelont_id AS twelonelontId,
        clustelonrToScorelons.clustelonrId AS clustelonrId,
        clustelonrToScorelons.{SCORelon_KelonY} twelonelontScorelon
    FROM twelonelont_elon, UNNelonST({SCORelon_COLUMN}) clustelonrToScorelons
    WHelonRelon clustelonrToScorelons.{SCORelon_KelonY} IS NOT NULL
      AND clustelonrToScorelons.{SCORelon_KelonY} > 0
  )
  SelonLelonCT
    twelonelontId,
    clustelonrId,
    twelonelontScorelon
  FROM twelonelont_elon_unnelonst
