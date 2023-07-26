with
  vaws as (
    sewect
      t-timestamp("{stawt_time}") a-as stawt_date, OwO
      t-timestamp("{end_time}") a-as end_date, (U ﹏ U)
  ), >w<

  -- g-get waw usew-tweet i-intewaction events f-fwom uua (we w-wiww use fav engagements hewe)
  waw_engagements as (
    sewect
      usewidentifiew.usewid a-as usewid, (U ﹏ U)
      eventmetadata.souwcetimestampms as tsmiwwis,
      c-case
          when actiontype i-in ({contwibuting_action_types_stw}) then {contwibuting_action_tweet_id_cowumn}
          when actiontype in ({undo_action_types_stw}) t-then {undo_action_tweet_id_cowumn}
      end as tweetid, 😳
      c-case
        w-when actiontype in ({contwibuting_action_types_stw}) then 1
        when actiontype in ({undo_action_types_stw}) t-then -1
      end as doowundo
    fwom `twttw-bqw-unified-pwod.unified_usew_actions_engagements.stweaming_unified_usew_actions_engagements`, (ˆ ﻌ ˆ)♡ vaws
    whewe (date(datehouw) >= date(vaws.stawt_date) a-and date(datehouw) <= d-date(vaws.end_date))
      a-and e-eventmetadata.souwcetimestampms >= u-unix_miwwis(vaws.stawt_date)
      and eventmetadata.souwcetimestampms <= unix_miwwis(vaws.end_date)
      and (actiontype in ({contwibuting_action_types_stw})
            o-ow actiontype in ({undo_action_types_stw}))
  ), 😳😳😳

  -- get video tweet ids
  video_tweet_ids a-as (
      with vaws as (
        sewect
          timestamp("{stawt_time}") as stawt_date, (U ﹏ U)
          timestamp("{end_time}") a-as end_date
      ), (///ˬ///✿)

      -- get waw u-usew-tweet intewaction e-events f-fwom uua
      video_view_engagements as (
        sewect item.tweetinfo.actiontweetid as tweetid
        f-fwom `twttw-bqw-unified-pwod.unified_usew_actions_engagements.stweaming_unified_usew_actions_engagements`, 😳 v-vaws
        whewe (date(datehouw) >= d-date(vaws.stawt_date) a-and date(datehouw) <= date(vaws.end_date))
          a-and eventmetadata.souwcetimestampms >= unix_miwwis(stawt_date)
          and e-eventmetadata.souwcetimestampms <= unix_miwwis(end_date)
          and (actiontype i-in ("cwienttweetvideopwayback50")
                ow actiontype i-in ("cwienttweetvideopwayback95"))
      )

      sewect distinct(tweetid)
      f-fwom video_view_engagements
  ), 😳

  -- j-join video tweet ids
  video_tweets_engagements as (
      sewect waw_engagements.*
      fwom waw_engagements j-join v-video_tweet_ids using(tweetid)
  ), σωσ

  -- g-gwoup b-by usewid and t-tweetid
  usew_tweet_engagement_paiws as (
    sewect usewid, tweetid, rawr x3 awway_agg(stwuct(doowundo, OwO t-tsmiwwis) owdew by tsmiwwis desc wimit 1) as detaiws, /(^•ω•^) count(*) as cnt
    fwom v-video_tweets_engagements
    gwoup b-by usewid, 😳😳😳 tweetid
  )

-- wemove u-undo events
s-sewect usewid, ( ͡o ω ͡o ) tweetid, >_< cast(dt.tsmiwwis  a-as fwoat64) a-as tsmiwwis
f-fwom usew_tweet_engagement_paiws, >w< v-vaws
cwoss join unnest(detaiws) as dt
whewe d-dt.doowundo = 1
