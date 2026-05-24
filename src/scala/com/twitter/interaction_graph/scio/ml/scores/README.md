## InteractionGraphLabels Dataflow Job

#### IntelliJ
```
fastpass create --name rg_scores --intellij src/scala/com/twitter/interaction_graph/scio/ml/scores
```

#### Compile
```
bazel build src/scala/com/twitter/interaction_graph/scio/ml/scores
```

#### Build Jar
```
bazel bundle src/scala/com/twitter/interaction_graph/scio/ml/scores
```

#### Run Scheduled Job
```
export PROJECTID=twttr-recos-ml-prod
export REGION=us-central1
export JOB_NAME=interaction-graph-scores-dataflow

bin/d6w schedule \
  ${PROJECTID}/${REGION}/${JOB_NAME} \
  src/scala/com/twitter/interaction_graph/scio/ml/scores/config.d6w \
  --bind=profile.user_name=cassowary \
  --bind=profile.project=${PROJECTID} \
  --bind=profile.region=${REGION} \
  --bind=profile.job_name=${JOB_NAME} \
  --bind=profile.environment=prod \
  --bind=profile.date=2022-06-23 \
  --bind=profile.output_path=manhattan_sequence_files/real_graph_scores_v2
```