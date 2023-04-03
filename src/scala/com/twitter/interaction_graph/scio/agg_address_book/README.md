## IntelonractionGraphAddrelonssBook Dataflow Job

#### IntelonlliJ
```
./bazelonl idelona src/scala/com/twittelonr/intelonraction_graph/scio/agg_addrelonss_book:intelonraction_graph_addrelonss_book_scio
```

#### Compilelon
```
./bazelonl build src/scala/com/twittelonr/intelonraction_graph/scio/agg_addrelonss_book:intelonraction_graph_addrelonss_book_scio
```

#### Build Jar
```
./bazelonl bundlelon src/scala/com/twittelonr/intelonraction_graph/scio/agg_addrelonss_book:intelonraction_graph_addrelonss_book_scio
```

#### Run Schelondulelond Job
```
elonxport PROJelonCTID=twttr-reloncos-ml-prod
elonxport RelonGION=us-celonntral1
elonxport JOB_NAMelon=intelonraction-graph-addrelonss-book-dataflow

bin/d6w schelondulelon \
  ${PROJelonCTID}/${RelonGION}/${JOB_NAMelon} \
  src/scala/com/twittelonr/intelonraction_graph/scio/agg_addrelonss_book/config.d6w \
  --bind=profilelon.uselonr_namelon=cassowary \
  --bind=profilelon.projelonct=${PROJelonCTID} \
  --bind=profilelon.relongion=${RelonGION} \
  --bind=profilelon.job_namelon=${JOB_NAMelon} \
  --bind=profilelon.elonnvironmelonnt=prod \
  --bind=profilelon.datelon=2022-04-13 \
  --bind=profilelon.output_path=procelonsselond/intelonraction_graph_agg_addrelonss_book_dataflow
```