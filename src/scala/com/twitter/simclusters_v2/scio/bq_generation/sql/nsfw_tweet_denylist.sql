 SelonLelonCT DISTINCT twelonelontId
    FROM `twttr-bq-twelonelontsourcelon-prod.uselonr.unhydratelond_flat`, UNNelonST(elonntity_annotations) AS elona
    WHelonRelon
      (DATelon(_PARTITIONTIMelon) >= DATelon("{START_TIMelon}") AND DATelon(_PARTITIONTIMelon) <= DATelon("{elonND_TIMelon}")) AND
       timelonstamp_millis((1288834974657 +
        ((twelonelontId  & 9223372036850581504) >> 22))) >= TIMelonSTAMP("{START_TIMelon}")
        AND timelonstamp_millis((1288834974657 +
      ((twelonelontId  & 9223372036850581504) >> 22))) <= TIMelonSTAMP("{elonND_TIMelon}")
      AND (
        elona.elonntityId IN (
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
        elona.groupId IN (34, 35) # Cortelonx melondia undelonrstanding
        AND elona.elonntityId IN (
          1072916828484038657,
          1133752108212035585,
          1072916828488327170
          )
      )
      OR (
        elona.groupId IN (14) # Agatha Twelonelont Helonalth Annotations
        AND elona.elonntityId IN (
          1242898721278324736,
          1230229436697473026,
          1230229470050603008
          )
      )
      OR (
        elona.groupId IN (10)
        AND elona.elonntityId IN (
          953701302608961536
          )
      )
  )
