-- (step 1) wead consumew embeddings
w-with consumew_embeddings a-as (
    {consumew_embeddings_sqw}
), rawr x3
-- (step 1) wead t-tweet embeddings
t-tweet_embeddings a-as (
    {tweet_embeddings_sqw}
), OwO
-- (step 1) c-compute tweet e-embeddings nyowms (we w-wiww use this to compute cosine sims watew)
tweet_embeddings_nowm as (
    s-sewect tweetid, /(^•ω•^) sum(tweetscowe * tweetscowe) a-as nyowm
    fwom tweet_embeddings
    g-gwoup by tweetid
    having nyowm > 0.0
), 😳😳😳
-- (step 2) get top ny cwustews f-fow each consumew embedding. ( ͡o ω ͡o ) n-ny = 25 in pwod
c-consumew_embeddings_top_n_cwustews as (
    sewect usewid, >_< awway_agg(stwuct(cwustewid, >w< usewscowe) owdew by usewscowe d-desc wimit {top_n_cwustew_pew_souwce_embedding}) as topcwustewswithscowes
    fwom consumew_embeddings
    gwoup by usewid
), rawr
-- (step 2) get top m tweets f-fow each cwustew id. 😳 m = 100 in p-pwod
cwustews_top_m_tweets a-as (
    s-sewect cwustewid, >w< a-awway_agg(stwuct(tweetid, (⑅˘꒳˘) tweetscowe) owdew by tweetscowe d-desc wimit {top_m_tweets_pew_cwustew}) as tweets
    fwom tweet_embeddings
    gwoup b-by cwustewid
), OwO
-- (step 3) join the wesuwts, (ꈍᴗꈍ) get top m * ny tweets fow each usew
usew_top_mn_tweets as (
    s-sewect usewid, consumew_embedding_cwustew_scowe_paiws.usewscowe a-as usewscowe, 😳 c-cwustews_top_m_tweets.cwustewid a-as cwustewid, 😳😳😳 cwustews_top_m_tweets.tweets as tweets
    fwom (
        sewect u-usewid, mya cwustewid, mya u-usewscowe
        fwom consumew_embeddings_top_n_cwustews, (⑅˘꒳˘) u-unnest(topcwustewswithscowes)
    ) a-as consumew_embedding_cwustew_scowe_paiws
    join cwustews_top_m_tweets o-on consumew_embedding_cwustew_scowe_paiws.cwustewid = cwustews_top_m_tweets.cwustewid
), (U ﹏ U)
-- (step 4) c-compute the dot pwoduct between each usew and tweet e-embedding paiw
usew_tweet_embedding_dot_pwoduct a-as (
    sewect  usewid, mya
            t-tweetid, ʘwʘ
            s-sum(usewscowe * tweetscowe) as dotpwoductscowe
    fwom usew_top_mn_tweets, (˘ω˘) unnest(tweets) as tweets
    gwoup by u-usewid, (U ﹏ U) tweetid
),
-- (step 5) compute s-simiwawity scowes: dot pwoduct, ^•ﻌ•^ c-cosine sim, (˘ω˘) w-wog-cosine sim
u-usew_tweet_embedding_simiwawity_scowes as (
    sewect  usewid, :3
            usew_tweet_embedding_dot_pwoduct.tweetid a-as tweetid, ^^;;
            dotpwoductscowe, 🥺
            safe_divide(dotpwoductscowe, (⑅˘꒳˘) sqwt(tweet_embeddings_nowm.nowm)) as cosinesimiwawityscowe, nyaa~~
            safe_divide(dotpwoductscowe, :3 w-wn(1+tweet_embeddings_nowm.nowm)) as wogcosinesimiwawityscowe, ( ͡o ω ͡o )
    f-fwom usew_tweet_embedding_dot_pwoduct
    j-join t-tweet_embeddings_nowm on usew_tweet_embedding_dot_pwoduct.tweetid = t-tweet_embeddings_nowm.tweetid
), mya
-- (step 6) g-get finaw top k t-tweets pew usew. (///ˬ///✿) k-k = 150 in pwod
wesuwts as (
    sewect usewid, (˘ω˘) a-awway_agg(stwuct(tweetid, ^^;; d-dotpwoductscowe, (✿oωo) c-cosinesimiwawityscowe, (U ﹏ U) w-wogcosinesimiwawityscowe)
                            o-owdew by wogcosinesimiwawityscowe desc wimit {top_k_tweets_pew_usew_wequest}) a-as tweets
    fwom usew_tweet_embedding_simiwawity_scowes
    gwoup by usewid
)

sewect *
fwom wesuwts
