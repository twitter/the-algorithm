 SELECT D ST NCT t et d
    FROM `twttr-bq-t ets ce-prod.user.unhydrated_flat`, UNNEST(ent y_annotat ons) AS ea
    WHERE
      (DATE(_PART T ONT ME) >= DATE("{START_T ME}") AND DATE(_PART T ONT ME) <= DATE("{END_T ME}")) AND
       t  stamp_m ll s((1288834974657 +
        ((t et d  & 9223372036850581504) >> 22))) >= T MESTAMP("{START_T ME}")
        AND t  stamp_m ll s((1288834974657 +
      ((t et d  & 9223372036850581504) >> 22))) <= T MESTAMP("{END_T ME}")
      AND (
        ea.ent y d  N (
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
        ea.group d  N (34, 35) # Cortex  d a understand ng
        AND ea.ent y d  N (
          1072916828484038657,
          1133752108212035585,
          1072916828488327170
          )
      )
      OR (
        ea.group d  N (14) # Agatha T et  alth Annotat ons
        AND ea.ent y d  N (
          1242898721278324736,
          1230229436697473026,
          1230229470050603008
          )
      )
      OR (
        ea.group d  N (10)
        AND ea.ent y d  N (
          953701302608961536
          )
      )
  )
