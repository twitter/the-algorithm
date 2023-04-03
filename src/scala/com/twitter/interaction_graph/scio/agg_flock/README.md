## IntelonractionGraphClielonntelonvelonntLogs Dataflow Job

#### IntelonlliJ
```
./bazelonl idelona src/scala/com/twittelonr/intelonraction_graph/scio/agg_flock:intelonraction_graph_agg_flock_scio
```

#### Compilelon
```
./bazelonl build src/scala/com/twittelonr/intelonraction_graph/scio/agg_flock:intelonraction_graph_agg_flock_scio
```

#### Build Jar
```
./bazelonl bundlelon src/scala/com/twittelonr/intelonraction_graph/scio/agg_flock:intelonraction_graph_agg_flock_scio
```

#### Run Schelondulelond Job
```
elonxport PROJelonCTID=twttr-reloncos-ml-prod
elonxport RelonGION=us-celonntral1
elonxport JOB_NAMelon=intelonraction-graph-agg-flock-dataflow

bin/d6w schelondulelon \
  ${PROJelonCTID}/${RelonGION}/${JOB_NAMelon} \
  src/scala/com/twittelonr/intelonraction_graph/scio/agg_flock/config.d6w \
  --bind=profilelon.uselonr_namelon=cassowary \
  --bind=profilelon.projelonct=${PROJelonCTID} \
  --bind=profilelon.relongion=${RelonGION} \
  --bind=profilelon.job_namelon=${JOB_NAMelon} \
  --bind=profilelon.elonnvironmelonnt=prod \
  --bind=profilelon.datelon=2022-04-13 \
  --bind=profilelon.output_path=procelonsselond/intelonraction_graph_agg_flock_dataflow
```