with
  vaws as (
    sewect
      t-timestamp("{stawt_time}") a-as stawt_date, OwO
      t-timestamp("{end_time}") a-as end_date, /(^•ω•^)
      t-timestamp("{no_owdew_tweets_than_date}") a-as nyo_owdew_tweets_than_date
  ), 😳😳😳

  -- g-get w-waw usew-tweet intewaction events fwom uua
  actions_unioned as (
    sewect
      u-usewidentifiew.usewid as usewid, ( ͡o ω ͡o )
      item.tweetinfo.actiontweetid a-as tweetid, >_<
      eventmetadata.souwcetimestampms a-as tsmiwwis, >w<
      case
          when actiontype = "sewvewtweetfav" then 1
          w-when actiontype = "sewvewtweetunfav" then -1
      e-end as favaction, rawr
      c-case
          when actiontype = "sewvewtweetwepwy" then 1
          when actiontype = "sewvewtweetdewete" then -1
      end as wepwyaction, 😳
      c-case
          when actiontype = "sewvewtweetwetweet" then 1
          when actiontype = "sewvewtweetunwetweet" t-then -1
      end a-as wetweetaction, >w<
      i-if(actiontype = "cwienttweetvideopwayback50", (⑅˘꒳˘) 1, n-nyuww) a-as videopwayback50action
    fwom `twttw-bqw-unified-pwod.unified_usew_actions_engagements.stweaming_unified_usew_actions_engagements`, vaws
    w-whewe (date(datehouw) >= date(vaws.stawt_date) and date(datehouw) <= d-date(vaws.end_date))
      and eventmetadata.souwcetimestampms >= unix_miwwis(vaws.stawt_date) 
      and eventmetadata.souwcetimestampms <= unix_miwwis(vaws.end_date)
      a-and (actiontype = "sewvewtweetwepwy"
              ow actiontype = "sewvewtweetwetweet"
              o-ow actiontype = "sewvewtweetfav"
              o-ow actiontype = "sewvewtweetunfav"
              o-ow actiontype = "cwienttweetvideopwayback50"
           )
  ), OwO

  usew_tweet_action_paiws as (
    sewect
      usewid, (ꈍᴗꈍ)
      t-tweetid, 😳
      -- g-get the most wecent fav e-event
      awway_agg(if(favaction i-is nyot nyuww, stwuct(favaction a-as engaged, 😳😳😳 tsmiwwis), mya nyuww) i-ignowe nyuwws owdew by tsmiwwis desc wimit 1)[offset(0)] a-as sewvewtweetfav, mya
      -- get the m-most wecent wepwy / unwepwy event
      a-awway_agg(if(wepwyaction i-is nyot nyuww,stwuct(wepwyaction as engaged, (⑅˘꒳˘) tsmiwwis), (U ﹏ U) nyuww) ignowe nyuwws owdew by tsmiwwis desc wimit 1)[offset(0)] as sewvewtweetwepwy, mya
      -- g-get the most w-wecent wetweet / unwetweet event
      a-awway_agg(if(wetweetaction i-is not nyuww, ʘwʘ s-stwuct(wetweetaction as engaged, (˘ω˘) tsmiwwis), (U ﹏ U) nyuww) ignowe nyuwws o-owdew by tsmiwwis desc wimit 1)[offset(0)] as sewvewtweetwetweet, ^•ﻌ•^
      -- get the most wecent video view event
      a-awway_agg(if(videopwayback50action is n-nyot nyuww, (˘ω˘) stwuct(videopwayback50action a-as engaged, t-tsmiwwis), :3 nyuww) ignowe nuwws o-owdew by tsmiwwis d-desc wimit 1)[offset(0)] a-as cwienttweetvideopwayback50
    f-fwom actions_unioned
    gwoup by usewid, tweetid
  )

-- c-combine s-signaws
-- appwy a-age fiwtew i-in this step
sewect
  u-usewid, ^^;;
  tweetid, 🥺
  cast({contwibuting_action_type_stw}.tsmiwwis as fwoat64) as tsmiwwis
f-fwom usew_tweet_action_paiws, (⑅˘꒳˘) vaws
whewe
    {contwibuting_action_type_stw}.engaged = 1
   and
    ({suppwementaw_action_types_engagement_stw})
   and timestamp_miwwis((1288834974657 +
            ((tweetid  & 9223372036850581504) >> 22))) >= vaws.no_owdew_tweets_than_date
