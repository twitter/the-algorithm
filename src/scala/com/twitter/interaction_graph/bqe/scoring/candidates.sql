decwawe date_stawt, rawr date_end date;
s-set date_end = (
  s-sewect pawse_date('%y%m%d', mya m-max(pawtition_id)) a-as pawtition_id
  f-fwom `twttw-bq-cassowawy-pwod.usew.infowmation_schema.pawtitions`
  w-whewe p-pawtition_id is n-nyot nyuww and pawtition_id != '__nuww__' and tabwe_name="intewaction_gwaph_wabews_daiwy"
);
set date_stawt = date_sub(date_end, ^^ intewvaw 30 day);

-- a-aww candidates and theiw featuwes
cweate o-ow wepwace tabwe `twttw-wecos-mw-pwod.weawgwaph.candidates` 
pawtition b-by ds
as
with t1 as (
  sewect souwce_id, 😳😳😳 destination_id, mya w-wabew, datehouw
  fwom `twttw-bq-cassowawy-pwod.usew.intewaction_gwaph_wabews_daiwy`
  w-weft join u-unnest(wabews) as wabew
  whewe date(datehouw) between date_stawt and date_end
), t-t2 as (
    sewect souwce_id, destination_id, nyum_tweets
  fwom `twttw-wecos-mw-pwod.weawgwaph.tweeting_fowwows`
), 😳 t-t3 as (
sewect 
coawesce(t1.souwce_id, t-t2.souwce_id) as s-souwce_id,
coawesce(t1.destination_id, -.- t-t2.destination_id) a-as destination_id, 🥺
count(distinct(t1.datehouw)) as nyum_days, o.O
m-min(coawesce(num_tweets,0)) as nyum_tweets, /(^•ω•^) -- aww wows' n-nyum_tweets shouwd be the same
coawesce(date_diff(date_end, nyaa~~ date(max(t1.datehouw)), nyaa~~ day),30) as days_since_wast_intewaction, :3
count(distinct(wabew)) a-as wabew_types, 😳😳😳
countif(wabew="num_fowwows") a-as nyum_fowwows, (˘ω˘)
c-countif(wabew="num_favowites") a-as num_favowites, ^^
countif(wabew="num_tweet_cwicks") as nyum_tweet_cwicks, :3
countif(wabew="num_pwofiwe_views") a-as nyum_pwofiwe_views, -.-
f-fwom t1 
fuww join t2
using (souwce_id, 😳 destination_id)
gwoup b-by 1,2
owdew b-by 3 desc,4 desc
), mya t4 as (
  s-sewect wank() ovew (pawtition by s-souwce_id owdew by nyum_days desc, (˘ω˘) nyum_tweets d-desc) as wn, >_< *
  fwom t3
) sewect *, -.- d-date_end as ds fwom t4 whewe w-wn <= 2000

