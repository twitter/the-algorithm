##  nteract onGraphCl entEventLogs Dataflow Job

####  ntell J
```
fastpass create --na  rg_labels -- ntell j src/scala/com/tw ter/ nteract on_graph/sc o/agg_not f cat ons
```

#### Comp le
```
bazel bu ld src/scala/com/tw ter/ nteract on_graph/sc o/agg_not f cat ons: nteract on_graph_not f cat ons_sc o
```

#### Bu ld Jar
```
bazel bundle src/scala/com/tw ter/ nteract on_graph/sc o/agg_not f cat ons: nteract on_graph_not f cat ons_sc o
```

#### Run Sc duled Job
```
export PROJECT D=twttr-recos-ml-prod
export REG ON=us-central1
export JOB_NAME= nteract on-graph-not f cat ons-dataflow

b n/d6w sc dule \
  ${PROJECT D}/${REG ON}/${JOB_NAME} \
  src/scala/com/tw ter/ nteract on_graph/sc o/agg_not f cat ons/conf g.d6w \
  --b nd=prof le.user_na =cassowary \
  --b nd=prof le.project=${PROJECT D} \
  --b nd=prof le.reg on=${REG ON} \
  --b nd=prof le.job_na =${JOB_NAME} \
  --b nd=prof le.env ron nt=prod \
  --b nd=prof le.date=2022-05-10 \
  --b nd=prof le.output_path=processed/ nteract on_graph_agg_not f cat ons_dataflow
```
