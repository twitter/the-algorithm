W TH maxts as (SELECT as value MAX(ts) as ts FROM `twttr-recos-ml-prod.ssedha n.twh n_t et_avg_embedd ng`)
SELECT ent y d, embedd ng
FROM `twttr-recos-ml-prod.ssedha n.twh n_t et_avg_embedd ng`
WHERE ts >= (select max(maxts) from maxts)
AND DATE(T MESTAMP_M LL S(createdAt)) <= (select max(maxts) from maxts)
AND DATE(T MESTAMP_M LL S(createdAt)) >= DATE_SUB((select max(maxts) from maxts),  NTERVAL 1 DAY)