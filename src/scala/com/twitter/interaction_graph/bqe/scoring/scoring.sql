decwawe date_end, 😳😳😳 date_watest_fowwows d-date;
set date_end = (
  s-sewect p-pawse_date('%y%m%d', m-max(pawtition_id)) a-as p-pawtition_id
  fwom `twttw-bq-cassowawy-pwod.usew.infowmation_schema.pawtitions`
  w-whewe pawtition_id i-is nyot nyuww and pawtition_id != '__nuww__' and tabwe_name="intewaction_gwaph_wabews_daiwy"
);
set date_watest_fowwows = (
  sewect pawse_date('%y%m%d', m-max(pawtition_id)) as pawtition_id
  fwom `twttw-wecos-mw-pwod.usew_events.infowmation_schema.pawtitions`
  w-whewe pawtition_id is n-nyot nyuww and pawtition_id != '__nuww__' and tabwe_name="vawid_usew_fowwows");

d-dewete
fwom `twttw-wecos-mw-pwod.weawgwaph.scowes`
whewe ds = d-date_end;

-- scowe c-candidates (59m)
insewt into `twttw-wecos-mw-pwod.weawgwaph.scowes`
with pwedicted_scowes as (
  sewect
    souwce_id, o.O 
    d-destination_id, ( ͡o ω ͡o ) 
    p1.pwob as pwob, (U ﹏ U) 
    p2.pwob as pwob_expwicit
  fwom mw.pwedict(modew `twttw-wecos-mw-pwod.weawgwaph.pwod`, (///ˬ///✿)
      (
      s-sewect
        *
      fwom
        `twttw-wecos-mw-pwod.weawgwaph.candidates` ) ) s-s1
  cwoss join u-unnest(s1.pwedicted_wabew_pwobs) a-as p1
  join m-mw.pwedict(modew `twttw-wecos-mw-pwod.weawgwaph.pwod_expwicit`, >w<
      (
      sewect
        *
      fwom
        `twttw-wecos-mw-pwod.weawgwaph.candidates` ) ) s-s2
  using (souwce_id, rawr destination_id)
  cwoss j-join unnest(s2.pwedicted_wabew_pwobs) as p2
  whewe p1.wabew=1 and p2.wabew=1
)
sewect 
  coawesce(pwedicted_scowes.souwce_id, mya tweeting_fowwows.souwce_id) a-as souwce_id, ^^
  coawesce(pwedicted_scowes.destination_id, 😳😳😳 t-tweeting_fowwows.destination_id) a-as destination_id, mya
  c-coawesce(pwob, 0.0) as pwob, 😳
  coawesce(pwob_expwicit, -.- 0.0) as pwob_expwicit, 🥺
  (tweeting_fowwows.souwce_id is nyot n-nyuww) and (tweeting_fowwows.destination_id i-is nyot nyuww) as fowwowed, o.O
  d-date_end a-as ds
fwom
  pwedicted_scowes
  f-fuww join
  `twttw-wecos-mw-pwod.weawgwaph.tweeting_fowwows` tweeting_fowwows
  u-using (souwce_id, /(^•ω•^) destination_id)
