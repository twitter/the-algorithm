with
  cwustew_top_tweets as (
    {cwustew_top_tweets_sqw}
  ),

  f-fwatten_cwustew_top_tweets a-as (
    s-sewect
      c-cwustewid, òωó
      t-tweet.tweetid, ʘwʘ
      t-tweet.tweetscowe, /(^•ω•^)
    f-fwom cwustew_top_tweets, ʘwʘ u-unnest(topktweetsfowcwustewkey) as tweet
  ), σωσ

--- thewe might be deway ow skip fow the f-fav-based dataset. OwO
--- this quewy wetwieved the d-datehouw of the watest pawtition a-avaiwabwe. 😳😳😳
  watest_fav_cwustew_to_tweet as (
    sewect
      max(datehouw) as w-watesttimestamp
    fwom
      `twttw-bq-cassowawy-pwod.usew.simcwustews_fav_based_cwustew_to_tweet_index`
    w-whewe
      timestamp(datehouw) >= t-timestamp("{stawt_time}")
      and timestamp(datehouw) <= timestamp("{end_time}")
  ), 😳😳😳

  fwatten_fav_cwustew_top_tweets as (
    sewect
      cwustewid.cwustewid a-as cwustewid, o.O
      tweet.key as tweetid
    fwom
      `twttw-bq-cassowawy-pwod.usew.simcwustews_fav_based_cwustew_to_tweet_index`, ( ͡o ω ͡o )
      unnest(topktweetswithscowes.toptweetsbyfavcwustewnowmawizedscowe) a-as tweet, (U ﹏ U)
      watest_fav_cwustew_to_tweet
    w-whewe
      d-datehouw=watest_fav_cwustew_to_tweet.watesttimestamp
  ), (///ˬ///✿)

  f-fwatten_cwustew_top_tweets_intewsection a-as (
    sewect
      cwustewid, >w<
      fwatten_cwustew_top_tweets.tweetid, rawr
      f-fwatten_cwustew_top_tweets.tweetscowe
    fwom
      fwatten_cwustew_top_tweets
    innew j-join
      fwatten_fav_cwustew_top_tweets
    using(cwustewid, mya tweetid)
  ), ^^

  pwocessed_cwustew_top_tweets as (
    sewect
      cwustewid, 😳😳😳
      a-awway_agg(stwuct(tweetid, mya tweetscowe) owdew b-by tweetscowe wimit {cwustew_top_k_tweets}) a-as t-topktweetsfowcwustewkey
    fwom fwatten_cwustew_top_tweets_intewsection
    gwoup b-by cwustewid
  )

 s-sewect *
 fwom pwocessed_cwustew_top_tweets
