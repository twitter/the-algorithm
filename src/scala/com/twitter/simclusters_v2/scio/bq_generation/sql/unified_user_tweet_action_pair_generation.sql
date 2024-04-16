W TH
  vars AS (
    SELECT
      T MESTAMP("{START_T ME}") AS start_date,
      T MESTAMP("{END_T ME}") AS end_date,
      T MESTAMP("{NO_OLDER_TWEETS_THAN_DATE}") AS no_older_t ets_than_date
  ),

  -- Get raw user-t et  nteract on events from UUA
   nteract ons_un oned AS (
    SELECT
      user dent f er.user d AS user d,
      event tadata.s ceT  stampMs AS tsM ll s,
      CASE
          WHEN act onType  N ({CONTR BUT NG_ACT ON_TYPES_STR}) THEN {CONTR BUT NG_ACT ON_TWEET_ D_COLUMN}
          WHEN act onType  N ({UNDO_ACT ON_TYPES_STR}) THEN {UNDO_ACT ON_TWEET_ D_COLUMN}
      END AS t et d,
      CASE
        WHEN act onType  N ({CONTR BUT NG_ACT ON_TYPES_STR}) THEN 1
        WHEN act onType  N ({UNDO_ACT ON_TYPES_STR}) THEN -1
      END AS doOrUndo
    FROM `twttr-bql-un f ed-prod.un f ed_user_act ons_engage nts.stream ng_un f ed_user_act ons_engage nts`, vars
    WHERE (DATE(dateH ) >= DATE(vars.start_date) AND DATE(dateH ) <= DATE(vars.end_date))
      AND event tadata.s ceT  stampMs >= UN X_M LL S(vars.start_date) 
      AND event tadata.s ceT  stampMs <= UN X_M LL S(vars.end_date)
      AND (act onType  N ({CONTR BUT NG_ACT ON_TYPES_STR})
            OR act onType  N ({UNDO_ACT ON_TYPES_STR}))
  ),

  -- Group by user d and t et d
  user_t et_ nteract on_pa rs AS (
    SELECT user d, t et d, ARRAY_AGG(STRUCT(doOrUndo, tsM ll s) ORDER BY tsM ll s DESC L M T 1) AS deta ls, COUNT(*) AS cnt
    FROM  nteract ons_un oned
    GROUP BY user d, t et d
  )

-- Remove undo events
-- Apply age f lter  n t  step
SELECT user d, t et d, CAST(dt.tsM ll s  AS FLOAT64) AS tsM ll s
FROM user_t et_ nteract on_pa rs, vars
CROSS JO N UNNEST(deta ls) AS dt
WHERE cnt < 3
  AND dt.doOrUndo = 1
  AND t  stamp_m ll s((1288834974657 +
            ((t et d  & 9223372036850581504) >> 22))) >= vars.no_older_t ets_than_date
