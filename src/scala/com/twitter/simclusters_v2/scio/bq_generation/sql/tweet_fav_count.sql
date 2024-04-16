-- Calculate t  fav counts for t ets w h n a g ven t  fra 
w h vars as (
    SELECT T MESTAMP("{START_T ME}") AS start_date,
    T MESTAMP("{END_T ME}") AS end_date
),

favs_un oned AS (
   SELECT
      user dent f er.user d AS user d,
       em.t et nfo.act onT et d AS t et d,
      event tadata.s ceT  stampMs AS tsM ll s,
   CASE
       WHEN act onType = "ServerT etFav" THEN 1
       WHEN act onType = "ServerT etUnfav" THEN -1
   END AS favOrUnfav
   FROM `twttr-bql-un f ed-prod.un f ed_user_act ons_engage nts.stream ng_un f ed_user_act ons_engage nts`, vars
   WHERE (DATE(dateH ) >= DATE(vars.start_date) AND DATE(dateH ) <= DATE(vars.end_date))
            AND event tadata.s ceT  stampMs >= UN X_M LL S(vars.start_date) 
            AND event tadata.s ceT  stampMs <= UN X_M LL S(vars.end_date)
            AND user dent f er.user d  S NOT NULL
            AND (act onType = "ServerT etFav" OR act onType = "ServerT etUnfav")
),

user_t et_fav_pa rs AS (
    SELECT user d, t et d, ARRAY_AGG(STRUCT(favOrUnfav, tsM ll s) ORDER BY tsM ll s DESC L M T 1) as deta ls, count(*) as cnt
    FROM favs_un oned
    GROUP BY user d, t et d
),

t et_raw_favs_table AS (
    SELECT user d, t et d, CAST(dt.tsM ll s  AS FLOAT64) AS tsM ll s
    FROM user_t et_fav_pa rs CROSS JO N UNNEST(deta ls) as dt
    WHERE cnt < 3 AND dt.favOrUnfav = 1
)

SELECT t et d, COUNT(D ST NCT(user d)) AS favCount
FROM t et_raw_favs_table
GROUP BY t et d
