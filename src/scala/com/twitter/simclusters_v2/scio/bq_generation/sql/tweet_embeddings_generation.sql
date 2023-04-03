with vars as (
    SelonLelonCT
    UNIX_MILLIS("{QUelonRY_DATelon}") AS currelonntTs,
    TIMelonSTAMP("{START_TIMelon}") AS startTimelon,
    TIMelonSTAMP("{elonND_TIMelon}") AS elonndTimelon,
    {MIN_SCORelon_THRelonSHOLD} AS twelonelontelonmbelonddingsMinClustelonrScorelon,
    {HALF_LIFelon} AS halfLifelon,
    TIMelonSTAMP("{NO_OLDelonR_TWelonelonTS_THAN_DATelon}") AS noOldelonrTwelonelontsThanDatelon
),

-- Gelont raw fav elonvelonnts
raw_favs AS (
    SelonLelonCT elonvelonnt.favoritelon.uselonr_id AS uselonrId, elonvelonnt.favoritelon.twelonelont_id AS twelonelontId, elonvelonnt.favoritelon.elonvelonnt_timelon_ms AS tsMillis, 1 AS favOrUnfav
    FROM `twttr-bql-timelonlinelon-prod.timelonlinelon_selonrvicelon_favoritelons.timelonlinelon_selonrvicelon_favoritelons`, vars
    WHelonRelon (DATelon(_PARTITIONTIMelon) = DATelon(vars.startTimelon) OR DATelon(_PARTITIONTIMelon) = DATelon(vars.elonndTimelon)) AND
        TIMelonSTAMP_MILLIS(elonvelonnt.favoritelon.elonvelonnt_timelon_ms) >= vars.startTimelon
        AND TIMelonSTAMP_MILLIS(elonvelonnt.favoritelon.elonvelonnt_timelon_ms) <= vars.elonndTimelon
        AND elonvelonnt.favoritelon IS NOT NULL
),

-- Gelont raw unfav elonvelonnts
raw_unfavs AS (
    SelonLelonCT elonvelonnt.unfavoritelon.uselonr_id AS uselonrId, elonvelonnt.unfavoritelon.twelonelont_id AS twelonelontId, elonvelonnt.unfavoritelon.elonvelonnt_timelon_ms AS tsMillis, -1 AS favOrUnfav
    FROM `twttr-bql-timelonlinelon-prod.timelonlinelon_selonrvicelon_favoritelons.timelonlinelon_selonrvicelon_favoritelons`, vars
    WHelonRelon (DATelon(_PARTITIONTIMelon) = DATelon(vars.startTimelon) OR DATelon(_PARTITIONTIMelon) = DATelon(vars.elonndTimelon)) AND
        TIMelonSTAMP_MILLIS(elonvelonnt.favoritelon.elonvelonnt_timelon_ms) >= vars.startTimelon
        AND TIMelonSTAMP_MILLIS(elonvelonnt.favoritelon.elonvelonnt_timelon_ms) <= vars.elonndTimelon
        AND elonvelonnt.unfavoritelon IS NOT NULL
),

-- Union fav and unfav elonvelonnts
favs_unionelond AS (
    SelonLelonCT * FROM raw_favs
    UNION ALL
    SelonLelonCT * FROM raw_unfavs
),

-- Group by uselonr and twelonelontId
uselonr_twelonelont_fav_pairs AS (
    SelonLelonCT uselonrId, twelonelontId, ARRAY_AGG(STRUCT(favOrUnfav, tsMillis) ORDelonR BY tsMillis DelonSC LIMIT 1) as delontails, count(*) as cnt
    FROM favs_unionelond
    GROUP BY uselonrId, twelonelontId
),

-- Relonmovelon unfav elonvelonnts
twelonelont_raw_favs_tablelon AS (
    SelonLelonCT uselonrId, twelonelontId, CAST(dt.tsMillis  AS FLOAT64) AS tsMillis
    FROM uselonr_twelonelont_fav_pairs CROSS JOIN UNNelonST(delontails) as dt
    WHelonRelon cnt < 3 AND dt.favOrUnfav = 1 -- cnt < 3 to relonmovelon crazy fav/unfav uselonrs
),

-- Gelont twelonelontIds that arelon elonligiblelon for twelonelont elonmbelonddings
twelonelont_favs_tablelon AS (
    SelonLelonCT uselonrId, twelonelont_raw_favs_tablelon.twelonelontId, tsMillis
    FROM twelonelont_raw_favs_tablelon, vars
    JOIN (
        SelonLelonCT twelonelontId, COUNT(DISTINCT(uselonrId)) AS favCount
        FROM twelonelont_raw_favs_tablelon
        GROUP BY twelonelontId
        HAVING favCount >= 8 --welon only gelonnelonratelon twelonelont elonmbelonddings for twelonelonts with >= 8 favs
    ) elonligiblelon_twelonelonts USING(twelonelontId)
     -- Apply twelonelont agelon filtelonr helonrelon
    WHelonRelon timelonstamp_millis((1288834974657 + ((twelonelont_raw_favs_tablelon.twelonelontId  & 9223372036850581504) >> 22))) >= vars.noOldelonrTwelonelontsThanDatelon
),

-- Relonad consumelonr elonmbelonddings
consumelonr_elonmbelonddings AS (
  {CONSUMelonR_elonMBelonDDINGS_SQL}
),

-- Updatelon twelonelont clustelonr scorelons baselond on fav elonvelonnts
twelonelont_clustelonr_scorelons AS (
    SelonLelonCT twelonelontId,
        STRUCT(
            clustelonrId,
            CASelon vars.halfLifelon
              -- halfLifelon = -1 melonans thelonrelon is no half lifelon/deloncay and welon direlonctly takelon thelon sum as thelon scorelon
              WHelonN -1 THelonN SUM(clustelonrNormalizelondLogFavScorelon)
              elonLSelon SUM(clustelonrNormalizelondLogFavScorelon * POW(0.5, (currelonntTs - tsMillis) / vars.halfLifelon))
              elonND AS clustelonrNormalizelondLogFavScorelon,
            COUNT(*) AS favCount)
        AS clustelonrIdToScorelons
    FROM twelonelont_favs_tablelon, vars
    JOIN consumelonr_elonmbelonddings USING(uselonrId)
    GROUP BY twelonelontId, clustelonrId, vars.halfLifelon
),

-- Gelonnelonratelon twelonelont elonmbelonddings
twelonelont_elonmbelonddings_with_top_clustelonrs AS (
    SelonLelonCT twelonelontId, ARRAY_AGG(
        clustelonrIdToScorelons
        ORDelonR BY clustelonrIdToScorelons.clustelonrNormalizelondLogFavScorelon DelonSC
        LIMIT {TWelonelonT_elonMBelonDDING_LelonNGTH}
    ) AS clustelonrIdToScorelons
    FROM twelonelont_clustelonr_scorelons
    GROUP BY twelonelontId
)

-- Relonturn (twelonelontId, clustelonrId, twelonelontScorelon) pairs whelonrelon twelonelontScorelon > twelonelontelonmbelonddingsMinClustelonrScorelon
SelonLelonCT twelonelontId,
    clustelonrId,
    clustelonrNormalizelondLogFavScorelon AS twelonelontScorelon, clustelonrIdToScorelons
FROM twelonelont_elonmbelonddings_with_top_clustelonrs, UNNelonST(clustelonrIdToScorelons) AS clustelonrIdToScorelons, vars
WHelonRelon clustelonrIdToScorelons.clustelonrNormalizelondLogFavScorelon > vars.twelonelontelonmbelonddingsMinClustelonrScorelon
