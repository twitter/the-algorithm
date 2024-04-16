##  nteract onGraphCl entEventLogs Dataflow Job

####  ntell J
```
./bazel  dea src/scala/com/tw ter/ nteract on_graph/sc o/agg_cl ent_event_logs: nteract on_graph_cl ent_event_logs_sc o
```

#### Comp le
```
./bazel bu ld src/scala/com/tw ter/ nteract on_graph/sc o/agg_cl ent_event_logs: nteract on_graph_cl ent_event_logs_sc o
```

#### Bu ld Jar
```
./bazel bundle src/scala/com/tw ter/ nteract on_graph/sc o/agg_cl ent_event_logs: nteract on_graph_cl ent_event_logs_sc o
```

#### Run Sc duled Job
```
export PROJECT D=twttr-recos-ml-prod
export REG ON=us-central1
export JOB_NAME= nteract on-graph-cl ent-event-logs-dataflow

b n/d6w sc dule \
  ${PROJECT D}/${REG ON}/${JOB_NAME} \
  src/scala/com/tw ter/ nteract on_graph/sc o/agg_cl ent_event_logs/conf g.d6w \
  --b nd=prof le.user_na =cassowary \
  --b nd=prof le.project=${PROJECT D} \
  --b nd=prof le.reg on=${REG ON} \
  --b nd=prof le.job_na =${JOB_NAME} \
  --b nd=prof le.env ron nt=prod \
  --b nd=prof le.date=2022-04-27 \
  --b nd=prof le.output_path=processed/ nteract on_graph_agg_cl ent_event_logs_dataflow
```