# FTR Tweet embeddings 

export GCP_PROJECT_NAME='twttr-recos-ml-prod'

## Running Adhoc jobs
### Base ftrat5 
```
rm dist/ftr-tweet-adhoc-job-bundle/ftr-tweet-adhoc-job.jar
./bazel bundle  src/scala/com/twitter/simclusters_v2/scio/bq_generation/ftr_tweet:ftr-tweet-adhoc-job && \
bin/d6w create \
${GCP_PROJECT_NAME}/us-central1/ftr-tweets-ann-adhoc-job \
src/scala/com/twitter/simclusters_v2/scio/bq_generation/ftr_tweet/ftr-tweets-ann-adhoc-job.d6w \
--jar dist/ftr-tweet-adhoc-job-bundle/ftr-tweet-adhoc-job.jar \
--bind=profile.project=
${GCP_PROJECT_NAME} \
--bind=profile.user_name=your_ldap \
--bind=profile.build_target="src/scala/com/twitter/simclusters_v2/scio/bq_generation/ftr_tweet:ftr-tweet-index-generation-adhoc-job" \
--bind=profile.date="2022-08-26T12" \
--bind=profile.machine="n2-standard-2" \
--bind=profile.job_name="ftr-tweets-ann-adhoc-job" --ignore-existing
```
### ClusterToTweet Index with base ftrat5
```
export GCP_PROJECT_NAME='twttr-recos-ml-prod'

rm dist/ftr-tweet-index-generation-adhoc-job-bundle/ftr-tweet-index-generation-adhoc-job.jar
./bazel bundle  src/scala/com/twitter/simclusters_v2/scio/bq_generation/ftr_tweet:ftr-tweet-index-generation-adhoc-job && \
bin/d6w create \
${GCP_PROJECT_NAME}/us-central1/ftr-tweet-index-generation-adhoc-job \
src/scala/com/twitter/simclusters_v2/scio/bq_generation/ftr_tweet/ftr-based-simclusters-index-generation-job.d6w \
--jar dist/ftr-tweet-index-generation-adhoc-job-bundle/ftr-tweet-index-generation-adhoc-job.jar \
--bind=profile.project=${GCP_PROJECT_NAME} \
--bind=profile.user_name=your_ldap \
--bind=profile.date="2022-08-27T12" \
--bind=profile.machine="n2-standard-2" \
--bind=profile.build_target="src/scala/com/twitter/simclusters_v2/scio/bq_generation/ftr_tweet:ftr-tweet-index-generation-adhoc-job" \
--bind=profile.job_name="ftr-tweet-index-generation-adhoc-job" --ignore-existing
```

### OON ftrat5
```
rm dist/oon-ftr-tweet-index-generation-adhoc-job-bundle/oon-ftr-tweet-index-generation-adhoc-job.jar
./bazel bundle  src/scala/com/twitter/simclusters_v2/scio/bq_generation/ftr_tweet:oon-ftr-tweet-index-generation-adhoc-job && \
bin/d6w create \
${GCP_PROJECT_NAME}/us-central1/oon-ftr-ann-adhoc-job \
src/scala/com/twitter/simclusters_v2/scio/bq_generation/ftr_tweet/ftr-based-simclusters-index-generation-job.d6w \
--jar dist/oon-ftr-tweet-index-generation-adhoc-job-bundle/oon-ftr-tweet-index-generation-adhoc-job.jar \
--bind=profile.project=${GCP_PROJECT_NAME} \
--bind=profile.user_name=${USER} \
--bind=profile.build_target="src/scala/com/twitter/simclusters_v2/scio/bq_generation/ftr_tweet:oon-ftr-tweet-index-generation-adhoc-job" \
--bind=profile.date="2022-09-21T12" \
--bind=profile.machine="n2-standard-2" \
--bind=profile.job_name="oon-ftr-ann-adhoc-job" --ignore-existing
```


## Scheduling jobs
### decayed_sum_job
```
export SERVICE_ACCOUNT='cassowary'
export GCP_PROJECT_NAME='twttr-recos-ml-prod'
export PROJECT_DATE='2022-07-24T16'

bin/d6w schedule \
${GCP_PROJECT_NAME}/us-central1/iikf2020-decayed-sum-ann-batch-job \
src/scala/com/twitter/simclusters_v2/scio/bq_generation/ftr_tweet/iikf2020-decayed-sum-ann-batch-job.d6w \
--bind=profile.project=${GCP_PROJECT_NAME} \
--bind=profile.user_name=${SERVICE_ACCOUNT} \
--bind=profile.machine="n2-highmem-4" \
--bind=profile.job_name="iikf2020-decayed-sum-ann-batch-job" \
--bind=profile.date=${PROJECT_DATE} \
--bind=profile.environment=prod
```

### ftrat5 pop1000

```
export SERVICE_ACCOUNT='cassowary'
export GCP_PROJECT_NAME='twttr-recos-ml-prod'
export PROJECT_DATE='2022-07-24T17'

bin/d6w schedule \
${GCP_PROJECT_NAME}/us-central1/iikf2020-ftrat5-pop1000-ann-batch-job \
src/scala/com/twitter/simclusters_v2/scio/bq_generation/ftr_tweet/iikf2020-ftrat5-pop1000-ann-batch-job.d6w \
--bind=profile.project=${GCP_PROJECT_NAME} \
--bind=profile.user_name=${SERVICE_ACCOUNT} \
--bind=profile.machine="n2-highmem-4" \
--bind=profile.job_name="iikf2020-ftrat5-pop1000-ann-batch-job" \
--bind=profile.date=${PROJECT_DATE} \
--bind=profile.environment=prod
```


### ftrat5 pop10000
```
export SERVICE_ACCOUNT='cassowary'
export GCP_PROJECT_NAME='twttr-recos-ml-prod'
export PROJECT_DATE='2022-07-24T18'

bin/d6w schedule \
${GCP_PROJECT_NAME}/us-central1/iikf2020-ftrat5-pop10000-ann-batch-job \
src/scala/com/twitter/simclusters_v2/scio/bq_generation/ftr_tweet/iikf2020-ftrat5-pop10000-ann-batch-job.d6w \
--bind=profile.project=${GCP_PROJECT_NAME} \
--bind=profile.user_name=${SERVICE_ACCOUNT} \
--bind=profile.machine="n2-highmem-4" \
--bind=profile.job_name="iikf2020-ftrat5-pop10000-ann-batch-job"  \
--bind=profile.date=${PROJECT_DATE} \
--bind=profile.environment=prod
```

### Deschedule
```
export SERVICE_ACCOUNT='cassowary'

aurora cron deschedule atla/${SERVICE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central1-iikf2020-decayed-sum-ann-batch-job
aurora cron deschedule atla/${SERVICE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central1-iikf2020-ftrat5-pop1000-ann-batch-job
aurora cron deschedule atla/${SERVICE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central1-iikf2020-ftrat5-pop10000-ann-batch-job

aurora cron deschedule atla/${SERVICE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central1-iikf2020-decayed-sum-ann-batch-job
aurora cron deschedule atla/${SERVICE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central1-iikf2020-ftrat5-pop1000-ann-batch-job
aurora cron deschedule atla/${SERVICE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central1-iikf2020-ftrat5-pop10000-ann-batch-job
```

### pop1000-rnkdecay11
```
export SERVICE_ACCOUNT='cassowary'
export GCP_PROJECT_NAME='twttr-recos-ml-prod'
export PROJECT_DATE='2022-08-27T16'

bin/d6w schedule \
${GCP_PROJECT_NAME}/us-central1/ftr-pop1000-rnkdecay11-tweet-index-generation-batch-job \
src/scala/com/twitter/simclusters_v2/scio/bq_generation/ftr_tweet/ftr-based-simclusters-index-generation-job.d6w \
--bind=profile.project=${GCP_PROJECT_NAME} \
--bind=profile.user_name=${SERVICE_ACCOUNT} \
--bind=profile.machine="n2-standard-2" \
--bind=profile.job_name="ftr-pop1000-rnkdecay11-tweet-index-generation-batch-job" \
--bind=profile.build_target="src/scala/com/twitter/simclusters_v2/scio/bq_generation/ftr_tweet:ftr-tweet-index-generation-pop1000-rnkdecay11-job" \
--bind=profile.date=${PROJECT_DATE} \
--bind=profile.environment=prod
```

### pop10000-rnkdecay11
```
export SERVICE_ACCOUNT='cassowary'
export GCP_PROJECT_NAME='twttr-recos-ml-prod'
export PROJECT_DATE='2022-08-27T16'

bin/d6w schedule \
${GCP_PROJECT_NAME}/us-central1/ftr-pop10000-rnkdecay11-tweet-index-generation-batch-job \
src/scala/com/twitter/simclusters_v2/scio/bq_generation/ftr_tweet/ftr-based-simclusters-index-generation-job.d6w \
--bind=profile.project=${GCP_PROJECT_NAME} \
--bind=profile.user_name=${SERVICE_ACCOUNT} \
--bind=profile.machine="n2-standard-2" \
--bind=profile.job_name="ftr-pop10000-rnkdecay11-tweet-index-generation-batch-job" \
--bind=profile.build_target="src/scala/com/twitter/simclusters_v2/scio/bq_generation/ftr_tweet:ftr-tweet-index-generation-pop10000-rnkdecay11-job" \
--bind=profile.date=${PROJECT_DATE} \
--bind=profile.environment=prod
```

### decayed_sum
```
export SERVICE_ACCOUNT='cassowary'
export GCP_PROJECT_NAME='twttr-recos-ml-prod'
export PROJECT_DATE='2022-09-05T16'

bin/d6w schedule \
${GCP_PROJECT_NAME}/us-central1/decayed-sum-tweet-index-generation-batch-job \
src/scala/com/twitter/simclusters_v2/scio/bq_generation/ftr_tweet/ftr-based-simclusters-index-generation-job.d6w \
--bind=profile.project=${GCP_PROJECT_NAME} \
--bind=profile.user_name=${SERVICE_ACCOUNT} \
--bind=profile.machine="n2-standard-2" \
--bind=profile.job_name="decayed-sum-tweet-index-generation-batch-job" \
--bind=profile.build_target="src/scala/com/twitter/simclusters_v2/scio/bq_generation/ftr_tweet:ftr-tweet-index-generation-decayed-sum-job" \
--bind=profile.date=${PROJECT_DATE} \
--bind=profile.environment=prod
```


### OON ftrat5
```
export SERVICE_ACCOUNT='cassowary'
export GCP_PROJECT_NAME='twttr-recos-ml-prod'
export PROJECT_DATE='2022-09-21T16'

bin/d6w schedule \
${GCP_PROJECT_NAME}/us-central1/oon-ftr-pop1000-rnkdecay-tweet-index-generation-batch-job \
src/scala/com/twitter/simclusters_v2/scio/bq_generation/ftr_tweet/ftr-based-simclusters-index-generation-job.d6w \
--bind=profile.project=${GCP_PROJECT_NAME} \
--bind=profile.user_name=${SERVICE_ACCOUNT} \
--bind=profile.machine="n2-standard-2" \
--bind=profile.job_name="oon-ftr-pop1000-rnkdecay-tweet-index-generation-batch-job" \
--bind=profile.build_target="src/scala/com/twitter/simclusters_v2/scio/bq_generation/ftr_tweet:oon-ftr-tweet-index-generation-pop1000-rnkdecay-job" \
--bind=profile.date=${PROJECT_DATE} \
--bind=profile.environment=prod
```

### Deschedule
```
export SERVICE_ACCOUNT='cassowary'

aurora cron deschedule atla/${SERVICE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central1-ftr-pop1000-rnkdecay11-tweet-index-generation-batch-job
aurora cron deschedule atla/${SERVICE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central1-ftr-pop1000-rnkdecay11-tweet-index-generation-batch-job

aurora cron deschedule atla/${SERVICE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central1-ftr-pop10000-rnkdecay11-tweet-index-generation-batch-job
aurora cron deschedule atla/${SERVICE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central1-ftr-pop10000-rnkdecay11-tweet-index-generation-batch-job

aurora cron deschedule atla/${SERVICE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central1-decayed-sum-tweet-index-generation-batch-job
aurora cron deschedule atla/${SERVICE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central1-decayed-sum-tweet-index-generation-batch-job

aurora cron deschedule atla/${SERVICE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central1-oon-ftr-pop1000-rnkdecay-tweet-index-generation-batch-job
aurora cron deschedule atla/${SERVICE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central1-oon-ftr-pop1000-rnkdecay-tweet-index-generation-batch-job
```
