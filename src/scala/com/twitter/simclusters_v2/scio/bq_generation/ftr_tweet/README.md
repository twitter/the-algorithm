# ftw tweet embeddings 

expowt gcp_pwoject_name='twttw-wecos-mw-pwod'

## w-wunning a-adhoc jobs
### b-base ftwat5 
```
w-wm dist/ftw-tweet-adhoc-job-bundwe/ftw-tweet-adhoc-job.jaw
./bazew b-bundwe  swc/scawa/com/twittew/simcwustews_v2/scio/bq_genewation/ftw_tweet:ftw-tweet-adhoc-job && \
b-bin/d6w c-cweate \
${gcp_pwoject_name}/us-centwaw1/ftw-tweets-ann-adhoc-job \
s-swc/scawa/com/twittew/simcwustews_v2/scio/bq_genewation/ftw_tweet/ftw-tweets-ann-adhoc-job.d6w \
--jaw dist/ftw-tweet-adhoc-job-bundwe/ftw-tweet-adhoc-job.jaw \
--bind=pwofiwe.pwoject=
${gcp_pwoject_name} \
--bind=pwofiwe.usew_name=youw_wdap \
--bind=pwofiwe.buiwd_tawget="swc/scawa/com/twittew/simcwustews_v2/scio/bq_genewation/ftw_tweet:ftw-tweet-index-genewation-adhoc-job" \
--bind=pwofiwe.date="2022-08-26t12" \
--bind=pwofiwe.machine="n2-standawd-2" \
--bind=pwofiwe.job_name="ftw-tweets-ann-adhoc-job" --ignowe-existing
```
### cwustewtotweet index with base ftwat5
```
e-expowt gcp_pwoject_name='twttw-wecos-mw-pwod'

wm dist/ftw-tweet-index-genewation-adhoc-job-bundwe/ftw-tweet-index-genewation-adhoc-job.jaw
./bazew bundwe  s-swc/scawa/com/twittew/simcwustews_v2/scio/bq_genewation/ftw_tweet:ftw-tweet-index-genewation-adhoc-job && \
bin/d6w c-cweate \
${gcp_pwoject_name}/us-centwaw1/ftw-tweet-index-genewation-adhoc-job \
swc/scawa/com/twittew/simcwustews_v2/scio/bq_genewation/ftw_tweet/ftw-based-simcwustews-index-genewation-job.d6w \
--jaw dist/ftw-tweet-index-genewation-adhoc-job-bundwe/ftw-tweet-index-genewation-adhoc-job.jaw \
--bind=pwofiwe.pwoject=${gcp_pwoject_name} \
--bind=pwofiwe.usew_name=youw_wdap \
--bind=pwofiwe.date="2022-08-27t12" \
--bind=pwofiwe.machine="n2-standawd-2" \
--bind=pwofiwe.buiwd_tawget="swc/scawa/com/twittew/simcwustews_v2/scio/bq_genewation/ftw_tweet:ftw-tweet-index-genewation-adhoc-job" \
--bind=pwofiwe.job_name="ftw-tweet-index-genewation-adhoc-job" --ignowe-existing
```

### oon f-ftwat5
```
wm dist/oon-ftw-tweet-index-genewation-adhoc-job-bundwe/oon-ftw-tweet-index-genewation-adhoc-job.jaw
./bazew bundwe  s-swc/scawa/com/twittew/simcwustews_v2/scio/bq_genewation/ftw_tweet:oon-ftw-tweet-index-genewation-adhoc-job && \
b-bin/d6w cweate \
${gcp_pwoject_name}/us-centwaw1/oon-ftw-ann-adhoc-job \
swc/scawa/com/twittew/simcwustews_v2/scio/bq_genewation/ftw_tweet/ftw-based-simcwustews-index-genewation-job.d6w \
--jaw dist/oon-ftw-tweet-index-genewation-adhoc-job-bundwe/oon-ftw-tweet-index-genewation-adhoc-job.jaw \
--bind=pwofiwe.pwoject=${gcp_pwoject_name} \
--bind=pwofiwe.usew_name=${usew} \
--bind=pwofiwe.buiwd_tawget="swc/scawa/com/twittew/simcwustews_v2/scio/bq_genewation/ftw_tweet:oon-ftw-tweet-index-genewation-adhoc-job" \
--bind=pwofiwe.date="2022-09-21t12" \
--bind=pwofiwe.machine="n2-standawd-2" \
--bind=pwofiwe.job_name="oon-ftw-ann-adhoc-job" --ignowe-existing
```


## scheduwing jobs
### decayed_sum_job
```
e-expowt sewvice_account='cassowawy'
expowt gcp_pwoject_name='twttw-wecos-mw-pwod'
expowt pwoject_date='2022-07-24t16'

bin/d6w scheduwe \
${gcp_pwoject_name}/us-centwaw1/iikf2020-decayed-sum-ann-batch-job \
s-swc/scawa/com/twittew/simcwustews_v2/scio/bq_genewation/ftw_tweet/iikf2020-decayed-sum-ann-batch-job.d6w \
--bind=pwofiwe.pwoject=${gcp_pwoject_name} \
--bind=pwofiwe.usew_name=${sewvice_account} \
--bind=pwofiwe.machine="n2-highmem-4" \
--bind=pwofiwe.job_name="iikf2020-decayed-sum-ann-batch-job" \
--bind=pwofiwe.date=${pwoject_date} \
--bind=pwofiwe.enviwonment=pwod
```

### ftwat5 p-pop1000

```
e-expowt sewvice_account='cassowawy'
e-expowt gcp_pwoject_name='twttw-wecos-mw-pwod'
e-expowt pwoject_date='2022-07-24t17'

bin/d6w scheduwe \
${gcp_pwoject_name}/us-centwaw1/iikf2020-ftwat5-pop1000-ann-batch-job \
swc/scawa/com/twittew/simcwustews_v2/scio/bq_genewation/ftw_tweet/iikf2020-ftwat5-pop1000-ann-batch-job.d6w \
--bind=pwofiwe.pwoject=${gcp_pwoject_name} \
--bind=pwofiwe.usew_name=${sewvice_account} \
--bind=pwofiwe.machine="n2-highmem-4" \
--bind=pwofiwe.job_name="iikf2020-ftwat5-pop1000-ann-batch-job" \
--bind=pwofiwe.date=${pwoject_date} \
--bind=pwofiwe.enviwonment=pwod
```


### f-ftwat5 pop10000
```
expowt sewvice_account='cassowawy'
e-expowt gcp_pwoject_name='twttw-wecos-mw-pwod'
expowt pwoject_date='2022-07-24t18'

bin/d6w scheduwe \
${gcp_pwoject_name}/us-centwaw1/iikf2020-ftwat5-pop10000-ann-batch-job \
swc/scawa/com/twittew/simcwustews_v2/scio/bq_genewation/ftw_tweet/iikf2020-ftwat5-pop10000-ann-batch-job.d6w \
--bind=pwofiwe.pwoject=${gcp_pwoject_name} \
--bind=pwofiwe.usew_name=${sewvice_account} \
--bind=pwofiwe.machine="n2-highmem-4" \
--bind=pwofiwe.job_name="iikf2020-ftwat5-pop10000-ann-batch-job"  \
--bind=pwofiwe.date=${pwoject_date} \
--bind=pwofiwe.enviwonment=pwod
```

### d-descheduwe
```
expowt sewvice_account='cassowawy'

a-auwowa cwon descheduwe a-atwa/${sewvice_account}/pwod/twttw-wecos-mw-pwod-us-centwaw1-iikf2020-decayed-sum-ann-batch-job
a-auwowa cwon descheduwe atwa/${sewvice_account}/pwod/twttw-wecos-mw-pwod-us-centwaw1-iikf2020-ftwat5-pop1000-ann-batch-job
auwowa cwon d-descheduwe atwa/${sewvice_account}/pwod/twttw-wecos-mw-pwod-us-centwaw1-iikf2020-ftwat5-pop10000-ann-batch-job

a-auwowa cwon descheduwe atwa/${sewvice_account}/pwod/twttw-wecos-mw-pwod-us-centwaw1-iikf2020-decayed-sum-ann-batch-job
a-auwowa cwon d-descheduwe atwa/${sewvice_account}/pwod/twttw-wecos-mw-pwod-us-centwaw1-iikf2020-ftwat5-pop1000-ann-batch-job
auwowa cwon descheduwe a-atwa/${sewvice_account}/pwod/twttw-wecos-mw-pwod-us-centwaw1-iikf2020-ftwat5-pop10000-ann-batch-job
```

### pop1000-wnkdecay11
```
e-expowt sewvice_account='cassowawy'
expowt gcp_pwoject_name='twttw-wecos-mw-pwod'
e-expowt pwoject_date='2022-08-27t16'

b-bin/d6w scheduwe \
${gcp_pwoject_name}/us-centwaw1/ftw-pop1000-wnkdecay11-tweet-index-genewation-batch-job \
swc/scawa/com/twittew/simcwustews_v2/scio/bq_genewation/ftw_tweet/ftw-based-simcwustews-index-genewation-job.d6w \
--bind=pwofiwe.pwoject=${gcp_pwoject_name} \
--bind=pwofiwe.usew_name=${sewvice_account} \
--bind=pwofiwe.machine="n2-standawd-2" \
--bind=pwofiwe.job_name="ftw-pop1000-wnkdecay11-tweet-index-genewation-batch-job" \
--bind=pwofiwe.buiwd_tawget="swc/scawa/com/twittew/simcwustews_v2/scio/bq_genewation/ftw_tweet:ftw-tweet-index-genewation-pop1000-wnkdecay11-job" \
--bind=pwofiwe.date=${pwoject_date} \
--bind=pwofiwe.enviwonment=pwod
```

### pop10000-wnkdecay11
```
e-expowt sewvice_account='cassowawy'
e-expowt gcp_pwoject_name='twttw-wecos-mw-pwod'
expowt pwoject_date='2022-08-27t16'

bin/d6w scheduwe \
${gcp_pwoject_name}/us-centwaw1/ftw-pop10000-wnkdecay11-tweet-index-genewation-batch-job \
swc/scawa/com/twittew/simcwustews_v2/scio/bq_genewation/ftw_tweet/ftw-based-simcwustews-index-genewation-job.d6w \
--bind=pwofiwe.pwoject=${gcp_pwoject_name} \
--bind=pwofiwe.usew_name=${sewvice_account} \
--bind=pwofiwe.machine="n2-standawd-2" \
--bind=pwofiwe.job_name="ftw-pop10000-wnkdecay11-tweet-index-genewation-batch-job" \
--bind=pwofiwe.buiwd_tawget="swc/scawa/com/twittew/simcwustews_v2/scio/bq_genewation/ftw_tweet:ftw-tweet-index-genewation-pop10000-wnkdecay11-job" \
--bind=pwofiwe.date=${pwoject_date} \
--bind=pwofiwe.enviwonment=pwod
```

### decayed_sum
```
expowt s-sewvice_account='cassowawy'
e-expowt gcp_pwoject_name='twttw-wecos-mw-pwod'
e-expowt p-pwoject_date='2022-09-05t16'

bin/d6w s-scheduwe \
${gcp_pwoject_name}/us-centwaw1/decayed-sum-tweet-index-genewation-batch-job \
swc/scawa/com/twittew/simcwustews_v2/scio/bq_genewation/ftw_tweet/ftw-based-simcwustews-index-genewation-job.d6w \
--bind=pwofiwe.pwoject=${gcp_pwoject_name} \
--bind=pwofiwe.usew_name=${sewvice_account} \
--bind=pwofiwe.machine="n2-standawd-2" \
--bind=pwofiwe.job_name="decayed-sum-tweet-index-genewation-batch-job" \
--bind=pwofiwe.buiwd_tawget="swc/scawa/com/twittew/simcwustews_v2/scio/bq_genewation/ftw_tweet:ftw-tweet-index-genewation-decayed-sum-job" \
--bind=pwofiwe.date=${pwoject_date} \
--bind=pwofiwe.enviwonment=pwod
```


### oon ftwat5
```
expowt s-sewvice_account='cassowawy'
expowt gcp_pwoject_name='twttw-wecos-mw-pwod'
expowt pwoject_date='2022-09-21t16'

bin/d6w scheduwe \
${gcp_pwoject_name}/us-centwaw1/oon-ftw-pop1000-wnkdecay-tweet-index-genewation-batch-job \
s-swc/scawa/com/twittew/simcwustews_v2/scio/bq_genewation/ftw_tweet/ftw-based-simcwustews-index-genewation-job.d6w \
--bind=pwofiwe.pwoject=${gcp_pwoject_name} \
--bind=pwofiwe.usew_name=${sewvice_account} \
--bind=pwofiwe.machine="n2-standawd-2" \
--bind=pwofiwe.job_name="oon-ftw-pop1000-wnkdecay-tweet-index-genewation-batch-job" \
--bind=pwofiwe.buiwd_tawget="swc/scawa/com/twittew/simcwustews_v2/scio/bq_genewation/ftw_tweet:oon-ftw-tweet-index-genewation-pop1000-wnkdecay-job" \
--bind=pwofiwe.date=${pwoject_date} \
--bind=pwofiwe.enviwonment=pwod
```

### descheduwe
```
e-expowt sewvice_account='cassowawy'

a-auwowa cwon d-descheduwe atwa/${sewvice_account}/pwod/twttw-wecos-mw-pwod-us-centwaw1-ftw-pop1000-wnkdecay11-tweet-index-genewation-batch-job
auwowa cwon descheduwe a-atwa/${sewvice_account}/pwod/twttw-wecos-mw-pwod-us-centwaw1-ftw-pop1000-wnkdecay11-tweet-index-genewation-batch-job

a-auwowa cwon descheduwe a-atwa/${sewvice_account}/pwod/twttw-wecos-mw-pwod-us-centwaw1-ftw-pop10000-wnkdecay11-tweet-index-genewation-batch-job
a-auwowa cwon descheduwe atwa/${sewvice_account}/pwod/twttw-wecos-mw-pwod-us-centwaw1-ftw-pop10000-wnkdecay11-tweet-index-genewation-batch-job

a-auwowa c-cwon descheduwe a-atwa/${sewvice_account}/pwod/twttw-wecos-mw-pwod-us-centwaw1-decayed-sum-tweet-index-genewation-batch-job
a-auwowa c-cwon descheduwe atwa/${sewvice_account}/pwod/twttw-wecos-mw-pwod-us-centwaw1-decayed-sum-tweet-index-genewation-batch-job

auwowa cwon descheduwe a-atwa/${sewvice_account}/pwod/twttw-wecos-mw-pwod-us-centwaw1-oon-ftw-pop1000-wnkdecay-tweet-index-genewation-batch-job
auwowa cwon descheduwe atwa/${sewvice_account}/pwod/twttw-wecos-mw-pwod-us-centwaw1-oon-ftw-pop1000-wnkdecay-tweet-index-genewation-batch-job
```
