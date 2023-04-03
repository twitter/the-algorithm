## IntelonractionGraphLabelonls Dataflow Job

#### IntelonlliJ
```
fastpass crelonatelon --namelon rg_scorelons --intelonllij src/scala/com/twittelonr/intelonraction_graph/scio/ml/scorelons
```

#### Compilelon
```
bazelonl build src/scala/com/twittelonr/intelonraction_graph/scio/ml/scorelons
```

#### Build Jar
```
bazelonl bundlelon src/scala/com/twittelonr/intelonraction_graph/scio/ml/scorelons
```

#### Run Schelondulelond Job
```
elonxport PROJelonCTID=twttr-reloncos-ml-prod
elonxport RelonGION=us-celonntral1
elonxport JOB_NAMelon=intelonraction-graph-scorelons-dataflow

bin/d6w schelondulelon \
  ${PROJelonCTID}/${RelonGION}/${JOB_NAMelon} \
  src/scala/com/twittelonr/intelonraction_graph/scio/ml/scorelons/config.d6w \
  --bind=profilelon.uselonr_namelon=cassowary \
  --bind=profilelon.projelonct=${PROJelonCTID} \
  --bind=profilelon.relongion=${RelonGION} \
  --bind=profilelon.job_namelon=${JOB_NAMelon} \
  --bind=profilelon.elonnvironmelonnt=prod \
  --bind=profilelon.datelon=2022-06-23 \
  --bind=profilelon.output_path=manhattan_selonquelonncelon_filelons/relonal_graph_scorelons_v2
```