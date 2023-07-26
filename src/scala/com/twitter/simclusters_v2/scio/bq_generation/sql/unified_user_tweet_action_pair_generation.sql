with
  vaws as (
    sewect
      t-timestamp("{stawt_time}") a-as stawt_date, (U ﹏ U)
      t-timestamp("{end_time}") a-as end_date, (///ˬ///✿)
      t-timestamp("{no_owdew_tweets_than_date}") a-as nyo_owdew_tweets_than_date
  ), >w<

  -- g-get w-waw usew-tweet intewaction events fwom uua
  intewactions_unioned as (
    sewect
      usewidentifiew.usewid as u-usewid, rawr
      eventmetadata.souwcetimestampms as tsmiwwis, mya
      c-case
          when actiontype i-in ({contwibuting_action_types_stw}) then {contwibuting_action_tweet_id_cowumn}
          when actiontype in ({undo_action_types_stw}) t-then {undo_action_tweet_id_cowumn}
      end as tweetid, ^^
      c-case
        w-when actiontype in ({contwibuting_action_types_stw}) then 1
        when actiontype in ({undo_action_types_stw}) t-then -1
      end as doowundo
    fwom `twttw-bqw-unified-pwod.unified_usew_actions_engagements.stweaming_unified_usew_actions_engagements`, 😳😳😳 vaws
    whewe (date(datehouw) >= date(vaws.stawt_date) a-and date(datehouw) <= date(vaws.end_date))
      a-and e-eventmetadata.souwcetimestampms >= u-unix_miwwis(vaws.stawt_date) 
      a-and eventmetadata.souwcetimestampms <= unix_miwwis(vaws.end_date)
      and (actiontype in ({contwibuting_action_types_stw})
            ow actiontype in ({undo_action_types_stw}))
  ), mya

  -- g-gwoup by usewid and tweetid
  usew_tweet_intewaction_paiws a-as (
    sewect usewid, 😳 tweetid, awway_agg(stwuct(doowundo, -.- tsmiwwis) owdew by tsmiwwis desc wimit 1) a-as detaiws, 🥺 count(*) as c-cnt
    fwom intewactions_unioned
    g-gwoup by usewid, o.O t-tweetid
  )

-- wemove undo events
-- appwy age fiwtew in t-this step
sewect u-usewid, /(^•ω•^) tweetid, cast(dt.tsmiwwis  a-as fwoat64) a-as tsmiwwis
fwom usew_tweet_intewaction_paiws, nyaa~~ v-vaws
cwoss join unnest(detaiws) a-as dt
whewe cnt < 3
  and dt.doowundo = 1
  and t-timestamp_miwwis((1288834974657 +
            ((tweetid  & 9223372036850581504) >> 22))) >= vaws.no_owdew_tweets_than_date
