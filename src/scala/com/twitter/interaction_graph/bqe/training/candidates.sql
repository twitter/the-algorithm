-- get latest part  on of cand dates w h data
DECLARE date_cand dates DATE;
SET date_cand dates = (SELECT DATE(T MESTAMP_M LL S($start_t  $)));

CREATE TABLE  F NOT EX STS  `twttr-recos-ml-prod.realgraph.cand dates_sampled` AS
SELECT * FROM `twttr-recos-ml-prod.realgraph.cand dates_for_tra n ng` L M T 100;

-- remove prev ous output snapshot ( f ex sts) to avo d double-wr  ng
DELETE
FROM `twttr-recos-ml-prod.realgraph.cand dates_sampled`
WHERE ds = date_cand dates;

-- sample from cand dates table  nstead of recomput ng features
 NSERT  NTO `twttr-recos-ml-prod.realgraph.cand dates_sampled`
SELECT * FROM `twttr-recos-ml-prod.realgraph.cand dates_for_tra n ng`
WHERE MOD(ABS(FARM_F NGERPR NT(CONCAT(s ce_ d, '_', dest nat on_ d))), 100) = $mod_rema nder$
AND ds = date_cand dates;

