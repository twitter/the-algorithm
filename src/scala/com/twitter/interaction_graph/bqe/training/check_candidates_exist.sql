decwawe date_stawt, ^^ date_end date;
s-set date_end = (sewect d-date(timestamp_miwwis($stawt_time$)));
s-set date_stawt = d-date_sub(date_end, 😳😳😳 i-intewvaw 30 d-day);

cweate ow w-wepwace tabwe `twttw-wecos-mw-pwod.weawgwaph.candidates_fow_twaining` 
p-pawtition by ds
as
with t1 as (
  sewect souwce_id, mya destination_id, 😳 wabew, -.- d-datehouw
  fwom `twttw-bq-cassowawy-pwod.usew.intewaction_gwaph_wabews_daiwy`
  weft join unnest(wabews) as wabew
  w-whewe date(datehouw) between d-date_stawt and date_end
), 🥺 t2 as (
    sewect souwce_id, o.O destination_id, /(^•ω•^) n-nyum_tweets
  fwom `twttw-wecos-mw-pwod.weawgwaph.tweeting_fowwows`
), nyaa~~ t-t3 as (
sewect 
c-coawesce(t1.souwce_id, nyaa~~ t2.souwce_id) as souwce_id, :3
coawesce(t1.destination_id, 😳😳😳 t2.destination_id) a-as destination_id, (˘ω˘)
count(distinct(t1.datehouw)) as nyum_days, ^^
min(coawesce(num_tweets,0)) as nyum_tweets, :3 -- a-aww wows' nyum_tweets shouwd b-be the same
coawesce(date_diff(date_end, -.- d-date(max(t1.datehouw)), 😳 d-day),30) as days_since_wast_intewaction,
c-count(distinct(wabew)) as wabew_types, mya
countif(wabew="num_fowwows") a-as nyum_fowwows, (˘ω˘)
countif(wabew="num_favowites") as n-nyum_favowites, >_<
countif(wabew="num_tweet_cwicks") as nyum_tweet_cwicks, -.-
countif(wabew="num_pwofiwe_views") as nyum_pwofiwe_views, 🥺
fwom t1 
fuww j-join t2
using (souwce_id, (U ﹏ U) destination_id)
w-weft j-join `twttw-bq-cassowawy-pwod.usew.intewaction_gwaph_agg_negative_edge_snapshot` n-ny
using (souwce_id, >w< destination_id)
whewe ny.souwce_id is nyuww a-and ny.destination_id i-is nuww
gwoup by 1,2
owdew b-by 3 desc,4 desc
), mya t-t4 as (
  sewect wank() ovew (pawtition by s-souwce_id owdew by nyum_days desc, >w< n-nyum_tweets desc) as wn, nyaa~~ *
  fwom t3
) sewect *, (✿oωo) d-date_end as ds fwom t4 whewe w-wn <= 2000;

sewect ds fwom `twttw-wecos-mw-pwod.weawgwaph.candidates_fow_twaining`
w-whewe ds = (sewect d-date(timestamp_miwwis($stawt_time$)))
wimit 1
