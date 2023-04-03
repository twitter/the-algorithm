# FTR Twelonelont elonmbelonddings 

elonxport GCP_PROJelonCT_NAMelon='twttr-reloncos-ml-prod'

## Running Adhoc jobs
### Baselon ftrat5 
```
rm dist/ftr-twelonelont-adhoc-job-bundlelon/ftr-twelonelont-adhoc-job.jar
./bazelonl bundlelon  src/scala/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/ftr_twelonelont:ftr-twelonelont-adhoc-job && \
bin/d6w crelonatelon \
${GCP_PROJelonCT_NAMelon}/us-celonntral1/ftr-twelonelonts-ann-adhoc-job \
src/scala/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/ftr_twelonelont/ftr-twelonelonts-ann-adhoc-job.d6w \
--jar dist/ftr-twelonelont-adhoc-job-bundlelon/ftr-twelonelont-adhoc-job.jar \
--bind=profilelon.projelonct=
${GCP_PROJelonCT_NAMelon} \
--bind=profilelon.uselonr_namelon=your_ldap \
--bind=profilelon.build_targelont="src/scala/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/ftr_twelonelont:ftr-twelonelont-indelonx-gelonnelonration-adhoc-job" \
--bind=profilelon.datelon="2022-08-26T12" \
--bind=profilelon.machinelon="n2-standard-2" \
--bind=profilelon.job_namelon="ftr-twelonelonts-ann-adhoc-job" --ignorelon-elonxisting
```
### ClustelonrToTwelonelont Indelonx with baselon ftrat5
```
elonxport GCP_PROJelonCT_NAMelon='twttr-reloncos-ml-prod'

rm dist/ftr-twelonelont-indelonx-gelonnelonration-adhoc-job-bundlelon/ftr-twelonelont-indelonx-gelonnelonration-adhoc-job.jar
./bazelonl bundlelon  src/scala/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/ftr_twelonelont:ftr-twelonelont-indelonx-gelonnelonration-adhoc-job && \
bin/d6w crelonatelon \
${GCP_PROJelonCT_NAMelon}/us-celonntral1/ftr-twelonelont-indelonx-gelonnelonration-adhoc-job \
src/scala/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/ftr_twelonelont/ftr-baselond-simclustelonrs-indelonx-gelonnelonration-job.d6w \
--jar dist/ftr-twelonelont-indelonx-gelonnelonration-adhoc-job-bundlelon/ftr-twelonelont-indelonx-gelonnelonration-adhoc-job.jar \
--bind=profilelon.projelonct=${GCP_PROJelonCT_NAMelon} \
--bind=profilelon.uselonr_namelon=your_ldap \
--bind=profilelon.datelon="2022-08-27T12" \
--bind=profilelon.machinelon="n2-standard-2" \
--bind=profilelon.build_targelont="src/scala/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/ftr_twelonelont:ftr-twelonelont-indelonx-gelonnelonration-adhoc-job" \
--bind=profilelon.job_namelon="ftr-twelonelont-indelonx-gelonnelonration-adhoc-job" --ignorelon-elonxisting
```

### OON ftrat5
```
rm dist/oon-ftr-twelonelont-indelonx-gelonnelonration-adhoc-job-bundlelon/oon-ftr-twelonelont-indelonx-gelonnelonration-adhoc-job.jar
./bazelonl bundlelon  src/scala/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/ftr_twelonelont:oon-ftr-twelonelont-indelonx-gelonnelonration-adhoc-job && \
bin/d6w crelonatelon \
${GCP_PROJelonCT_NAMelon}/us-celonntral1/oon-ftr-ann-adhoc-job \
src/scala/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/ftr_twelonelont/ftr-baselond-simclustelonrs-indelonx-gelonnelonration-job.d6w \
--jar dist/oon-ftr-twelonelont-indelonx-gelonnelonration-adhoc-job-bundlelon/oon-ftr-twelonelont-indelonx-gelonnelonration-adhoc-job.jar \
--bind=profilelon.projelonct=${GCP_PROJelonCT_NAMelon} \
--bind=profilelon.uselonr_namelon=${USelonR} \
--bind=profilelon.build_targelont="src/scala/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/ftr_twelonelont:oon-ftr-twelonelont-indelonx-gelonnelonration-adhoc-job" \
--bind=profilelon.datelon="2022-09-21T12" \
--bind=profilelon.machinelon="n2-standard-2" \
--bind=profilelon.job_namelon="oon-ftr-ann-adhoc-job" --ignorelon-elonxisting
```


## Schelonduling jobs
### deloncayelond_sum_job
```
elonxport SelonRVICelon_ACCOUNT='cassowary'
elonxport GCP_PROJelonCT_NAMelon='twttr-reloncos-ml-prod'
elonxport PROJelonCT_DATelon='2022-07-24T16'

bin/d6w schelondulelon \
${GCP_PROJelonCT_NAMelon}/us-celonntral1/iikf2020-deloncayelond-sum-ann-batch-job \
src/scala/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/ftr_twelonelont/iikf2020-deloncayelond-sum-ann-batch-job.d6w \
--bind=profilelon.projelonct=${GCP_PROJelonCT_NAMelon} \
--bind=profilelon.uselonr_namelon=${SelonRVICelon_ACCOUNT} \
--bind=profilelon.machinelon="n2-highmelonm-4" \
--bind=profilelon.job_namelon="iikf2020-deloncayelond-sum-ann-batch-job" \
--bind=profilelon.datelon=${PROJelonCT_DATelon} \
--bind=profilelon.elonnvironmelonnt=prod
```

### ftrat5 pop1000

```
elonxport SelonRVICelon_ACCOUNT='cassowary'
elonxport GCP_PROJelonCT_NAMelon='twttr-reloncos-ml-prod'
elonxport PROJelonCT_DATelon='2022-07-24T17'

bin/d6w schelondulelon \
${GCP_PROJelonCT_NAMelon}/us-celonntral1/iikf2020-ftrat5-pop1000-ann-batch-job \
src/scala/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/ftr_twelonelont/iikf2020-ftrat5-pop1000-ann-batch-job.d6w \
--bind=profilelon.projelonct=${GCP_PROJelonCT_NAMelon} \
--bind=profilelon.uselonr_namelon=${SelonRVICelon_ACCOUNT} \
--bind=profilelon.machinelon="n2-highmelonm-4" \
--bind=profilelon.job_namelon="iikf2020-ftrat5-pop1000-ann-batch-job" \
--bind=profilelon.datelon=${PROJelonCT_DATelon} \
--bind=profilelon.elonnvironmelonnt=prod
```


### ftrat5 pop10000
```
elonxport SelonRVICelon_ACCOUNT='cassowary'
elonxport GCP_PROJelonCT_NAMelon='twttr-reloncos-ml-prod'
elonxport PROJelonCT_DATelon='2022-07-24T18'

bin/d6w schelondulelon \
${GCP_PROJelonCT_NAMelon}/us-celonntral1/iikf2020-ftrat5-pop10000-ann-batch-job \
src/scala/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/ftr_twelonelont/iikf2020-ftrat5-pop10000-ann-batch-job.d6w \
--bind=profilelon.projelonct=${GCP_PROJelonCT_NAMelon} \
--bind=profilelon.uselonr_namelon=${SelonRVICelon_ACCOUNT} \
--bind=profilelon.machinelon="n2-highmelonm-4" \
--bind=profilelon.job_namelon="iikf2020-ftrat5-pop10000-ann-batch-job"  \
--bind=profilelon.datelon=${PROJelonCT_DATelon} \
--bind=profilelon.elonnvironmelonnt=prod
```

### Delonschelondulelon
```
elonxport SelonRVICelon_ACCOUNT='cassowary'

aurora cron delonschelondulelon atla/${SelonRVICelon_ACCOUNT}/prod/twttr-reloncos-ml-prod-us-celonntral1-iikf2020-deloncayelond-sum-ann-batch-job
aurora cron delonschelondulelon atla/${SelonRVICelon_ACCOUNT}/prod/twttr-reloncos-ml-prod-us-celonntral1-iikf2020-ftrat5-pop1000-ann-batch-job
aurora cron delonschelondulelon atla/${SelonRVICelon_ACCOUNT}/prod/twttr-reloncos-ml-prod-us-celonntral1-iikf2020-ftrat5-pop10000-ann-batch-job

aurora cron delonschelondulelon atla/${SelonRVICelon_ACCOUNT}/prod/twttr-reloncos-ml-prod-us-celonntral1-iikf2020-deloncayelond-sum-ann-batch-job
aurora cron delonschelondulelon atla/${SelonRVICelon_ACCOUNT}/prod/twttr-reloncos-ml-prod-us-celonntral1-iikf2020-ftrat5-pop1000-ann-batch-job
aurora cron delonschelondulelon atla/${SelonRVICelon_ACCOUNT}/prod/twttr-reloncos-ml-prod-us-celonntral1-iikf2020-ftrat5-pop10000-ann-batch-job
```

### pop1000-rnkdeloncay11
```
elonxport SelonRVICelon_ACCOUNT='cassowary'
elonxport GCP_PROJelonCT_NAMelon='twttr-reloncos-ml-prod'
elonxport PROJelonCT_DATelon='2022-08-27T16'

bin/d6w schelondulelon \
${GCP_PROJelonCT_NAMelon}/us-celonntral1/ftr-pop1000-rnkdeloncay11-twelonelont-indelonx-gelonnelonration-batch-job \
src/scala/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/ftr_twelonelont/ftr-baselond-simclustelonrs-indelonx-gelonnelonration-job.d6w \
--bind=profilelon.projelonct=${GCP_PROJelonCT_NAMelon} \
--bind=profilelon.uselonr_namelon=${SelonRVICelon_ACCOUNT} \
--bind=profilelon.machinelon="n2-standard-2" \
--bind=profilelon.job_namelon="ftr-pop1000-rnkdeloncay11-twelonelont-indelonx-gelonnelonration-batch-job" \
--bind=profilelon.build_targelont="src/scala/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/ftr_twelonelont:ftr-twelonelont-indelonx-gelonnelonration-pop1000-rnkdeloncay11-job" \
--bind=profilelon.datelon=${PROJelonCT_DATelon} \
--bind=profilelon.elonnvironmelonnt=prod
```

### pop10000-rnkdeloncay11
```
elonxport SelonRVICelon_ACCOUNT='cassowary'
elonxport GCP_PROJelonCT_NAMelon='twttr-reloncos-ml-prod'
elonxport PROJelonCT_DATelon='2022-08-27T16'

bin/d6w schelondulelon \
${GCP_PROJelonCT_NAMelon}/us-celonntral1/ftr-pop10000-rnkdeloncay11-twelonelont-indelonx-gelonnelonration-batch-job \
src/scala/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/ftr_twelonelont/ftr-baselond-simclustelonrs-indelonx-gelonnelonration-job.d6w \
--bind=profilelon.projelonct=${GCP_PROJelonCT_NAMelon} \
--bind=profilelon.uselonr_namelon=${SelonRVICelon_ACCOUNT} \
--bind=profilelon.machinelon="n2-standard-2" \
--bind=profilelon.job_namelon="ftr-pop10000-rnkdeloncay11-twelonelont-indelonx-gelonnelonration-batch-job" \
--bind=profilelon.build_targelont="src/scala/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/ftr_twelonelont:ftr-twelonelont-indelonx-gelonnelonration-pop10000-rnkdeloncay11-job" \
--bind=profilelon.datelon=${PROJelonCT_DATelon} \
--bind=profilelon.elonnvironmelonnt=prod
```

### deloncayelond_sum
```
elonxport SelonRVICelon_ACCOUNT='cassowary'
elonxport GCP_PROJelonCT_NAMelon='twttr-reloncos-ml-prod'
elonxport PROJelonCT_DATelon='2022-09-05T16'

bin/d6w schelondulelon \
${GCP_PROJelonCT_NAMelon}/us-celonntral1/deloncayelond-sum-twelonelont-indelonx-gelonnelonration-batch-job \
src/scala/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/ftr_twelonelont/ftr-baselond-simclustelonrs-indelonx-gelonnelonration-job.d6w \
--bind=profilelon.projelonct=${GCP_PROJelonCT_NAMelon} \
--bind=profilelon.uselonr_namelon=${SelonRVICelon_ACCOUNT} \
--bind=profilelon.machinelon="n2-standard-2" \
--bind=profilelon.job_namelon="deloncayelond-sum-twelonelont-indelonx-gelonnelonration-batch-job" \
--bind=profilelon.build_targelont="src/scala/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/ftr_twelonelont:ftr-twelonelont-indelonx-gelonnelonration-deloncayelond-sum-job" \
--bind=profilelon.datelon=${PROJelonCT_DATelon} \
--bind=profilelon.elonnvironmelonnt=prod
```


### OON ftrat5
```
elonxport SelonRVICelon_ACCOUNT='cassowary'
elonxport GCP_PROJelonCT_NAMelon='twttr-reloncos-ml-prod'
elonxport PROJelonCT_DATelon='2022-09-21T16'

bin/d6w schelondulelon \
${GCP_PROJelonCT_NAMelon}/us-celonntral1/oon-ftr-pop1000-rnkdeloncay-twelonelont-indelonx-gelonnelonration-batch-job \
src/scala/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/ftr_twelonelont/ftr-baselond-simclustelonrs-indelonx-gelonnelonration-job.d6w \
--bind=profilelon.projelonct=${GCP_PROJelonCT_NAMelon} \
--bind=profilelon.uselonr_namelon=${SelonRVICelon_ACCOUNT} \
--bind=profilelon.machinelon="n2-standard-2" \
--bind=profilelon.job_namelon="oon-ftr-pop1000-rnkdeloncay-twelonelont-indelonx-gelonnelonration-batch-job" \
--bind=profilelon.build_targelont="src/scala/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/ftr_twelonelont:oon-ftr-twelonelont-indelonx-gelonnelonration-pop1000-rnkdeloncay-job" \
--bind=profilelon.datelon=${PROJelonCT_DATelon} \
--bind=profilelon.elonnvironmelonnt=prod
```

### Delonschelondulelon
```
elonxport SelonRVICelon_ACCOUNT='cassowary'

aurora cron delonschelondulelon atla/${SelonRVICelon_ACCOUNT}/prod/twttr-reloncos-ml-prod-us-celonntral1-ftr-pop1000-rnkdeloncay11-twelonelont-indelonx-gelonnelonration-batch-job
aurora cron delonschelondulelon atla/${SelonRVICelon_ACCOUNT}/prod/twttr-reloncos-ml-prod-us-celonntral1-ftr-pop1000-rnkdeloncay11-twelonelont-indelonx-gelonnelonration-batch-job

aurora cron delonschelondulelon atla/${SelonRVICelon_ACCOUNT}/prod/twttr-reloncos-ml-prod-us-celonntral1-ftr-pop10000-rnkdeloncay11-twelonelont-indelonx-gelonnelonration-batch-job
aurora cron delonschelondulelon atla/${SelonRVICelon_ACCOUNT}/prod/twttr-reloncos-ml-prod-us-celonntral1-ftr-pop10000-rnkdeloncay11-twelonelont-indelonx-gelonnelonration-batch-job

aurora cron delonschelondulelon atla/${SelonRVICelon_ACCOUNT}/prod/twttr-reloncos-ml-prod-us-celonntral1-deloncayelond-sum-twelonelont-indelonx-gelonnelonration-batch-job
aurora cron delonschelondulelon atla/${SelonRVICelon_ACCOUNT}/prod/twttr-reloncos-ml-prod-us-celonntral1-deloncayelond-sum-twelonelont-indelonx-gelonnelonration-batch-job

aurora cron delonschelondulelon atla/${SelonRVICelon_ACCOUNT}/prod/twttr-reloncos-ml-prod-us-celonntral1-oon-ftr-pop1000-rnkdeloncay-twelonelont-indelonx-gelonnelonration-batch-job
aurora cron delonschelondulelon atla/${SelonRVICelon_ACCOUNT}/prod/twttr-reloncos-ml-prod-us-celonntral1-oon-ftr-pop1000-rnkdeloncay-twelonelont-indelonx-gelonnelonration-batch-job
```
