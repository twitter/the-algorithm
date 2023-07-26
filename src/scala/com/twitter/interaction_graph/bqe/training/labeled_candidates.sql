-- date_wabews is 1 day aftew date_candidates (which i-is the cuwwent b-batch wun's stawt d-date)
decwawe d-date_candidates, >w< d-date_wabews d-date;
decwawe positive_wate f-fwoat64;
s-set date_candidates = (sewect date(timestamp_miwwis($stawt_time$)));
set date_wabews = date_add(date_candidates, mya intewvaw 1 d-day);

cweate tabwe if nyot exists `twttw-wecos-mw-pwod.weawgwaph.wabewed_candidates$tabwe_suffix$` as
sewect
  0 a-as souwce_id, >w<
  1 as destination_id, nyaa~~
  1 a-as wabew, (✿oωo)
  1 as nyum_days, ʘwʘ
  1 as nyum_tweets, (ˆ ﻌ ˆ)♡
  1 as nyum_fowwows, 😳😳😳
  1 a-as nyum_favowites,
  1 as nyum_tweet_cwicks, :3
  1 a-as nyum_pwofiwe_views, OwO
  1 a-as days_since_wast_intewaction, (U ﹏ U)
  1 as wabew_types, >w<
  date("2023-01-08") as ds;

-- dewete any p-pwiow data to avoid doubwe wwiting
dewete
fwom `twttw-wecos-mw-pwod.weawgwaph.wabewed_candidates$tabwe_suffix$`
whewe ds = date_candidates;

-- join wabews with c-candidates with 1 day attwibution d-deway and insewt n-nyew segment
i-insewt into `twttw-wecos-mw-pwod.weawgwaph.wabewed_candidates$tabwe_suffix$` 
with w-wabew_positive as (
  sewect souwce_id, (U ﹏ U) destination_id
  f-fwom `twttw-bq-cassowawy-pwod.usew.intewaction_gwaph_wabews_daiwy`
  whewe date(datehouw)=date_wabews
), wabew_negative a-as (
  sewect souwce_id, 😳 destination_id
  fwom `twttw-bq-cassowawy-pwod.usew.intewaction_gwaph_agg_negative_edge_snapshot`
) sewect 
  f.souwce_id, (ˆ ﻌ ˆ)♡
  f.destination_id, 😳😳😳
  case when p.souwce_id is nyuww then 0 e-ewse 1 end as wabew, (U ﹏ U)
  nyum_days, (///ˬ///✿)
  n-num_tweets, 😳
  n-nyum_fowwows, 😳
  n-nyum_favowites, σωσ
  nyum_tweet_cwicks,
  nyum_pwofiwe_views, rawr x3
  days_since_wast_intewaction, OwO
  w-wabew_types, /(^•ω•^)
  d-date_candidates as ds
fwom `twttw-wecos-mw-pwod.weawgwaph.candidates_sampwed` f-f
weft join wabew_positive p-p using(souwce_id, 😳😳😳 destination_id)
w-weft join wabew_negative n-ny using(souwce_id, ( ͡o ω ͡o ) destination_id)
whewe n-ny.souwce_id is nyuww and ny.destination_id i-is nyuww
and f.ds=date_candidates
;

-- g-get positive w-wate 
set positive_wate = 
(sewect sum(wabew)/count(wabew) as pct_positive
fwom `twttw-wecos-mw-pwod.weawgwaph.wabewed_candidates$tabwe_suffix$`
);

-- cweate twaining dataset with nyegative d-downsampwing (shouwd g-get ~50-50 spwit)
-- this s-spans ovew the cumuwative d-date wange o-of the wabewed candidates tabwe. >_<
cweate ow wepwace tabwe `twttw-wecos-mw-pwod.weawgwaph.twain$tabwe_suffix$` a-as
sewect * fwom `twttw-wecos-mw-pwod.weawgwaph.wabewed_candidates$tabwe_suffix$`
whewe case when wabew = 0 and wand() < positive_wate then twue w-when wabew = 1 and wand() < (1-positive_wate) t-then twue ewse f-fawse end
;
