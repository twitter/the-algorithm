## InteractionGraphAggregationJob Dataflow Job

This job aggregates the previous day's history with today's activities, and outputs an updated
history. This history is joined with the explicit scores from real graph's BQML pipeline, and
exported as features for timelines (which is why we're using their thrift).

#### IntelliJ
```
fastpass create --name rg_agg_all --intellij src/scala/com/twitter/interaction_graph/scio/agg_all:interaction_graph_aggregation_job_scio
```

#### Compile
```
bazel build src/scala/com/twitter/interaction_graph/scio/agg_all:interaction_graph_aggregation_job_scio
```

#### Build Jar
```
bazel bundle src/scala/com/twitter/interaction_graph/scio/agg_all:interaction_graph_aggregation_job_scio
```

#### Run Scheduled Job
```
export PROJECTID=twttr-recos-ml-prod
export REGION=us-central1
export JOB_NAME=interaction-graph-aggregation-dataflow

bin/d6w schedule \
  ${PROJECTID}/${REGION}/${JOB_NAME} \
  src/scala/com/twitter/interaction_graph/scio/agg_all/config.d6w \
  --bind=profile.user_name=cassowary \
  --bind=profile.project=${PROJECTID} \
  --bind=profile.region=${REGION} \
  --bind=profile.job_name=${JOB_NAME} \
  --bind=profile.environment=prod \
  --bind=profile.date=2022-11-08 \
  --bind=profile.output_path=processed/interaction_graph_aggregation_dataflow
```