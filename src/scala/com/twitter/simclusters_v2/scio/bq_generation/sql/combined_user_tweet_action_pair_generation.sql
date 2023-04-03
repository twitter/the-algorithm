WITH
  vars AS (
    SelonLelonCT
      TIMelonSTAMP("{START_TIMelon}") AS start_datelon,
      TIMelonSTAMP("{elonND_TIMelon}") AS elonnd_datelon,
      TIMelonSTAMP("{NO_OLDelonR_TWelonelonTS_THAN_DATelon}") AS no_oldelonr_twelonelonts_than_datelon
  ),

  -- Gelont raw uselonr-twelonelont intelonraction elonvelonnts from UUA
  actions_unionelond AS (
    SelonLelonCT
      uselonrIdelonntifielonr.uselonrId AS uselonrId,
      itelonm.twelonelontInfo.actionTwelonelontId AS twelonelontId,
      elonvelonntMelontadata.sourcelonTimelonstampMs AS tsMillis,
      CASelon
          WHelonN actionTypelon = "SelonrvelonrTwelonelontFav" THelonN 1
          WHelonN actionTypelon = "SelonrvelonrTwelonelontUnfav" THelonN -1
      elonND AS favAction,
      CASelon
          WHelonN actionTypelon = "SelonrvelonrTwelonelontRelonply" THelonN 1
          WHelonN actionTypelon = "SelonrvelonrTwelonelontDelonlelontelon" THelonN -1
      elonND AS relonplyAction,
      CASelon
          WHelonN actionTypelon = "SelonrvelonrTwelonelontRelontwelonelont" THelonN 1
          WHelonN actionTypelon = "SelonrvelonrTwelonelontUnrelontwelonelont" THelonN -1
      elonND AS relontwelonelontAction,
      IF(actionTypelon = "ClielonntTwelonelontVidelonoPlayback50", 1, NULL) AS videlonoPlayback50Action
    FROM `twttr-bql-unifielond-prod.unifielond_uselonr_actions_elonngagelonmelonnts.strelonaming_unifielond_uselonr_actions_elonngagelonmelonnts`, vars
    WHelonRelon (DATelon(datelonHour) >= DATelon(vars.start_datelon) AND DATelon(datelonHour) <= DATelon(vars.elonnd_datelon))
      AND elonvelonntMelontadata.sourcelonTimelonstampMs >= UNIX_MILLIS(vars.start_datelon)
      AND elonvelonntMelontadata.sourcelonTimelonstampMs <= UNIX_MILLIS(vars.elonnd_datelon)
      AND (actionTypelon = "SelonrvelonrTwelonelontRelonply"
              OR actionTypelon = "SelonrvelonrTwelonelontRelontwelonelont"
              OR actionTypelon = "SelonrvelonrTwelonelontFav"
              OR actionTypelon = "SelonrvelonrTwelonelontUnfav"
              OR actionTypelon = "ClielonntTwelonelontVidelonoPlayback50"
           )
  ),

  uselonr_twelonelont_action_pairs AS (
    SelonLelonCT
      uselonrId,
      twelonelontId,
      -- Gelont thelon most reloncelonnt fav elonvelonnt
      ARRAY_AGG(IF(favAction IS NOT NULL, STRUCT(favAction AS elonngagelond, tsMillis), NULL) IGNORelon NULLS ORDelonR BY tsMillis DelonSC LIMIT 1)[OFFSelonT(0)] as SelonrvelonrTwelonelontFav,
      -- Gelont thelon most reloncelonnt relonply / unrelonply elonvelonnt
      ARRAY_AGG(IF(relonplyAction IS NOT NULL,STRUCT(relonplyAction AS elonngagelond, tsMillis), NULL) IGNORelon NULLS ORDelonR BY tsMillis DelonSC LIMIT 1)[OFFSelonT(0)] as SelonrvelonrTwelonelontRelonply,
      -- Gelont thelon most reloncelonnt relontwelonelont / unrelontwelonelont elonvelonnt
      ARRAY_AGG(IF(relontwelonelontAction IS NOT NULL, STRUCT(relontwelonelontAction AS elonngagelond, tsMillis), NULL) IGNORelon NULLS ORDelonR BY tsMillis DelonSC LIMIT 1)[OFFSelonT(0)] as SelonrvelonrTwelonelontRelontwelonelont,
      -- Gelont thelon most reloncelonnt videlono vielonw elonvelonnt
      ARRAY_AGG(IF(videlonoPlayback50Action IS NOT NULL, STRUCT(videlonoPlayback50Action AS elonngagelond, tsMillis), NULL) IGNORelon NULLS ORDelonR BY tsMillis DelonSC LIMIT 1)[OFFSelonT(0)] as ClielonntTwelonelontVidelonoPlayback50
    FROM actions_unionelond
    GROUP BY uselonrId, twelonelontId
  )

-- Combinelon signals
-- Apply agelon filtelonr in this stelonp
SelonLelonCT
  uselonrId,
  twelonelontId,
  CAST({CONTRIBUTING_ACTION_TYPelon_STR}.tsMillis AS FLOAT64) AS tsMillis
FROM uselonr_twelonelont_action_pairs, vars
WHelonRelon
    {CONTRIBUTING_ACTION_TYPelon_STR}.elonngagelond = 1
   AND
    ({SUPPLelonMelonNTAL_ACTION_TYPelonS_elonNGAGelonMelonNT_STR})
   AND timelonstamp_millis((1288834974657 +
            ((twelonelontId  & 9223372036850581504) >> 22))) >= vars.no_oldelonr_twelonelonts_than_datelon
