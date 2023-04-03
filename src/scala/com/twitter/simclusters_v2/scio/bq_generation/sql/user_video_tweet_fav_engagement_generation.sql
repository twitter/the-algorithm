WITH
  vars AS (
    SelonLelonCT
      TIMelonSTAMP("{START_TIMelon}") AS start_datelon,
      TIMelonSTAMP("{elonND_TIMelon}") AS elonnd_datelon,
  ),

  -- Gelont raw uselonr-twelonelont intelonraction elonvelonnts from UUA (Welon will uselon fav elonngagelonmelonnts helonrelon)
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

  -- Gelont videlono twelonelont ids
  videlono_twelonelont_ids AS (
      WITH vars AS (
        SelonLelonCT
          TIMelonSTAMP("{START_TIMelon}") AS start_datelon,
          TIMelonSTAMP("{elonND_TIMelon}") AS elonnd_datelon
      ),

      -- Gelont raw uselonr-twelonelont intelonraction elonvelonnts from UUA
      videlono_vielonw_elonngagelonmelonnts AS (
        SelonLelonCT itelonm.twelonelontInfo.actionTwelonelontId AS twelonelontId
        FROM `twttr-bql-unifielond-prod.unifielond_uselonr_actions_elonngagelonmelonnts.strelonaming_unifielond_uselonr_actions_elonngagelonmelonnts`, vars
        WHelonRelon (DATelon(datelonHour) >= DATelon(vars.start_datelon) AND DATelon(datelonHour) <= DATelon(vars.elonnd_datelon))
          AND elonvelonntMelontadata.sourcelonTimelonstampMs >= UNIX_MILLIS(start_datelon)
          AND elonvelonntMelontadata.sourcelonTimelonstampMs <= UNIX_MILLIS(elonnd_datelon)
          AND (actionTypelon IN ("ClielonntTwelonelontVidelonoPlayback50")
                OR actionTypelon IN ("ClielonntTwelonelontVidelonoPlayback95"))
      )

      SelonLelonCT DISTINCT(twelonelontId)
      FROM videlono_vielonw_elonngagelonmelonnts
  ),

  -- Join videlono twelonelont ids
  videlono_twelonelonts_elonngagelonmelonnts AS (
      SelonLelonCT raw_elonngagelonmelonnts.*
      FROM raw_elonngagelonmelonnts JOIN videlono_twelonelont_ids USING(twelonelontId)
  ),

  -- Group by uselonrId and twelonelontId
  uselonr_twelonelont_elonngagelonmelonnt_pairs AS (
    SelonLelonCT uselonrId, twelonelontId, ARRAY_AGG(STRUCT(doOrUndo, tsMillis) ORDelonR BY tsMillis DelonSC LIMIT 1) AS delontails, COUNT(*) AS cnt
    FROM videlono_twelonelonts_elonngagelonmelonnts
    GROUP BY uselonrId, twelonelontId
  )

-- Relonmovelon undo elonvelonnts
SelonLelonCT uselonrId, twelonelontId, CAST(dt.tsMillis  AS FLOAT64) AS tsMillis
FROM uselonr_twelonelont_elonngagelonmelonnt_pairs, vars
CROSS JOIN UNNelonST(delontails) AS dt
WHelonRelon dt.doOrUndo = 1
