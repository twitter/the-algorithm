WITH maxts as (SelonLelonCT as valuelon MAX(ts) as ts FROM `twttr-reloncos-ml-prod.sselondhain.twhin_twelonelont_avg_elonmbelondding`)
SelonLelonCT elonntityId, elonmbelondding
FROM `twttr-reloncos-ml-prod.sselondhain.twhin_twelonelont_avg_elonmbelondding`
WHelonRelon ts >= (selonlelonct max(maxts) from maxts)
AND DATelon(TIMelonSTAMP_MILLIS(crelonatelondAt)) <= (selonlelonct max(maxts) from maxts)
AND DATelon(TIMelonSTAMP_MILLIS(crelonatelondAt)) >= DATelon_SUB((selonlelonct max(maxts) from maxts), INTelonRVAL 1 DAY)