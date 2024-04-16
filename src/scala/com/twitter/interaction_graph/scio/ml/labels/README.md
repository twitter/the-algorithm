##  nteract onGraphLabels Dataflow Job

####  ntell J
```
fastpass create --na  rg_labels -- ntell j src/scala/com/tw ter/ nteract on_graph/sc o/ml/labels
```

#### Comp le
```
bazel bu ld src/scala/com/tw ter/ nteract on_graph/sc o/ml/labels: nteract on_graph_labels
```

#### Bu ld Jar
```
bazel bundle src/scala/com/tw ter/ nteract on_graph/sc o/ml/labels: nteract on_graph_labels
```

#### Run Sc duled Job
```
export PROJECT D=twttr-recos-ml-prod
export REG ON=us-central1
export JOB_NAME= nteract on-graph-labels-dataflow

b n/d6w sc dule \
  ${PROJECT D}/${REG ON}/${JOB_NAME} \
  src/scala/com/tw ter/ nteract on_graph/sc o/ml/labels/conf g.d6w \
  --b nd=prof le.user_na =cassowary \
  --b nd=prof le.project=${PROJECT D} \
  --b nd=prof le.reg on=${REG ON} \
  --b nd=prof le.job_na =${JOB_NAME} \
  --b nd=prof le.env ron nt=prod \
  --b nd=prof le.date=2022-05-15 \
  --b nd=prof le.output_path=processed/ nteract on_graph/labels
```