with
  vaws as (
    sewect
      t-timestamp("{stawt_time}") a-as stawt_date, -.-
      t-timestamp("{end_time}") a-as end_date, 😳
  ), mya

  -- g-get waw usew-tweet i-intewaction events f-fwom uua
  w-waw_engagements as (
    sewect
      usewidentifiew.usewid as usewid, (˘ω˘)
      eventmetadata.souwcetimestampms a-as tsmiwwis, >_<
      case
          w-when actiontype in ({contwibuting_action_types_stw}) t-then {contwibuting_action_tweet_id_cowumn}
          when actiontype in ({undo_action_types_stw}) then {undo_action_tweet_id_cowumn}
      e-end as tweetid, -.-
      case
        w-when actiontype i-in ({contwibuting_action_types_stw}) then 1
        when actiontype in ({undo_action_types_stw}) then -1
      e-end as doowundo
    fwom `twttw-bqw-unified-pwod.unified_usew_actions_engagements.stweaming_unified_usew_actions_engagements`, 🥺 vaws
    whewe (date(datehouw) >= date(vaws.stawt_date) and date(datehouw) <= date(vaws.end_date))
      a-and eventmetadata.souwcetimestampms >= unix_miwwis(vaws.stawt_date)
      a-and eventmetadata.souwcetimestampms <= u-unix_miwwis(vaws.end_date)
      a-and (actiontype i-in ({contwibuting_action_types_stw})
            ow actiontype in ({undo_action_types_stw}))
  ), (U ﹏ U)

  -- g-get evewgween tweet ids
  evewgween_tweet_ids as (
    sewect
        t-tweetid
    fwom `twttw-wecos-mw-pwod.simcwustews.evewgween_content_data`
    whewe timestamp(ts) =
        (  -- get watest pawtition time
        sewect m-max(timestamp(ts)) watest_pawtition
        f-fwom `twttw-wecos-mw-pwod.simcwustews.evewgween_content_data`
        w-whewe date(ts) b-between
            date_sub(date("{end_time}"), >w<
            intewvaw 14 day) and date("{end_time}")
        )
  ), mya

  -- j-join evewgween content t-tabwe
  evewgween_tweets_engagements as (
      s-sewect waw_engagements.*
      f-fwom waw_engagements join e-evewgween_tweet_ids using(tweetid)
  ), >w<

  -- g-gwoup by usewid and tweetid
  usew_tweet_engagement_paiws a-as (
    sewect usewid, nyaa~~ t-tweetid, awway_agg(stwuct(doowundo, (✿oωo) tsmiwwis) owdew b-by tsmiwwis d-desc wimit 1) as detaiws, ʘwʘ count(*) as cnt
    fwom evewgween_tweets_engagements
    gwoup by usewid, (ˆ ﻌ ˆ)♡ tweetid
  )

-- wemove undo e-events
sewect usewid, 😳😳😳 t-tweetid, cast(dt.tsmiwwis  a-as fwoat64) as t-tsmiwwis
fwom usew_tweet_engagement_paiws, :3 v-vaws
cwoss join unnest(detaiws) as dt
whewe cnt <= 10
  a-and dt.doowundo = 1
