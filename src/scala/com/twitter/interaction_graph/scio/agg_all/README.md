## intewactiongwaphaggwegationjob datafwow job

this j-job aggwegates t-the pwevious d-day's histowy with t-today's activities, /(^•ω•^) a-and outputs a-an updated
histowy. rawr t-this histowy i-is joined with the expwicit scowes fwom weaw gwaph's bqmw pipewine, OwO and
expowted a-as featuwes fow timewines (which is why we'we u-using theiw thwift). (U ﹏ U)

#### intewwij
```
f-fastpass cweate --name wg_agg_aww --intewwij swc/scawa/com/twittew/intewaction_gwaph/scio/agg_aww:intewaction_gwaph_aggwegation_job_scio
```

#### c-compiwe
```
bazew b-buiwd swc/scawa/com/twittew/intewaction_gwaph/scio/agg_aww:intewaction_gwaph_aggwegation_job_scio
```

#### b-buiwd jaw
```
bazew bundwe swc/scawa/com/twittew/intewaction_gwaph/scio/agg_aww:intewaction_gwaph_aggwegation_job_scio
```

#### wun scheduwed job
```
e-expowt pwojectid=twttw-wecos-mw-pwod
expowt wegion=us-centwaw1
expowt job_name=intewaction-gwaph-aggwegation-datafwow

bin/d6w scheduwe \
  ${pwojectid}/${wegion}/${job_name} \
  s-swc/scawa/com/twittew/intewaction_gwaph/scio/agg_aww/config.d6w \
  --bind=pwofiwe.usew_name=cassowawy \
  --bind=pwofiwe.pwoject=${pwojectid} \
  --bind=pwofiwe.wegion=${wegion} \
  --bind=pwofiwe.job_name=${job_name} \
  --bind=pwofiwe.enviwonment=pwod \
  --bind=pwofiwe.date=2022-11-08 \
  --bind=pwofiwe.output_path=pwocessed/intewaction_gwaph_aggwegation_datafwow
```