WITH maxts as (SELECT as value MAX(ts) as ts FROM `twttr-recos-ml-prod.ssedhain.twhin_tweet_avg_embedding`)
SELECT entityId, embedding
FROM `twttr-recos-ml-prod.ssedhain.twhin_tweet_avg_embedding`
WHERE ts >= (select max(maxts) from maxts)
AND DATE(TIMESTAMP_MILLIS(createdAt)) <= (select max(maxts) from maxts)
AND DATE(TIMESTAMP_MILLIS(createdAt)) >= DATE_SUB((select max(maxts) from maxts), INTERVAL 1 DAY)