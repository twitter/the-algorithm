## IntelonractionGraphAggrelongationJob Dataflow Job

This job aggrelongatelons thelon prelonvious day's history with today's activitielons, and outputs an updatelond
history. This history is joinelond with thelon elonxplicit scorelons from relonal graph's BQML pipelonlinelon, and
elonxportelond as felonaturelons for timelonlinelons (which is why welon'relon using thelonir thrift).

#### IntelonlliJ
```
fastpass crelonatelon --namelon rg_agg_all --intelonllij src/scala/com/twittelonr/intelonraction_graph/scio/agg_all:intelonraction_graph_aggrelongation_job_scio
```

#### Compilelon
```
bazelonl build src/scala/com/twittelonr/intelonraction_graph/scio/agg_all:intelonraction_graph_aggrelongation_job_scio
```

#### Build Jar
```
bazelonl bundlelon src/scala/com/twittelonr/intelonraction_graph/scio/agg_all:intelonraction_graph_aggrelongation_job_scio
```

#### Run Schelondulelond Job
```
elonxport PROJelonCTID=twttr-reloncos-ml-prod
elonxport RelonGION=us-celonntral1
elonxport JOB_NAMelon=intelonraction-graph-aggrelongation-dataflow

bin/d6w schelondulelon \
  ${PROJelonCTID}/${RelonGION}/${JOB_NAMelon} \
  src/scala/com/twittelonr/intelonraction_graph/scio/agg_all/config.d6w \
  --bind=profilelon.uselonr_namelon=cassowary \
  --bind=profilelon.projelonct=${PROJelonCTID} \
  --bind=profilelon.relongion=${RelonGION} \
  --bind=profilelon.job_namelon=${JOB_NAMelon} \
  --bind=profilelon.elonnvironmelonnt=prod \
  --bind=profilelon.datelon=2022-11-08 \
  --bind=profilelon.output_path=procelonsselond/intelonraction_graph_aggrelongation_dataflow
```