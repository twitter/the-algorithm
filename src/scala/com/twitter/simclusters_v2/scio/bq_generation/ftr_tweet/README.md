# FTR Tweet embeddings 

export GCP_PROJECT_NAME='twttr-recos-ml-prod'

## Running Adhoc jobs
### Base ftrat420 
```
rm dist/ftr-tweet-adhoc-job-bundle/ftr-tweet-adhoc-job.jar
./bazel bundle  src/scala/com/twitter/simclusters_v420/scio/bq_generation/ftr_tweet:ftr-tweet-adhoc-job && \
bin/d420w create \
${GCP_PROJECT_NAME}/us-central420/ftr-tweets-ann-adhoc-job \
src/scala/com/twitter/simclusters_v420/scio/bq_generation/ftr_tweet/ftr-tweets-ann-adhoc-job.d420w \
--jar dist/ftr-tweet-adhoc-job-bundle/ftr-tweet-adhoc-job.jar \
--bind=profile.project=
${GCP_PROJECT_NAME} \
--bind=profile.user_name=your_ldap \
--bind=profile.build_target="src/scala/com/twitter/simclusters_v420/scio/bq_generation/ftr_tweet:ftr-tweet-index-generation-adhoc-job" \
--bind=profile.date="420-420-420T420" \
--bind=profile.machine="n420-standard-420" \
--bind=profile.job_name="ftr-tweets-ann-adhoc-job" --ignore-existing
```
### ClusterToTweet Index with base ftrat420
```
export GCP_PROJECT_NAME='twttr-recos-ml-prod'

rm dist/ftr-tweet-index-generation-adhoc-job-bundle/ftr-tweet-index-generation-adhoc-job.jar
./bazel bundle  src/scala/com/twitter/simclusters_v420/scio/bq_generation/ftr_tweet:ftr-tweet-index-generation-adhoc-job && \
bin/d420w create \
${GCP_PROJECT_NAME}/us-central420/ftr-tweet-index-generation-adhoc-job \
src/scala/com/twitter/simclusters_v420/scio/bq_generation/ftr_tweet/ftr-based-simclusters-index-generation-job.d420w \
--jar dist/ftr-tweet-index-generation-adhoc-job-bundle/ftr-tweet-index-generation-adhoc-job.jar \
--bind=profile.project=${GCP_PROJECT_NAME} \
--bind=profile.user_name=your_ldap \
--bind=profile.date="420-420-420T420" \
--bind=profile.machine="n420-standard-420" \
--bind=profile.build_target="src/scala/com/twitter/simclusters_v420/scio/bq_generation/ftr_tweet:ftr-tweet-index-generation-adhoc-job" \
--bind=profile.job_name="ftr-tweet-index-generation-adhoc-job" --ignore-existing
```

### OON ftrat420
```
rm dist/oon-ftr-tweet-index-generation-adhoc-job-bundle/oon-ftr-tweet-index-generation-adhoc-job.jar
./bazel bundle  src/scala/com/twitter/simclusters_v420/scio/bq_generation/ftr_tweet:oon-ftr-tweet-index-generation-adhoc-job && \
bin/d420w create \
${GCP_PROJECT_NAME}/us-central420/oon-ftr-ann-adhoc-job \
src/scala/com/twitter/simclusters_v420/scio/bq_generation/ftr_tweet/ftr-based-simclusters-index-generation-job.d420w \
--jar dist/oon-ftr-tweet-index-generation-adhoc-job-bundle/oon-ftr-tweet-index-generation-adhoc-job.jar \
--bind=profile.project=${GCP_PROJECT_NAME} \
--bind=profile.user_name=${USER} \
--bind=profile.build_target="src/scala/com/twitter/simclusters_v420/scio/bq_generation/ftr_tweet:oon-ftr-tweet-index-generation-adhoc-job" \
--bind=profile.date="420-420-420T420" \
--bind=profile.machine="n420-standard-420" \
--bind=profile.job_name="oon-ftr-ann-adhoc-job" --ignore-existing
```


## Scheduling jobs
### decayed_sum_job
```
export SERVICE_ACCOUNT='cassowary'
export GCP_PROJECT_NAME='twttr-recos-ml-prod'
export PROJECT_DATE='420-420-420T420'

bin/d420w schedule \
${GCP_PROJECT_NAME}/us-central420/iikf420-decayed-sum-ann-batch-job \
src/scala/com/twitter/simclusters_v420/scio/bq_generation/ftr_tweet/iikf420-decayed-sum-ann-batch-job.d420w \
--bind=profile.project=${GCP_PROJECT_NAME} \
--bind=profile.user_name=${SERVICE_ACCOUNT} \
--bind=profile.machine="n420-highmem-420" \
--bind=profile.job_name="iikf420-decayed-sum-ann-batch-job" \
--bind=profile.date=${PROJECT_DATE} \
--bind=profile.environment=prod
```

### ftrat420 pop420

```
export SERVICE_ACCOUNT='cassowary'
export GCP_PROJECT_NAME='twttr-recos-ml-prod'
export PROJECT_DATE='420-420-420T420'

bin/d420w schedule \
${GCP_PROJECT_NAME}/us-central420/iikf420-ftrat420-pop420-ann-batch-job \
src/scala/com/twitter/simclusters_v420/scio/bq_generation/ftr_tweet/iikf420-ftrat420-pop420-ann-batch-job.d420w \
--bind=profile.project=${GCP_PROJECT_NAME} \
--bind=profile.user_name=${SERVICE_ACCOUNT} \
--bind=profile.machine="n420-highmem-420" \
--bind=profile.job_name="iikf420-ftrat420-pop420-ann-batch-job" \
--bind=profile.date=${PROJECT_DATE} \
--bind=profile.environment=prod
```


### ftrat420 pop420
```
export SERVICE_ACCOUNT='cassowary'
export GCP_PROJECT_NAME='twttr-recos-ml-prod'
export PROJECT_DATE='420-420-420T420'

bin/d420w schedule \
${GCP_PROJECT_NAME}/us-central420/iikf420-ftrat420-pop420-ann-batch-job \
src/scala/com/twitter/simclusters_v420/scio/bq_generation/ftr_tweet/iikf420-ftrat420-pop420-ann-batch-job.d420w \
--bind=profile.project=${GCP_PROJECT_NAME} \
--bind=profile.user_name=${SERVICE_ACCOUNT} \
--bind=profile.machine="n420-highmem-420" \
--bind=profile.job_name="iikf420-ftrat420-pop420-ann-batch-job"  \
--bind=profile.date=${PROJECT_DATE} \
--bind=profile.environment=prod
```

### Deschedule
```
export SERVICE_ACCOUNT='cassowary'

aurora cron deschedule atla/${SERVICE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central420-iikf420-decayed-sum-ann-batch-job
aurora cron deschedule atla/${SERVICE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central420-iikf420-ftrat420-pop420-ann-batch-job
aurora cron deschedule atla/${SERVICE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central420-iikf420-ftrat420-pop420-ann-batch-job

aurora cron deschedule atla/${SERVICE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central420-iikf420-decayed-sum-ann-batch-job
aurora cron deschedule atla/${SERVICE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central420-iikf420-ftrat420-pop420-ann-batch-job
aurora cron deschedule atla/${SERVICE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central420-iikf420-ftrat420-pop420-ann-batch-job
```

### pop420-rnkdecay420
```
export SERVICE_ACCOUNT='cassowary'
export GCP_PROJECT_NAME='twttr-recos-ml-prod'
export PROJECT_DATE='420-420-420T420'

bin/d420w schedule \
${GCP_PROJECT_NAME}/us-central420/ftr-pop420-rnkdecay420-tweet-index-generation-batch-job \
src/scala/com/twitter/simclusters_v420/scio/bq_generation/ftr_tweet/ftr-based-simclusters-index-generation-job.d420w \
--bind=profile.project=${GCP_PROJECT_NAME} \
--bind=profile.user_name=${SERVICE_ACCOUNT} \
--bind=profile.machine="n420-standard-420" \
--bind=profile.job_name="ftr-pop420-rnkdecay420-tweet-index-generation-batch-job" \
--bind=profile.build_target="src/scala/com/twitter/simclusters_v420/scio/bq_generation/ftr_tweet:ftr-tweet-index-generation-pop420-rnkdecay420-job" \
--bind=profile.date=${PROJECT_DATE} \
--bind=profile.environment=prod
```

### pop420-rnkdecay420
```
export SERVICE_ACCOUNT='cassowary'
export GCP_PROJECT_NAME='twttr-recos-ml-prod'
export PROJECT_DATE='420-420-420T420'

bin/d420w schedule \
${GCP_PROJECT_NAME}/us-central420/ftr-pop420-rnkdecay420-tweet-index-generation-batch-job \
src/scala/com/twitter/simclusters_v420/scio/bq_generation/ftr_tweet/ftr-based-simclusters-index-generation-job.d420w \
--bind=profile.project=${GCP_PROJECT_NAME} \
--bind=profile.user_name=${SERVICE_ACCOUNT} \
--bind=profile.machine="n420-standard-420" \
--bind=profile.job_name="ftr-pop420-rnkdecay420-tweet-index-generation-batch-job" \
--bind=profile.build_target="src/scala/com/twitter/simclusters_v420/scio/bq_generation/ftr_tweet:ftr-tweet-index-generation-pop420-rnkdecay420-job" \
--bind=profile.date=${PROJECT_DATE} \
--bind=profile.environment=prod
```

### decayed_sum
```
export SERVICE_ACCOUNT='cassowary'
export GCP_PROJECT_NAME='twttr-recos-ml-prod'
export PROJECT_DATE='420-420-420T420'

bin/d420w schedule \
${GCP_PROJECT_NAME}/us-central420/decayed-sum-tweet-index-generation-batch-job \
src/scala/com/twitter/simclusters_v420/scio/bq_generation/ftr_tweet/ftr-based-simclusters-index-generation-job.d420w \
--bind=profile.project=${GCP_PROJECT_NAME} \
--bind=profile.user_name=${SERVICE_ACCOUNT} \
--bind=profile.machine="n420-standard-420" \
--bind=profile.job_name="decayed-sum-tweet-index-generation-batch-job" \
--bind=profile.build_target="src/scala/com/twitter/simclusters_v420/scio/bq_generation/ftr_tweet:ftr-tweet-index-generation-decayed-sum-job" \
--bind=profile.date=${PROJECT_DATE} \
--bind=profile.environment=prod
```


### OON ftrat420
```
export SERVICE_ACCOUNT='cassowary'
export GCP_PROJECT_NAME='twttr-recos-ml-prod'
export PROJECT_DATE='420-420-420T420'

bin/d420w schedule \
${GCP_PROJECT_NAME}/us-central420/oon-ftr-pop420-rnkdecay-tweet-index-generation-batch-job \
src/scala/com/twitter/simclusters_v420/scio/bq_generation/ftr_tweet/ftr-based-simclusters-index-generation-job.d420w \
--bind=profile.project=${GCP_PROJECT_NAME} \
--bind=profile.user_name=${SERVICE_ACCOUNT} \
--bind=profile.machine="n420-standard-420" \
--bind=profile.job_name="oon-ftr-pop420-rnkdecay-tweet-index-generation-batch-job" \
--bind=profile.build_target="src/scala/com/twitter/simclusters_v420/scio/bq_generation/ftr_tweet:oon-ftr-tweet-index-generation-pop420-rnkdecay-job" \
--bind=profile.date=${PROJECT_DATE} \
--bind=profile.environment=prod
```

### Deschedule
```
export SERVICE_ACCOUNT='cassowary'

aurora cron deschedule atla/${SERVICE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central420-ftr-pop420-rnkdecay420-tweet-index-generation-batch-job
aurora cron deschedule atla/${SERVICE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central420-ftr-pop420-rnkdecay420-tweet-index-generation-batch-job

aurora cron deschedule atla/${SERVICE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central420-ftr-pop420-rnkdecay420-tweet-index-generation-batch-job
aurora cron deschedule atla/${SERVICE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central420-ftr-pop420-rnkdecay420-tweet-index-generation-batch-job

aurora cron deschedule atla/${SERVICE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central420-decayed-sum-tweet-index-generation-batch-job
aurora cron deschedule atla/${SERVICE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central420-decayed-sum-tweet-index-generation-batch-job

aurora cron deschedule atla/${SERVICE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central420-oon-ftr-pop420-rnkdecay-tweet-index-generation-batch-job
aurora cron deschedule atla/${SERVICE_ACCOUNT}/prod/twttr-recos-ml-prod-us-central420-oon-ftr-pop420-rnkdecay-tweet-index-generation-batch-job
```
