## InteractionGraphClientEventLogs Dataflow Job

#### IntelliJ
```
./bazel idea src/scala/com/twitter/interaction_graph/scio/agg_client_event_logs:interaction_graph_client_event_logs_scio
```

#### Compile
```
./bazel build src/scala/com/twitter/interaction_graph/scio/agg_client_event_logs:interaction_graph_client_event_logs_scio
```

#### Build Jar
```
./bazel bundle src/scala/com/twitter/interaction_graph/scio/agg_client_event_logs:interaction_graph_client_event_logs_scio
```

#### Run Scheduled Job
```
export PROJECTID=twttr-recos-ml-prod
export REGION=us-central1
export JOB_NAME=interaction-graph-client-event-logs-dataflow

bin/d6w schedule \
  ${PROJECTID}/${REGION}/${JOB_NAME} \
  src/scala/com/twitter/interaction_graph/scio/agg_client_event_logs/config.d6w \
  --bind=profile.user_name=cassowary \
  --bind=profile.project=${PROJECTID} \
  --bind=profile.region=${REGION} \
  --bind=profile.job_name=${JOB_NAME} \
  --bind=profile.environment=prod \
  --bind=profile.date=2022-04-27 \
  --bind=profile.output_path=processed/interaction_graph_agg_client_event_logs_dataflow
```