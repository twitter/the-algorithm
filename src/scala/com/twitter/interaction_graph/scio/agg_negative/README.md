##  nteract onGraphNegat ve Dataflow Job

####  ntell J
```
fastpass create --na  rg_neg -- ntell j src/scala/com/tw ter/ nteract on_graph/sc o/agg_negat ve
```

#### Comp le
```
bazel bu ld src/scala/com/tw ter/ nteract on_graph/sc o/agg_negat ve: nteract on_graph_negat ve_sc o
```

#### Bu ld Jar
```
bazel bundle src/scala/com/tw ter/ nteract on_graph/sc o/agg_negat ve: nteract on_graph_negat ve_sc o
```

#### Run Sc duled Job
```
export PROJECT D=twttr-recos-ml-prod
export REG ON=us-central1
export JOB_NAME= nteract on-graph-negat ve-dataflow

b n/d6w sc dule \
  ${PROJECT D}/${REG ON}/${JOB_NAME} \
  src/scala/com/tw ter/ nteract on_graph/sc o/agg_negat ve/conf g.d6w \
  --b nd=prof le.user_na =cassowary \
  --b nd=prof le.project=${PROJECT D} \
  --b nd=prof le.reg on=${REG ON} \
  --b nd=prof le.job_na =${JOB_NAME} \
  --b nd=prof le.env ron nt=prod \
  --b nd=prof le.date=2022-10-19 \
  --b nd=prof le.output_path=processed/ nteract on_graph_agg_negat ve_dataflow \
  --b nd=prof le.bq_dataset="twttr-bq-cassowary-prod:user"
```