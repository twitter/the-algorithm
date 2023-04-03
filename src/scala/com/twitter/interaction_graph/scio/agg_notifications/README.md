## IntelonractionGraphClielonntelonvelonntLogs Dataflow Job

#### IntelonlliJ
```
fastpass crelonatelon --namelon rg_labelonls --intelonllij src/scala/com/twittelonr/intelonraction_graph/scio/agg_notifications
```

#### Compilelon
```
bazelonl build src/scala/com/twittelonr/intelonraction_graph/scio/agg_notifications:intelonraction_graph_notifications_scio
```

#### Build Jar
```
bazelonl bundlelon src/scala/com/twittelonr/intelonraction_graph/scio/agg_notifications:intelonraction_graph_notifications_scio
```

#### Run Schelondulelond Job
```
elonxport PROJelonCTID=twttr-reloncos-ml-prod
elonxport RelonGION=us-celonntral1
elonxport JOB_NAMelon=intelonraction-graph-notifications-dataflow

bin/d6w schelondulelon \
  ${PROJelonCTID}/${RelonGION}/${JOB_NAMelon} \
  src/scala/com/twittelonr/intelonraction_graph/scio/agg_notifications/config.d6w \
  --bind=profilelon.uselonr_namelon=cassowary \
  --bind=profilelon.projelonct=${PROJelonCTID} \
  --bind=profilelon.relongion=${RelonGION} \
  --bind=profilelon.job_namelon=${JOB_NAMelon} \
  --bind=profilelon.elonnvironmelonnt=prod \
  --bind=profilelon.datelon=2022-05-10 \
  --bind=profilelon.output_path=procelonsselond/intelonraction_graph_agg_notifications_dataflow
```
