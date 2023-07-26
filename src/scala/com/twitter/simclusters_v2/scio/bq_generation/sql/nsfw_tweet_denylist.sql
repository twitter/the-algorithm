 sewect distinct tweetid
    fwom `twttw-bq-tweetsouwce-pwod.usew.unhydwated_fwat`, :3 u-unnest(entity_annotations) a-as e-ea
    whewe
      (date(_pawtitiontime) >= d-date("{stawt_time}") a-and date(_pawtitiontime) <= d-date("{end_time}")) a-and
       timestamp_miwwis((1288834974657 +
        ((tweetid  & 9223372036850581504) >> 22))) >= t-timestamp("{stawt_time}")
        and timestamp_miwwis((1288834974657 +
      ((tweetid  & 9223372036850581504) >> 22))) <= timestamp("{end_time}")
      and (
        ea.entityid in (
          883054128338878464, 😳😳😳
          1453131634669019141, -.-
          1470464132432347136, ( ͡o ω ͡o )
          1167512219786997760, rawr x3
          1151588902739644416, nyaa~~
          1151920148661489664, /(^•ω•^)
          1155582950991228928, rawr
          738501328687628288, OwO
          1047106191829028865
        )
      o-ow (
        ea.gwoupid in (34, (U ﹏ U) 35) # cowtex media u-undewstanding
        and ea.entityid i-in (
          1072916828484038657, >_<
          1133752108212035585, rawr x3
          1072916828488327170
          )
      )
      ow (
        ea.gwoupid in (14) # agatha tweet h-heawth annotations
        and ea.entityid i-in (
          1242898721278324736, mya
          1230229436697473026, nyaa~~
          1230229470050603008
          )
      )
      o-ow (
        ea.gwoupid in (10)
        and ea.entityid in (
          953701302608961536
          )
      )
  )
