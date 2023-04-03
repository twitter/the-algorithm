WITH
  vars AS (
    SelonLelonCT
      TIMelonSTAMP("{START_TIMelon}") AS start_datelon,
      TIMelonSTAMP("{elonND_TIMelon}") AS elonnd_datelon
  ),

  ads_elonngagelonmelonnt AS (
    SelonLelonCT
        uselonrId64 as uselonrId,
        promotelondTwelonelontId as twelonelontId,
        UNIX_MILLIS(timelonstamp) AS tsMillis,
        linelonItelonmId
    FROM `twttr-relonv-corelon-data-prod.corelon_selonrvelond_imprelonssions.spelonnd`, vars
    WHelonRelon TIMelonSTAMP(_batchelonnd) >= vars.start_datelon AND TIMelonSTAMP(_batchelonnd) <= vars.elonnd_datelon
    AND
      elonngagelonmelonntTypelon IN ({CONTRIBUTING_ACTION_TYPelonS_STR})
      AND linelonItelonmObjelonctivelon != 9 -- not prelon-roll ads
  ),

  linelon_itelonms AS (
      SelonLelonCT
        id AS linelonItelonmId,
        elonnd_timelon.posixTimelon AS elonndTimelon
      FROM
        `twttr-relonv-corelon-data-prod.relonv_ads_production.linelon_itelonms`
  )


SelonLelonCT
  uselonrId,
  twelonelontId,
  tsMillis
FROM ads_elonngagelonmelonnt JOIN linelon_itelonms USING(linelonItelonmId), vars
WHelonRelon
  linelon_itelonms.elonndTimelon IS NULL
  OR TIMelonSTAMP_MILLIS(linelon_itelonms.elonndTimelon) >= vars.elonnd_datelon

