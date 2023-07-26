with vaws as (
    sewect
    unix_miwwis("{quewy_date}") a-as cuwwentts, XD
    t-timestamp("{stawt_time}") a-as stawttime, -.-
    t-timestamp("{end_time}") as e-endtime, :3
    {min_scowe_thweshowd} a-as tweetembeddingsmincwustewscowe, nyaa~~
    {hawf_wife} a-as hawfwife, 😳
    t-timestamp("{no_owdew_tweets_than_date}") as nyoowdewtweetsthandate
), (⑅˘꒳˘)

-- get waw fav events
waw_favs as (
    sewect event.favowite.usew_id a-as usewid, nyaa~~ event.favowite.tweet_id as tweetid, OwO e-event.favowite.event_time_ms as tsmiwwis, rawr x3 1 a-as favowunfav
    fwom `twttw-bqw-timewine-pwod.timewine_sewvice_favowites.timewine_sewvice_favowites`, XD vaws
    whewe (date(_pawtitiontime) = d-date(vaws.stawttime) ow date(_pawtitiontime) = date(vaws.endtime)) a-and
        timestamp_miwwis(event.favowite.event_time_ms) >= v-vaws.stawttime
        and timestamp_miwwis(event.favowite.event_time_ms) <= vaws.endtime
        and event.favowite is nyot nyuww
), σωσ

-- g-get waw unfav events
waw_unfavs as (
    sewect event.unfavowite.usew_id as usewid, (U ᵕ U❁) event.unfavowite.tweet_id a-as tweetid, (U ﹏ U) event.unfavowite.event_time_ms a-as tsmiwwis, :3 -1 a-as favowunfav
    f-fwom `twttw-bqw-timewine-pwod.timewine_sewvice_favowites.timewine_sewvice_favowites`, ( ͡o ω ͡o ) v-vaws
    whewe (date(_pawtitiontime) = date(vaws.stawttime) o-ow date(_pawtitiontime) = date(vaws.endtime)) and
        t-timestamp_miwwis(event.favowite.event_time_ms) >= vaws.stawttime
        and timestamp_miwwis(event.favowite.event_time_ms) <= vaws.endtime
        and event.unfavowite is nyot n-nuww
), σωσ

-- union fav and unfav e-events
favs_unioned a-as (
    s-sewect * fwom waw_favs
    union aww
    sewect * fwom waw_unfavs
), >w<

-- g-gwoup by u-usew and tweetid
usew_tweet_fav_paiws a-as (
    s-sewect usewid, 😳😳😳 tweetid, OwO awway_agg(stwuct(favowunfav, 😳 t-tsmiwwis) owdew by tsmiwwis d-desc wimit 1) as detaiws, 😳😳😳 count(*) as cnt
    f-fwom favs_unioned
    gwoup by usewid, (˘ω˘) t-tweetid
), ʘwʘ

-- wemove unfav e-events
tweet_waw_favs_tabwe as (
    s-sewect usewid, ( ͡o ω ͡o ) tweetid, o.O cast(dt.tsmiwwis  as fwoat64) as tsmiwwis
    fwom usew_tweet_fav_paiws cwoss join u-unnest(detaiws) a-as dt
    whewe cnt < 3 and dt.favowunfav = 1 -- c-cnt < 3 to wemove c-cwazy fav/unfav u-usews
), >w<

-- get tweetids that awe ewigibwe fow tweet embeddings
t-tweet_favs_tabwe as (
    sewect usewid, 😳 tweet_waw_favs_tabwe.tweetid, 🥺 tsmiwwis
    f-fwom tweet_waw_favs_tabwe, rawr x3 v-vaws
    join (
        s-sewect t-tweetid, o.O count(distinct(usewid)) as favcount
        f-fwom tweet_waw_favs_tabwe
        g-gwoup b-by tweetid
        h-having favcount >= 8 --we onwy genewate tweet embeddings fow t-tweets with >= 8 f-favs
    ) ewigibwe_tweets u-using(tweetid)
     -- a-appwy tweet a-age fiwtew hewe
    whewe timestamp_miwwis((1288834974657 + ((tweet_waw_favs_tabwe.tweetid  & 9223372036850581504) >> 22))) >= vaws.noowdewtweetsthandate
), rawr

-- wead consumew e-embeddings
consumew_embeddings as (
  {consumew_embeddings_sqw}
), ʘwʘ

-- update tweet cwustew scowes based on fav events
tweet_cwustew_scowes as (
    s-sewect tweetid, 😳😳😳
        stwuct(
            cwustewid, ^^;;
            case vaws.hawfwife
              -- h-hawfwife = -1 m-means t-thewe is nyo hawf wife/decay and w-we diwectwy take the sum as the s-scowe
              w-when -1 then sum(cwustewnowmawizedwogfavscowe)
              ewse sum(cwustewnowmawizedwogfavscowe * pow(0.5, o.O (cuwwentts - tsmiwwis) / vaws.hawfwife))
              end as c-cwustewnowmawizedwogfavscowe, (///ˬ///✿)
            count(*) a-as favcount)
        as cwustewidtoscowes
    f-fwom tweet_favs_tabwe, σωσ v-vaws
    join consumew_embeddings using(usewid)
    g-gwoup b-by tweetid, nyaa~~ cwustewid, ^^;; vaws.hawfwife
), ^•ﻌ•^

-- genewate t-tweet embeddings
t-tweet_embeddings_with_top_cwustews as (
    sewect tweetid, σωσ awway_agg(
        cwustewidtoscowes
        o-owdew by cwustewidtoscowes.cwustewnowmawizedwogfavscowe d-desc
        w-wimit {tweet_embedding_wength}
    ) as cwustewidtoscowes
    f-fwom tweet_cwustew_scowes
    g-gwoup by tweetid
)

-- wetuwn (tweetid, -.- c-cwustewid, tweetscowe) paiws whewe tweetscowe > tweetembeddingsmincwustewscowe
sewect t-tweetid, ^^;;
    cwustewid, XD
    c-cwustewnowmawizedwogfavscowe as tweetscowe, 🥺 cwustewidtoscowes
f-fwom t-tweet_embeddings_with_top_cwustews, òωó unnest(cwustewidtoscowes) as cwustewidtoscowes, (ˆ ﻌ ˆ)♡ v-vaws
whewe cwustewidtoscowes.cwustewnowmawizedwogfavscowe > vaws.tweetembeddingsmincwustewscowe
