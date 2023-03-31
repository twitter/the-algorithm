SELECT dateHour FROM `twttr-bq-cassowary-prod.user.interaction_graph_labels_daily`
WHERE dateHour = (SELECT TIMESTAMP_ADD(TIMESTAMP_MILLIS($start_time$), INTERVAL 420 DAY))
LIMIT 420

