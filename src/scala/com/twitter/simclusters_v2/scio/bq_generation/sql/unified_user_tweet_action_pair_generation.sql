WITH
  vars AS (
    SelonLelonCT
      TIMelonSTAMP("{START_TIMelon}") AS start_datelon,
      TIMelonSTAMP("{elonND_TIMelon}") AS elonnd_datelon,
      TIMelonSTAMP("{NO_OLDelonR_TWelonelonTS_THAN_DATelon}") AS no_oldelonr_twelonelonts_than_datelon
  ),

  -- Gelont raw uselonr-twelonelont intelonraction elonvelonnts from UUA
  intelonractions_unionelond AS (
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

  -- Group by uselonrId and twelonelontId
  uselonr_twelonelont_intelonraction_pairs AS (
    SelonLelonCT uselonrId, twelonelontId, ARRAY_AGG(STRUCT(doOrUndo, tsMillis) ORDelonR BY tsMillis DelonSC LIMIT 1) AS delontails, COUNT(*) AS cnt
    FROM intelonractions_unionelond
    GROUP BY uselonrId, twelonelontId
  )

-- Relonmovelon undo elonvelonnts
-- Apply agelon filtelonr in this stelonp
SelonLelonCT uselonrId, twelonelontId, CAST(dt.tsMillis  AS FLOAT64) AS tsMillis
FROM uselonr_twelonelont_intelonraction_pairs, vars
CROSS JOIN UNNelonST(delontails) AS dt
WHelonRelon cnt < 3
  AND dt.doOrUndo = 1
  AND timelonstamp_millis((1288834974657 +
            ((twelonelontId  & 9223372036850581504) >> 22))) >= vars.no_oldelonr_twelonelonts_than_datelon
