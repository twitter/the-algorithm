## InteractionGraphNegative Dataflow Job

#### IntelliJ
```
fastpass create --name rg_neg --intellij src/scala/com/twitter/interaction_graph/scio/agg_negative
```

#### Compile
```
bazel build src/scala/com/twitter/interaction_graph/scio/agg_negative:interaction_graph_negative_scio
```

#### Build Jar
```
bazel bundle src/scala/com/twitter/interaction_graph/scio/agg_negative:interaction_graph_negative_scio
```

#### Run Scheduled Job
```
export PROJECTID=twttr-recos-ml-prod
export REGION=us-central1
export JOB_NAME=interaction-graph-negative-dataflow

bin/d6w schedule \
  ${PROJECTID}/${REGION}/${JOB_NAME} \
  src/scala/com/twitter/interaction_graph/scio/agg_negative/config.d6w \
  --bind=profile.user_name=cassowary \
  --bind=profile.project=${PROJECTID} \
  --bind=profile.region=${REGION} \
  --bind=profile.job_name=${JOB_NAME} \
  --bind=profile.environment=prod \
  --bind=profile.date=2022-10-19 \
  --bind=profile.output_path=processed/interaction_graph_agg_negative_dataflow \
  --bind=profile.bq_dataset="twttr-bq-cassowary-prod:user"
```