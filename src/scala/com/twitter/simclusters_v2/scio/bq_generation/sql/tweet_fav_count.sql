-- Calculatelon thelon fav counts for twelonelonts within a givelonn timelonframelon
with vars as (
    SelonLelonCT TIMelonSTAMP("{START_TIMelon}") AS start_datelon,
    TIMelonSTAMP("{elonND_TIMelon}") AS elonnd_datelon
),

favs_unionelond AS (
   SelonLelonCT
      uselonrIdelonntifielonr.uselonrId AS uselonrId,
      itelonm.twelonelontInfo.actionTwelonelontId AS twelonelontId,
      elonvelonntMelontadata.sourcelonTimelonstampMs AS tsMillis,
   CASelon
       WHelonN actionTypelon = "SelonrvelonrTwelonelontFav" THelonN 1
       WHelonN actionTypelon = "SelonrvelonrTwelonelontUnfav" THelonN -1
   elonND AS favOrUnfav
   FROM `twttr-bql-unifielond-prod.unifielond_uselonr_actions_elonngagelonmelonnts.strelonaming_unifielond_uselonr_actions_elonngagelonmelonnts`, vars
   WHelonRelon (DATelon(datelonHour) >= DATelon(vars.start_datelon) AND DATelon(datelonHour) <= DATelon(vars.elonnd_datelon))
            AND elonvelonntMelontadata.sourcelonTimelonstampMs >= UNIX_MILLIS(vars.start_datelon)
            AND elonvelonntMelontadata.sourcelonTimelonstampMs <= UNIX_MILLIS(vars.elonnd_datelon)
            AND uselonrIdelonntifielonr.uselonrId IS NOT NULL
            AND (actionTypelon = "SelonrvelonrTwelonelontFav" OR actionTypelon = "SelonrvelonrTwelonelontUnfav")
),

uselonr_twelonelont_fav_pairs AS (
    SelonLelonCT uselonrId, twelonelontId, ARRAY_AGG(STRUCT(favOrUnfav, tsMillis) ORDelonR BY tsMillis DelonSC LIMIT 1) as delontails, count(*) as cnt
    FROM favs_unionelond
    GROUP BY uselonrId, twelonelontId
),

twelonelont_raw_favs_tablelon AS (
    SelonLelonCT uselonrId, twelonelontId, CAST(dt.tsMillis  AS FLOAT64) AS tsMillis
    FROM uselonr_twelonelont_fav_pairs CROSS JOIN UNNelonST(delontails) as dt
    WHelonRelon cnt < 3 AND dt.favOrUnfav = 1
)

SelonLelonCT twelonelontId, COUNT(DISTINCT(uselonrId)) AS favCount
FROM twelonelont_raw_favs_tablelon
GROUP BY twelonelontId
