with
  vaws as (
    sewect
      t-timestamp("{stawt_time}") a-as stawt_date, >_<
      t-timestamp("{end_time}") a-as end_date
  ), rawr x3

  a-ads_engagement a-as (
    s-sewect
        u-usewid64 as usewid, mya
        pwomotedtweetid as tweetid, nyaa~~
        unix_miwwis(timestamp) as tsmiwwis, (⑅˘꒳˘)
        wineitemid
    fwom `twttw-wev-cowe-data-pwod.cowe_sewved_impwessions.spend`, rawr x3 v-vaws
    whewe timestamp(_batchend) >= vaws.stawt_date a-and timestamp(_batchend) <= vaws.end_date
    a-and
      engagementtype in ({contwibuting_action_types_stw})
      and wineitemobjective != 9 -- nyot pwe-woww a-ads
  ), (✿oωo)

  wine_items as (
      s-sewect
        i-id as wineitemid, (ˆ ﻌ ˆ)♡
        end_time.posixtime as endtime
      fwom
        `twttw-wev-cowe-data-pwod.wev_ads_pwoduction.wine_items`
  )


sewect
  u-usewid, (˘ω˘)
  tweetid, (⑅˘꒳˘)
  tsmiwwis
fwom ads_engagement join wine_items using(wineitemid), (///ˬ///✿) v-vaws
whewe
  wine_items.endtime i-is nyuww
  o-ow timestamp_miwwis(wine_items.endtime) >= v-vaws.end_date

