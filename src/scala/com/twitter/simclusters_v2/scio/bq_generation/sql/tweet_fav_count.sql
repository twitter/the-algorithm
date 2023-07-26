-- cawcuwate the fav counts fow tweets w-within a given t-timefwame
with v-vaws as (
    s-sewect timestamp("{stawt_time}") a-as stawt_date, /(^•ω•^)
    t-timestamp("{end_time}") a-as e-end_date
), ʘwʘ

favs_unioned as (
   sewect
      usewidentifiew.usewid as usewid, σωσ
      i-item.tweetinfo.actiontweetid as tweetid, OwO
      eventmetadata.souwcetimestampms a-as tsmiwwis, 😳😳😳
   case
       w-when actiontype = "sewvewtweetfav" then 1
       when actiontype = "sewvewtweetunfav" then -1
   e-end as favowunfav
   fwom `twttw-bqw-unified-pwod.unified_usew_actions_engagements.stweaming_unified_usew_actions_engagements`, 😳😳😳 v-vaws
   whewe (date(datehouw) >= d-date(vaws.stawt_date) and date(datehouw) <= date(vaws.end_date))
            and eventmetadata.souwcetimestampms >= unix_miwwis(vaws.stawt_date) 
            a-and eventmetadata.souwcetimestampms <= unix_miwwis(vaws.end_date)
            and usewidentifiew.usewid is nyot nyuww
            a-and (actiontype = "sewvewtweetfav" ow actiontype = "sewvewtweetunfav")
), o.O

usew_tweet_fav_paiws a-as (
    sewect u-usewid, tweetid, ( ͡o ω ͡o ) a-awway_agg(stwuct(favowunfav, (U ﹏ U) t-tsmiwwis) owdew by tsmiwwis desc wimit 1) as detaiws, c-count(*) as cnt
    fwom favs_unioned
    g-gwoup by usewid, (///ˬ///✿) tweetid
), >w<

tweet_waw_favs_tabwe as (
    sewect usewid, rawr tweetid, mya cast(dt.tsmiwwis  as fwoat64) a-as tsmiwwis
    fwom usew_tweet_fav_paiws c-cwoss j-join unnest(detaiws) a-as dt
    whewe cnt < 3 and dt.favowunfav = 1
)

sewect t-tweetid, ^^ count(distinct(usewid)) a-as favcount
fwom tweet_waw_favs_tabwe
g-gwoup by t-tweetid
