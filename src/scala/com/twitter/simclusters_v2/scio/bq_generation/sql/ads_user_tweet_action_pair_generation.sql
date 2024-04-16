W TH
  vars AS (
    SELECT
      T MESTAMP("{START_T ME}") AS start_date,
      T MESTAMP("{END_T ME}") AS end_date
  ),

  ads_engage nt AS (
    SELECT
        user d64 as user d,
        promotedT et d as t et d,
        UN X_M LL S(t  stamp) AS tsM ll s,
        l ne em d
    FROM `twttr-rev-core-data-prod.core_served_ mpress ons.spend`, vars
    WHERE T MESTAMP(_batchEnd) >= vars.start_date AND T MESTAMP(_batchEnd) <= vars.end_date
    AND
      engage ntType  N ({CONTR BUT NG_ACT ON_TYPES_STR})
      AND l ne emObject ve != 9 -- not pre-roll ads
  ),

  l ne_ ems AS (
      SELECT
         d AS l ne em d,
        end_t  .pos xT   AS endT  
      FROM
        `twttr-rev-core-data-prod.rev_ads_product on.l ne_ ems`
  )


SELECT
  user d,
  t et d,
  tsM ll s
FROM ads_engage nt JO N l ne_ ems US NG(l ne em d), vars
WHERE
  l ne_ ems.endT    S NULL
  OR T MESTAMP_M LL S(l ne_ ems.endT  ) >= vars.end_date

