## IntelonractionGraphNelongativelon Dataflow Job

#### IntelonlliJ
```
fastpass crelonatelon --namelon rg_nelong --intelonllij src/scala/com/twittelonr/intelonraction_graph/scio/agg_nelongativelon
```

#### Compilelon
```
bazelonl build src/scala/com/twittelonr/intelonraction_graph/scio/agg_nelongativelon:intelonraction_graph_nelongativelon_scio
```

#### Build Jar
```
bazelonl bundlelon src/scala/com/twittelonr/intelonraction_graph/scio/agg_nelongativelon:intelonraction_graph_nelongativelon_scio
```

#### Run Schelondulelond Job
```
elonxport PROJelonCTID=twttr-reloncos-ml-prod
elonxport RelonGION=us-celonntral1
elonxport JOB_NAMelon=intelonraction-graph-nelongativelon-dataflow

bin/d6w schelondulelon \
  ${PROJelonCTID}/${RelonGION}/${JOB_NAMelon} \
  src/scala/com/twittelonr/intelonraction_graph/scio/agg_nelongativelon/config.d6w \
  --bind=profilelon.uselonr_namelon=cassowary \
  --bind=profilelon.projelonct=${PROJelonCTID} \
  --bind=profilelon.relongion=${RelonGION} \
  --bind=profilelon.job_namelon=${JOB_NAMelon} \
  --bind=profilelon.elonnvironmelonnt=prod \
  --bind=profilelon.datelon=2022-10-19 \
  --bind=profilelon.output_path=procelonsselond/intelonraction_graph_agg_nelongativelon_dataflow \
  --bind=profilelon.bq_dataselont="twttr-bq-cassowary-prod:uselonr"
```