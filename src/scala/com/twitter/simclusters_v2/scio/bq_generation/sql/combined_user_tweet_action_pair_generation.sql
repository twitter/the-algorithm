W TH
  vars AS (
    SELECT
      T MESTAMP("{START_T ME}") AS start_date,
      T MESTAMP("{END_T ME}") AS end_date,
      T MESTAMP("{NO_OLDER_TWEETS_THAN_DATE}") AS no_older_t ets_than_date
  ),

  -- Get raw user-t et  nteract on events from UUA
  act ons_un oned AS (
    SELECT
      user dent f er.user d AS user d,
       em.t et nfo.act onT et d AS t et d,
      event tadata.s ceT  stampMs AS tsM ll s,
      CASE
          WHEN act onType = "ServerT etFav" THEN 1
          WHEN act onType = "ServerT etUnfav" THEN -1
      END AS favAct on,
      CASE
          WHEN act onType = "ServerT etReply" THEN 1
          WHEN act onType = "ServerT etDelete" THEN -1
      END AS replyAct on,
      CASE
          WHEN act onType = "ServerT etRet et" THEN 1
          WHEN act onType = "ServerT etUnret et" THEN -1
      END AS ret etAct on,
       F(act onType = "Cl entT etV deoPlayback50", 1, NULL) AS v deoPlayback50Act on
    FROM `twttr-bql-un f ed-prod.un f ed_user_act ons_engage nts.stream ng_un f ed_user_act ons_engage nts`, vars
    WHERE (DATE(dateH ) >= DATE(vars.start_date) AND DATE(dateH ) <= DATE(vars.end_date))
      AND event tadata.s ceT  stampMs >= UN X_M LL S(vars.start_date) 
      AND event tadata.s ceT  stampMs <= UN X_M LL S(vars.end_date)
      AND (act onType = "ServerT etReply"
              OR act onType = "ServerT etRet et"
              OR act onType = "ServerT etFav"
              OR act onType = "ServerT etUnfav"
              OR act onType = "Cl entT etV deoPlayback50"
           )
  ),

  user_t et_act on_pa rs AS (
    SELECT
      user d,
      t et d,
      -- Get t  most recent fav event
      ARRAY_AGG( F(favAct on  S NOT NULL, STRUCT(favAct on AS engaged, tsM ll s), NULL)  GNORE NULLS ORDER BY tsM ll s DESC L M T 1)[OFFSET(0)] as ServerT etFav,
      -- Get t  most recent reply / unreply event
      ARRAY_AGG( F(replyAct on  S NOT NULL,STRUCT(replyAct on AS engaged, tsM ll s), NULL)  GNORE NULLS ORDER BY tsM ll s DESC L M T 1)[OFFSET(0)] as ServerT etReply,
      -- Get t  most recent ret et / unret et event
      ARRAY_AGG( F(ret etAct on  S NOT NULL, STRUCT(ret etAct on AS engaged, tsM ll s), NULL)  GNORE NULLS ORDER BY tsM ll s DESC L M T 1)[OFFSET(0)] as ServerT etRet et,
      -- Get t  most recent v deo v ew event
      ARRAY_AGG( F(v deoPlayback50Act on  S NOT NULL, STRUCT(v deoPlayback50Act on AS engaged, tsM ll s), NULL)  GNORE NULLS ORDER BY tsM ll s DESC L M T 1)[OFFSET(0)] as Cl entT etV deoPlayback50
    FROM act ons_un oned
    GROUP BY user d, t et d
  )

-- Comb ne s gnals
-- Apply age f lter  n t  step
SELECT
  user d,
  t et d,
  CAST({CONTR BUT NG_ACT ON_TYPE_STR}.tsM ll s AS FLOAT64) AS tsM ll s
FROM user_t et_act on_pa rs, vars
WHERE
    {CONTR BUT NG_ACT ON_TYPE_STR}.engaged = 1
   AND
    ({SUPPLEMENTAL_ACT ON_TYPES_ENGAGEMENT_STR})
   AND t  stamp_m ll s((1288834974657 +
            ((t et d  & 9223372036850581504) >> 22))) >= vars.no_older_t ets_than_date
