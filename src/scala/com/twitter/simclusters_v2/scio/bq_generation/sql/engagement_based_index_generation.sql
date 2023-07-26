-- this sqw quewy genewate the cwustew t-to top k tweets i-index based o-on tweet engagements. OwO
-- t-the engagement t-type is d-decided by usew_tweet_engagement_tabwe_sqw. (ꈍᴗꈍ)

with v-vaws as (
        s-sewect {hawf_wife} as hawfwife, 😳 -- defauwt: 8 houw hawfwife in miwwis
        u-unix_miwwis("{cuwwent_ts}") as cuwwentts,
    ), 😳😳😳

  usew_tweet_engagement_paiws a-as (
      {usew_tweet_engagement_tabwe_sqw}
  ), mya

  -- a sequence o-of fiwtews to get ewigibwe tweetids fow tweet embedding genewation
  -- appwy m-min intewaction count fiwtew
 u-usew_tweet_intewaction_with_min_intewaction_count_fiwtew a-as (
      sewect usewid, mya usew_tweet_engagement_paiws.tweetid, (⑅˘꒳˘) tsmiwwis
      fwom usew_tweet_engagement_paiws, (U ﹏ U) v-vaws
      join (
          sewect tweetid, mya count(distinct(usewid)) as intewactioncount
          f-fwom usew_tweet_engagement_paiws
          g-gwoup by t-tweetid
          h-having intewactioncount >= {min_intewaction_count} -- o-onwy genewate tweet embeddings fow tweets w-with >= {min_intewaction_count} intewactions
      ) ewigibwe_tweets u-using(tweetid)
  ), ʘwʘ

  -- appwy min fav count fiwtew
  usew_tweet_intewaction_with_fav_count_fiwtew as (
    {tweet_intewaction_with_fav_count_fiwtew_sqw}
  ), (˘ω˘)

  -- appwy heawth and v-video fiwtew
  usew_tweet_intewaction_with_heawth_fiwtew as (
    {tweet_intewaction_with_heawth_fiwtew_sqw}
  ),

  -- f-finaw fiwtewed u-usew tweet i-intewaction tabwe
  -- wead the wesuwt fwom the wast fiwtew
  u-usew_tweet_intewaction_pwocessed_tabwe a-as (
    sewect *
    fwom u-usew_tweet_intewaction_with_heawth_fiwtew
  ), (U ﹏ U)

  -- w-wead consumew embeddings
  c-consumew_embeddings as (
     {consumew_embeddings_sqw}
  ), ^•ﻌ•^

  -- u-update tweet cwustew scowes based on intewaction e-events
  tweet_cwustew_scowes as (
      sewect t-tweetid, (˘ω˘)
          stwuct(
              cwustewid, :3
              c-case vaws.hawfwife
                -- h-hawfwife = -1 means thewe is nyo hawf wife decay and we diwectwy take the sum as the scowe
                w-when -1 t-then sum(cwustewnowmawizedwogfavscowe)
                ewse sum(cwustewnowmawizedwogfavscowe * p-pow(0.5, ^^;; (cuwwentts - t-tsmiwwis) / v-vaws.hawfwife))
                end as nyowmawizedscowe, 🥺
              count(*) as engagementcount)
          a-as cwustewidtoscowes
      fwom usew_tweet_intewaction_pwocessed_tabwe, (⑅˘꒳˘) vaws
      join consumew_embeddings u-using(usewid)
      gwoup by tweetid, nyaa~~ c-cwustewid, :3 vaws.hawfwife
  ), ( ͡o ω ͡o )

  -- g-genewate tweet e-embeddings
  tweet_embeddings_with_top_cwustews a-as (
      s-sewect tweetid, a-awway_agg(
          c-cwustewidtoscowes
          owdew by cwustewidtoscowes.nowmawizedscowe desc
          w-wimit {tweet_embedding_wength}
      ) a-as cwustewidtoscowes
      f-fwom t-tweet_cwustew_scowes
      g-gwoup by tweetid
  ), mya

  cwustews_top_k_tweets as (
    s-sewect cwustewid, (///ˬ///✿) awway_agg(stwuct(tweetid, (˘ω˘) nowmawizedscowe as tweetscowe) owdew by nyowmawizedscowe desc wimit {cwustew_top_k_tweets}) a-as topktweetsfowcwustewkey
    fwom tweet_embeddings_with_top_cwustews, ^^;; u-unnest(cwustewidtoscowes) as c-cwustewidtoscowes
    w-whewe engagementcount >= {min_engagement_pew_cwustew}
    gwoup by cwustewid
  )

s-sewect *
fwom cwustews_top_k_tweets

