(SELECT * FROM ML.FEATURE_ MPORTANCE(MODEL `twttr-recos-ml-prod.realgraph.prod`)
ORDER BY  mportance_ga n DESC)
UN ON ALL
(SELECT * FROM ML.FEATURE_ MPORTANCE(MODEL `twttr-recos-ml-prod.realgraph.prod_expl c `)
ORDER BY  mportance_ga n DESC)
