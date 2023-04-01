 SELECT DISTINCT tweetId
    FROM `twttr-bq-tweetsource-prod.user.unhydrated_flat`, UNNEST(entity_annotations) AS ea
    WHERE
      (DATE(_PARTITIONTIME) >= DATE("{START_TIME}") AND DATE(_PARTITIONTIME) <= DATE("{END_TIME}")) AND
       timestamp_millis((1288834974657 +
        ((tweetId  & 9223372036850581504) >> 22))) >= TIMESTAMP("{START_TIME}")
        AND timestamp_millis((1288834974657 +
      ((tweetId  & 9223372036850581504) >> 22))) <= TIMESTAMP("{END_TIME}")
      AND (
        ea.entityId IN (
          883054128338878464,
          1453131634669019141,
          1470464132432347136,
          1167512219786997760,
          1151588902739644416,
          1151920148661489664,
          1155582950991228928,
          738501328687628288,
          1047106191829028865
        )
      OR (
        ea.groupId IN (34, 35) # Cortex media understanding
        AND ea.entityId IN (
          1072916828484038657,
          1133752108212035585,
          1072916828488327170
          )
      )
      OR (
        ea.groupId IN (14) # Agatha Tweet Health Annotations
        AND ea.entityId IN (
          1242898721278324736,
          1230229436697473026,
          1230229470050603008
          )
      )
      OR (
        ea.groupId IN (10)
        AND ea.entityId IN (
          953701302608961536
          )
      )
  )
