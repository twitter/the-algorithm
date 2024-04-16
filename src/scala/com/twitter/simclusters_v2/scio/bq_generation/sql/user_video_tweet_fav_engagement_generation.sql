W TH
  vars AS (
    SELECT
      T MESTAMP("{START_T ME}") AS start_date,
      T MESTAMP("{END_T ME}") AS end_date,
  ),

  -- Get raw user-t et  nteract on events from UUA (  w ll use fav engage nts  re)
  raw_engage nts AS (
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

  -- Get v deo t et  ds
  v deo_t et_ ds AS (
      W TH vars AS (
        SELECT
          T MESTAMP("{START_T ME}") AS start_date,
          T MESTAMP("{END_T ME}") AS end_date
      ),

      -- Get raw user-t et  nteract on events from UUA
      v deo_v ew_engage nts AS (
        SELECT  em.t et nfo.act onT et d AS t et d
        FROM `twttr-bql-un f ed-prod.un f ed_user_act ons_engage nts.stream ng_un f ed_user_act ons_engage nts`, vars
        WHERE (DATE(dateH ) >= DATE(vars.start_date) AND DATE(dateH ) <= DATE(vars.end_date))
          AND event tadata.s ceT  stampMs >= UN X_M LL S(start_date)
          AND event tadata.s ceT  stampMs <= UN X_M LL S(end_date)
          AND (act onType  N ("Cl entT etV deoPlayback50")
                OR act onType  N ("Cl entT etV deoPlayback95"))
      )

      SELECT D ST NCT(t et d)
      FROM v deo_v ew_engage nts
  ),

  -- Jo n v deo t et  ds
  v deo_t ets_engage nts AS (
      SELECT raw_engage nts.*
      FROM raw_engage nts JO N v deo_t et_ ds US NG(t et d)
  ),

  -- Group by user d and t et d
  user_t et_engage nt_pa rs AS (
    SELECT user d, t et d, ARRAY_AGG(STRUCT(doOrUndo, tsM ll s) ORDER BY tsM ll s DESC L M T 1) AS deta ls, COUNT(*) AS cnt
    FROM v deo_t ets_engage nts
    GROUP BY user d, t et d
  )

-- Remove undo events
SELECT user d, t et d, CAST(dt.tsM ll s  AS FLOAT64) AS tsM ll s
FROM user_t et_engage nt_pa rs, vars
CROSS JO N UNNEST(deta ls) AS dt
WHERE dt.doOrUndo = 1
