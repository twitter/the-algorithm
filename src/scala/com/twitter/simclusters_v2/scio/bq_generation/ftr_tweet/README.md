# FTR T et embedd ngs 

export GCP_PROJECT_NAME='twttr-recos-ml-prod'

## Runn ng Adhoc jobs
### Base ftrat5 
```
rm d st/ftr-t et-adhoc-job-bundle/ftr-t et-adhoc-job.jar
./bazel bundle  src/scala/com/tw ter/s mclusters_v2/sc o/bq_generat on/ftr_t et:ftr-t et-adhoc-job && \
b n/d6w create \
${GCP_PROJECT_NAME}/us-central1/ftr-t ets-ann-adhoc-job \
src/scala/com/tw ter/s mclusters_v2/sc o/bq_generat on/ftr_t et/ftr-t ets-ann-adhoc-job.d6w \
--jar d st/ftr-t et-adhoc-job-bundle/ftr-t et-adhoc-job.jar \
--b nd=prof le.project=
${GCP_PROJECT_NAME} \
--b nd=prof le.user_na =y _ldap \
--b nd=prof le.bu ld_target="src/scala/com/tw ter/s mclusters_v2/sc o/bq_generat on/ftr_t et:ftr-t et- ndex-generat on-adhoc-job" \
--b nd=prof le.date="2022-08-26T12" \
--b nd=prof le.mach ne="n2-standard-2" \
--b nd=prof le.job_na ="ftr-t ets-ann-adhoc-job" -- gnore-ex st ng
```
### ClusterToT et  ndex w h base ftrat5
```
export GCP_PROJECT_NAME='twttr-recos-ml-prod'

rm d st/ftr-t et- ndex-generat on-adhoc-job-bundle/ftr-t et- ndex-generat on-adhoc-job.jar
./bazel bundle  src/scala/com/tw ter/s mclusters_v2/sc o/bq_generat on/ftr_t et:ftr-t et- ndex-generat on-adhoc-job && \
b n/d6w create \
${GCP_PROJECT_NAME}/us-central1/ftr-t et- ndex-generat on-adhoc-job \
src/scala/com/tw ter/s mclusters_v2/sc o/bq_generat on/ftr_t et/ftr-based-s mclusters- ndex-generat on-job.d6w \
--jar d st/ftr-t et- ndex-generat on-adhoc-job-bundle/ftr-t et- ndex-generat on-adhoc-job.jar \
--b nd=prof le.project=${GCP_PROJECT_NAME} \
--b nd=prof le.user_na =y _ldap \
--b nd=prof le.date="2022-08-27T12" \
--b nd=prof le.mach ne="n2-standard-2" \
--b nd=prof le.bu ld_target="src/scala/com/tw ter/s mclusters_v2/sc o/bq_generat on/ftr_t et:ftr-t et- ndex-generat on-adhoc-job" \
--b nd=prof le.job_na ="ftr-t et- ndex-generat on-adhoc-job" -- gnore-ex st ng
```

### OON ftrat5
```
rm d st/oon-ftr-t et- ndex-generat on-adhoc-job-bundle/oon-ftr-t et- ndex-generat on-adhoc-job.jar
./bazel bundle  src/scala/com/tw ter/s mclusters_v2/sc o/bq_generat on/ftr_t et:oon-ftr-t et- ndex-generat on-adhoc-job && \
b n/d6w create \
${GCP_PROJECT_NAME}/us-central1/oon-ftr-ann-adhoc-job \
src/scala/com/tw ter/s mclusters_v2/sc o/bq_generat on/ftr_t et/ftr-based-s mclusters- ndex-generat on-job.d6w \
--jar d st/oon-ftr-t et- ndex-generat on-adhoc-job-bundle/oon-ftr-t et- ndex-generat on-adhoc-job.jar \
--b nd=prof le.project=${GCP_PROJECT_NAME} \
--b nd=prof le.user_na =${USER} \
--b nd=prof le.bu ld_target="src/scala/com/tw ter/s mclusters_v2/sc o/bq_generat on/ftr_t et:oon-ftr-t et- ndex-generat on-adhoc-job" \
--b nd=prof le.date="2022-09-21T12" \
--b nd=prof le.mach ne="n2-standard-2" \
--b nd=prof le.job_na ="oon-ftr-ann-adhoc-job" -- gnore-ex st ng
```


## Sc dul ng jobs
### decayed_sum_job
```
export SERV CE_ACCOUNT='cassowary'
export GCP_PROJECT_NAME='twttr-recos-ml-prod'
export PROJECT_DATE='2022-07-24T16'

b n/d6w sc dule \
${GCP_PROJECT_NAME}/us-central1/  kf2020-decayed-sum-ann-batch-job \
src/scala/com/tw ter/s mclusters_v2/sc o/bq_generat on/ftr_t et/  kf2020-decayed-sum-ann-batch-job.d6w \
--b nd=prof le.project=${GCP_PROJECT_NAME} \
--b nd=prof le.user_na =${SERV CE_ACCOUNT} \
--b nd=prof le.mach ne="n2-h gh m-4" \
--b nd=prof le.job_na ="  kf2020-decayed-sum-ann-batch-job" \
--b nd=prof le.date=${PROJECT_DATE} \
--b nd=prof le.env ron nt=prod
```

### ftrat5 pop1000

```
export SERV CE_ACCOUNT='cassowary'
export GCP_PROJECT_NAME='twttr-recos-ml-prod'
export PROJECT_DATE='2022-07-24T17'

b n/d6w sc dule \
${GCP_PROJECT_NAME}/us-central1/  kf2020-ftrat5-pop1000-ann-batch-job \
src/scala/com/tw ter/s mclusters_v2/sc o/bq_generat on/ftr_t et/  kf2020-ftrat5-pop1000-ann-batch-job.d6w \
--b nd=prof le.project=${GCP_PROJECT_NAME} \
--b nd=prof le.user_na =${SERV CE_ACCOUNT} \
--b nd=prof le.mach ne="n2-h gh m-4" \
--b nd=prof le.job_na ="  kf2020-ftrat5-pop1000-ann-batch-job" \
--b nd=prof le.date=${PROJECT_DATE} \
--b nd=prof le.env ron nt=prod
```


### ftrat5 pop10000
```
export SERV CE_ACCOUNT='cassowary'
export GCP_PROJECT_NAME='twttr-recos-ml-prod'
export PROJECT_DATE='2022-07-24T18'

b n/d6w sc dule \
${GCP_PROJECT_NAME}/us-central1/  kf2020-ftrat5-pop10000-ann-batch-job \
src/scala/com/tw ter/s mclusters_v2/sc o/bq_generat on/ftr_t et/  kf2020-ftrat5-pop10000-ann-batch-job.d6w \
--b nd=prof le.project=${GCP_PROJECT_NAME} \
--b nd=prof le.user_na =${SERV CE_ACCOUNT} \
--b nd=prof le.mach ne="n2-h gh m-4" \
--b nd=prof le.job_na ="  kf2020-ftrat5-pop10000-ann-batch-job"  \
--b nd=prof le.date=${PROJECT_DATE} \
--b nd=prof le.env ron nt=prod
```

### Desc dule
```
export SERV CE_ACCOUNT='cassowary'

aurora cron desc dule atla/${SERV CE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central1-  kf2020-decayed-sum-ann-batch-job
aurora cron desc dule atla/${SERV CE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central1-  kf2020-ftrat5-pop1000-ann-batch-job
aurora cron desc dule atla/${SERV CE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central1-  kf2020-ftrat5-pop10000-ann-batch-job

aurora cron desc dule atla/${SERV CE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central1-  kf2020-decayed-sum-ann-batch-job
aurora cron desc dule atla/${SERV CE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central1-  kf2020-ftrat5-pop1000-ann-batch-job
aurora cron desc dule atla/${SERV CE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central1-  kf2020-ftrat5-pop10000-ann-batch-job
```

### pop1000-rnkdecay11
```
export SERV CE_ACCOUNT='cassowary'
export GCP_PROJECT_NAME='twttr-recos-ml-prod'
export PROJECT_DATE='2022-08-27T16'

b n/d6w sc dule \
${GCP_PROJECT_NAME}/us-central1/ftr-pop1000-rnkdecay11-t et- ndex-generat on-batch-job \
src/scala/com/tw ter/s mclusters_v2/sc o/bq_generat on/ftr_t et/ftr-based-s mclusters- ndex-generat on-job.d6w \
--b nd=prof le.project=${GCP_PROJECT_NAME} \
--b nd=prof le.user_na =${SERV CE_ACCOUNT} \
--b nd=prof le.mach ne="n2-standard-2" \
--b nd=prof le.job_na ="ftr-pop1000-rnkdecay11-t et- ndex-generat on-batch-job" \
--b nd=prof le.bu ld_target="src/scala/com/tw ter/s mclusters_v2/sc o/bq_generat on/ftr_t et:ftr-t et- ndex-generat on-pop1000-rnkdecay11-job" \
--b nd=prof le.date=${PROJECT_DATE} \
--b nd=prof le.env ron nt=prod
```

### pop10000-rnkdecay11
```
export SERV CE_ACCOUNT='cassowary'
export GCP_PROJECT_NAME='twttr-recos-ml-prod'
export PROJECT_DATE='2022-08-27T16'

b n/d6w sc dule \
${GCP_PROJECT_NAME}/us-central1/ftr-pop10000-rnkdecay11-t et- ndex-generat on-batch-job \
src/scala/com/tw ter/s mclusters_v2/sc o/bq_generat on/ftr_t et/ftr-based-s mclusters- ndex-generat on-job.d6w \
--b nd=prof le.project=${GCP_PROJECT_NAME} \
--b nd=prof le.user_na =${SERV CE_ACCOUNT} \
--b nd=prof le.mach ne="n2-standard-2" \
--b nd=prof le.job_na ="ftr-pop10000-rnkdecay11-t et- ndex-generat on-batch-job" \
--b nd=prof le.bu ld_target="src/scala/com/tw ter/s mclusters_v2/sc o/bq_generat on/ftr_t et:ftr-t et- ndex-generat on-pop10000-rnkdecay11-job" \
--b nd=prof le.date=${PROJECT_DATE} \
--b nd=prof le.env ron nt=prod
```

### decayed_sum
```
export SERV CE_ACCOUNT='cassowary'
export GCP_PROJECT_NAME='twttr-recos-ml-prod'
export PROJECT_DATE='2022-09-05T16'

b n/d6w sc dule \
${GCP_PROJECT_NAME}/us-central1/decayed-sum-t et- ndex-generat on-batch-job \
src/scala/com/tw ter/s mclusters_v2/sc o/bq_generat on/ftr_t et/ftr-based-s mclusters- ndex-generat on-job.d6w \
--b nd=prof le.project=${GCP_PROJECT_NAME} \
--b nd=prof le.user_na =${SERV CE_ACCOUNT} \
--b nd=prof le.mach ne="n2-standard-2" \
--b nd=prof le.job_na ="decayed-sum-t et- ndex-generat on-batch-job" \
--b nd=prof le.bu ld_target="src/scala/com/tw ter/s mclusters_v2/sc o/bq_generat on/ftr_t et:ftr-t et- ndex-generat on-decayed-sum-job" \
--b nd=prof le.date=${PROJECT_DATE} \
--b nd=prof le.env ron nt=prod
```


### OON ftrat5
```
export SERV CE_ACCOUNT='cassowary'
export GCP_PROJECT_NAME='twttr-recos-ml-prod'
export PROJECT_DATE='2022-09-21T16'

b n/d6w sc dule \
${GCP_PROJECT_NAME}/us-central1/oon-ftr-pop1000-rnkdecay-t et- ndex-generat on-batch-job \
src/scala/com/tw ter/s mclusters_v2/sc o/bq_generat on/ftr_t et/ftr-based-s mclusters- ndex-generat on-job.d6w \
--b nd=prof le.project=${GCP_PROJECT_NAME} \
--b nd=prof le.user_na =${SERV CE_ACCOUNT} \
--b nd=prof le.mach ne="n2-standard-2" \
--b nd=prof le.job_na ="oon-ftr-pop1000-rnkdecay-t et- ndex-generat on-batch-job" \
--b nd=prof le.bu ld_target="src/scala/com/tw ter/s mclusters_v2/sc o/bq_generat on/ftr_t et:oon-ftr-t et- ndex-generat on-pop1000-rnkdecay-job" \
--b nd=prof le.date=${PROJECT_DATE} \
--b nd=prof le.env ron nt=prod
```

### Desc dule
```
export SERV CE_ACCOUNT='cassowary'

aurora cron desc dule atla/${SERV CE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central1-ftr-pop1000-rnkdecay11-t et- ndex-generat on-batch-job
aurora cron desc dule atla/${SERV CE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central1-ftr-pop1000-rnkdecay11-t et- ndex-generat on-batch-job

aurora cron desc dule atla/${SERV CE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central1-ftr-pop10000-rnkdecay11-t et- ndex-generat on-batch-job
aurora cron desc dule atla/${SERV CE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central1-ftr-pop10000-rnkdecay11-t et- ndex-generat on-batch-job

aurora cron desc dule atla/${SERV CE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central1-decayed-sum-t et- ndex-generat on-batch-job
aurora cron desc dule atla/${SERV CE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central1-decayed-sum-t et- ndex-generat on-batch-job

aurora cron desc dule atla/${SERV CE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central1-oon-ftr-pop1000-rnkdecay-t et- ndex-generat on-batch-job
aurora cron desc dule atla/${SERV CE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central1-oon-ftr-pop1000-rnkdecay-t et- ndex-generat on-batch-job
```
