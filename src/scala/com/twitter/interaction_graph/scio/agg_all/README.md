##  nteract onGraphAggregat onJob Dataflow Job

T  job aggregates t  prev ous day's  tory w h today's act v  es, and outputs an updated
 tory. T   tory  s jo ned w h t  expl c  scores from real graph's BQML p pel ne, and
exported as features for t  l nes (wh ch  s why  're us ng t  r thr ft).

####  ntell J
```
fastpass create --na  rg_agg_all -- ntell j src/scala/com/tw ter/ nteract on_graph/sc o/agg_all: nteract on_graph_aggregat on_job_sc o
```

#### Comp le
```
bazel bu ld src/scala/com/tw ter/ nteract on_graph/sc o/agg_all: nteract on_graph_aggregat on_job_sc o
```

#### Bu ld Jar
```
bazel bundle src/scala/com/tw ter/ nteract on_graph/sc o/agg_all: nteract on_graph_aggregat on_job_sc o
```

#### Run Sc duled Job
```
export PROJECT D=twttr-recos-ml-prod
export REG ON=us-central1
export JOB_NAME= nteract on-graph-aggregat on-dataflow

b n/d6w sc dule \
  ${PROJECT D}/${REG ON}/${JOB_NAME} \
  src/scala/com/tw ter/ nteract on_graph/sc o/agg_all/conf g.d6w \
  --b nd=prof le.user_na =cassowary \
  --b nd=prof le.project=${PROJECT D} \
  --b nd=prof le.reg on=${REG ON} \
  --b nd=prof le.job_na =${JOB_NAME} \
  --b nd=prof le.env ron nt=prod \
  --b nd=prof le.date=2022-11-08 \
  --b nd=prof le.output_path=processed/ nteract on_graph_aggregat on_dataflow
```