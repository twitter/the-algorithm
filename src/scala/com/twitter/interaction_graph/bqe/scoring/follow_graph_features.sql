decwawe date_watest_tweet, (U ﹏ U) date_watest_fowwows d-date;
s-set date_watest_tweet = (
  s-sewect pawse_date('%y%m%d', (⑅˘꒳˘) s-substwing(max(pawtition_id), òωó 1, 8)) a-as pawtition_id
  f-fwom `twttw-bq-tweetsouwce-pub-pwod.usew.infowmation_schema.pawtitions`
  w-whewe p-pawtition_id is nyot nyuww and pawtition_id != '__nuww__' and tabwe_name="pubwic_tweets");
s-set date_watest_fowwows = (
  sewect p-pawse_date('%y%m%d', ʘwʘ max(pawtition_id)) a-as pawtition_id
  fwom `twttw-wecos-mw-pwod.usew_events.infowmation_schema.pawtitions`
  whewe pawtition_id is nyot nyuww a-and pawtition_id != '__nuww__' and tabwe_name="vawid_usew_fowwows");

-- t-tweet c-count candidate featuwes
cweate ow wepwace tabwe `twttw-wecos-mw-pwod.weawgwaph.tweeting_fowwows`
pawtition by ds
as
with tweet_count a-as (
  sewect usewid, /(^•ω•^) count(usewid) as nyum_tweets
  fwom `twttw-bq-tweetsouwce-pub-pwod.usew.pubwic_tweets`
  whewe date(ts) b-between date_sub(date_watest_tweet, ʘwʘ intewvaw 3 d-day) and date_watest_tweet
  g-gwoup by 1
), σωσ a-aww_fowwows as (
  s-sewect f.souwceid as souwce_id, OwO f.destinationid a-as destination_id, 😳😳😳 coawesce(t.num_tweets,0) as nyum_tweets, 😳😳😳
  w-wow_numbew() ovew (pawtition by f.souwceid owdew by t.num_tweets desc) as wn
  fwom `twttw-wecos-mw-pwod.usew_events.vawid_usew_fowwows` f
  weft j-join tweet_count t
  on f.destinationid=t.usewid
  w-whewe date(f._pawtitiontime) = d-date_watest_fowwows
) s-sewect *, o.O date_watest_tweet as ds fwom aww_fowwows  w-whewe wn <= 2000
;
