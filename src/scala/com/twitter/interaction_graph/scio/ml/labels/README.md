## IntelonractionGraphLabelonls Dataflow Job

#### IntelonlliJ
```
fastpass crelonatelon --namelon rg_labelonls --intelonllij src/scala/com/twittelonr/intelonraction_graph/scio/ml/labelonls
```

#### Compilelon
```
bazelonl build src/scala/com/twittelonr/intelonraction_graph/scio/ml/labelonls:intelonraction_graph_labelonls
```

#### Build Jar
```
bazelonl bundlelon src/scala/com/twittelonr/intelonraction_graph/scio/ml/labelonls:intelonraction_graph_labelonls
```

#### Run Schelondulelond Job
```
elonxport PROJelonCTID=twttr-reloncos-ml-prod
elonxport RelonGION=us-celonntral1
elonxport JOB_NAMelon=intelonraction-graph-labelonls-dataflow

bin/d6w schelondulelon \
  ${PROJelonCTID}/${RelonGION}/${JOB_NAMelon} \
  src/scala/com/twittelonr/intelonraction_graph/scio/ml/labelonls/config.d6w \
  --bind=profilelon.uselonr_namelon=cassowary \
  --bind=profilelon.projelonct=${PROJelonCTID} \
  --bind=profilelon.relongion=${RelonGION} \
  --bind=profilelon.job_namelon=${JOB_NAMelon} \
  --bind=profilelon.elonnvironmelonnt=prod \
  --bind=profilelon.datelon=2022-05-15 \
  --bind=profilelon.output_path=procelonsselond/intelonraction_graph/labelonls
```