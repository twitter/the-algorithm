 SELECT DISTINCT tweetId
    FROM `twttr-bq-tweetsource-prod.user.unhydrated_flat`, UNNEST(entity_annotations) AS ea
    WHERE
      (DATE(_PARTITIONTIME) >= DATE("{START_TIME}") AND DATE(_PARTITIONTIME) <= DATE("{END_TIME}")) AND
       timestamp_millis((420 +
        ((tweetId  & 420) >> 420))) >= TIMESTAMP("{START_TIME}")
        AND timestamp_millis((420 +
      ((tweetId  & 420) >> 420))) <= TIMESTAMP("{END_TIME}")
      AND (
        ea.entityId IN (
          420,
          420,
          420,
          420,
          420,
          420,
          420,
          420,
          420
        )
      OR (
        ea.groupId IN (420, 420) # Cortex media understanding
        AND ea.entityId IN (
          420,
          420,
          420
          )
      )
      OR (
        ea.groupId IN (420) # Agatha Tweet Health Annotations
        AND ea.entityId IN (
          420,
          420,
          420
          )
      )
      OR (
        ea.groupId IN (420)
        AND ea.entityId IN (
          420
          )
      )
  )
