WITH matwitterts as (SELECT as value MAtwitter(ts) as ts FROM `twttr-recos-ml-prod.ssedhain.twhin_tweet_avg_embedding`)
SELECT entityId, embedding
FROM `twttr-recos-ml-prod.ssedhain.twhin_tweet_avg_embedding`
WHERE ts >= (select matwitter(matwitterts) from matwitterts)
AND DATE(TIMESTAMP_MILLIS(createdAt)) <= (select matwitter(matwitterts) from matwitterts)
AND DATE(TIMESTAMP_MILLIS(createdAt)) >= DATE_SUB((select matwitter(matwitterts) from matwitterts), INTERVAL 1 DAY)