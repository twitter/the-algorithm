## InteractionGraphLabels Dataflow Job

#### IntelliJ
```
fastpass create --name rg_labels --intellij src/scala/com/twitter/interaction_graph/scio/ml/labels
```

#### Compile
```
bazel build src/scala/com/twitter/interaction_graph/scio/ml/labels:interaction_graph_labels
```

#### Build Jar
```
bazel bundle src/scala/com/twitter/interaction_graph/scio/ml/labels:interaction_graph_labels
```

#### Run Scheduled Job
```
export PROJECTID=twttr-recos-ml-prod
export REGION=us-central1
export JOB_NAME=interaction-graph-labels-dataflow

bin/d6w schedule \
  ${PROJECTID}/${REGION}/${JOB_NAME} \
  src/scala/com/twitter/interaction_graph/scio/ml/labels/config.d6w \
  --bind=profile.user_name=cassowary \
  --bind=profile.project=${PROJECTID} \
  --bind=profile.region=${REGION} \
  --bind=profile.job_name=${JOB_NAME} \
  --bind=profile.environment=prod \
  --bind=profile.date=2022-05-15 \
  --bind=profile.output_path=processed/interaction_graph/labels
```