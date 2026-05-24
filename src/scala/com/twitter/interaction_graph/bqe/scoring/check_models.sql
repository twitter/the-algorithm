(SELECT * FROM ML.FEATURE_IMPORTANCE(MODEL `twttr-recos-ml-prod.realgraph.prod`)
ORDER BY importance_gain DESC)
UNION ALL
(SELECT * FROM ML.FEATURE_IMPORTANCE(MODEL `twttr-recos-ml-prod.realgraph.prod_explicit`)
ORDER BY importance_gain DESC)
