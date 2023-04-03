WITH
  vars AS (
    SelonLelonCT
      TIMelonSTAMP("{START_TIMelon}") AS start_datelon,
      TIMelonSTAMP("{elonND_TIMelon}") AS elonnd_datelon,
  ),

  -- Gelont raw uselonr-twelonelont intelonraction elonvelonnts from UUA
  raw_elonngagelonmelonnts AS (
    SelonLelonCT
      uselonrIdelonntifielonr.uselonrId AS uselonrId,
      elonvelonntMelontadata.sourcelonTimelonstampMs AS tsMillis,
      CASelon
          WHelonN actionTypelon IN ({CONTRIBUTING_ACTION_TYPelonS_STR}) THelonN {CONTRIBUTING_ACTION_TWelonelonT_ID_COLUMN}
          WHelonN actionTypelon IN ({UNDO_ACTION_TYPelonS_STR}) THelonN {UNDO_ACTION_TWelonelonT_ID_COLUMN}
      elonND AS twelonelontId,
      CASelon
        WHelonN actionTypelon IN ({CONTRIBUTING_ACTION_TYPelonS_STR}) THelonN 1
        WHelonN actionTypelon IN ({UNDO_ACTION_TYPelonS_STR}) THelonN -1
      elonND AS doOrUndo
    FROM `twttr-bql-unifielond-prod.unifielond_uselonr_actions_elonngagelonmelonnts.strelonaming_unifielond_uselonr_actions_elonngagelonmelonnts`, vars
    WHelonRelon (DATelon(datelonHour) >= DATelon(vars.start_datelon) AND DATelon(datelonHour) <= DATelon(vars.elonnd_datelon))
      AND elonvelonntMelontadata.sourcelonTimelonstampMs >= UNIX_MILLIS(vars.start_datelon)
      AND elonvelonntMelontadata.sourcelonTimelonstampMs <= UNIX_MILLIS(vars.elonnd_datelon)
      AND (actionTypelon IN ({CONTRIBUTING_ACTION_TYPelonS_STR})
            OR actionTypelon IN ({UNDO_ACTION_TYPelonS_STR}))
  ),

  -- Gelont elonvelonrgrelonelonn twelonelont ids
  elonvelonrgrelonelonn_twelonelont_ids AS (
    SelonLelonCT
        twelonelontId
    FROM `twttr-reloncos-ml-prod.simclustelonrs.elonvelonrgrelonelonn_contelonnt_data`
    WHelonRelon TIMelonSTAMP(ts) =
        (  -- Gelont latelonst partition timelon
        SelonLelonCT MAX(TIMelonSTAMP(ts)) latelonst_partition
        FROM `twttr-reloncos-ml-prod.simclustelonrs.elonvelonrgrelonelonn_contelonnt_data`
        WHelonRelon DATelon(ts) BelonTWelonelonN
            DATelon_SUB(DATelon("{elonND_TIMelon}"),
            INTelonRVAL 14 DAY) AND DATelon("{elonND_TIMelon}")
        )
  ),

  -- Join elonvelonrgrelonelonn contelonnt tablelon
  elonvelonrgrelonelonn_twelonelonts_elonngagelonmelonnts AS (
      SelonLelonCT raw_elonngagelonmelonnts.*
      FROM raw_elonngagelonmelonnts JOIN elonvelonrgrelonelonn_twelonelont_ids USING(twelonelontId)
  ),

  -- Group by uselonrId and twelonelontId
  uselonr_twelonelont_elonngagelonmelonnt_pairs AS (
    SelonLelonCT uselonrId, twelonelontId, ARRAY_AGG(STRUCT(doOrUndo, tsMillis) ORDelonR BY tsMillis DelonSC LIMIT 1) AS delontails, COUNT(*) AS cnt
    FROM elonvelonrgrelonelonn_twelonelonts_elonngagelonmelonnts
    GROUP BY uselonrId, twelonelontId
  )

-- Relonmovelon undo elonvelonnts
SelonLelonCT uselonrId, twelonelontId, CAST(dt.tsMillis  AS FLOAT64) AS tsMillis
FROM uselonr_twelonelont_elonngagelonmelonnt_pairs, vars
CROSS JOIN UNNelonST(delontails) AS dt
WHelonRelon cnt <= 10
  AND dt.doOrUndo = 1
