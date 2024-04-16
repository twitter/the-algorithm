# Loadtest ANN query serv ce w h random embedd ngs

An ANN query serv ce can be load-tested w h random embedd ngs as quer es, generated automat cally by loadtest tool.
Example scr pt to load test a ANN query serv ce w h random embedd ngs:

```bash
$ aurora job create smf1/<role>/stag ng/ann-loadtest-serv ce ann/src/ma n/aurora/loadtest/loadtest.aurora \
  --b nd=prof le.na =ann-loadtest-serv ce \
  --b nd=prof le.role=<role> \
  --b nd=prof le.durat on_sec=10 \
  --b nd=prof le.number_of_ne ghbors=10 \
  --b nd=prof le.qps=200 \
  --b nd=prof le.algo=hnsw \
  --b nd=prof le. tr c=Cos ne \
  --b nd=prof le. ndex_ d_type= nt \
  --b nd=prof le.hnsw_ef=400,600,800 \
  --b nd=prof le.embedd ng_d  ns on=3 \
  --b nd=prof le.concurrency_level=8 \
  --b nd=prof le.loadtest_type=remote \
  --b nd=prof le.serv ce_dest nat on=/srv#/stag ng/local/apoorvs/ann-server-test \
  --b nd=prof le.w h_random_quer es=True \
  --b nd=prof le.random_quer es_count=50000 \
  --b nd=prof le.random_embedd ng_m n_value=-10.0 \
  --b nd=prof le.random_embedd ng_max_value=10.0
```

  w ll run t  loadtest w h `50000` random embedd ngs, w re each embedd ng value w ll be range bounded bet en `random_embedd ng_m n_value` and `random_embedd ng_max_value`.
 n t  above t  case   w ll be bounded bet en `-10.0` and `10.0`.
 f `random_embedd ng_m n_value` and `random_embedd ng_max_value` are not suppl ed default value of `-1.0` and `1.0` w ll be used.

## Results

Load test results w ll be pr nted to stdout of an aurora job.

# Loadtest ANN query serv ce w h query set

An ANN query serv ce can be load-tested w h sample quer es drawn from t  embedd ngs dataset.
For creat ng sample quer es  .e `query_set` refer t  [sect on](#query-set-generator).

Test  s run w h `l ve` vers on of loadtest b nary that  s already ava lable  n packer.
Example scr pt to load test a ANN query serv ce:

```bash
$ aurora job create smf1/<role>/stag ng/ann-loadtest-serv ce ann/src/ma n/aurora/loadtest/loadtest.aurora \
  --b nd=prof le.na =ann-loadtest-serv ce \
  --b nd=prof le.role=<role> \
  --b nd=prof le.durat on_sec=10 \
  --b nd=prof le.query_set_d r=hdfs:///user/cortex/ann_example/dataset/search/query_knn/query_set \
  --b nd=prof le.number_of_ne ghbors=10 \
  --b nd=prof le.qps=200 \
  --b nd=prof le.algo=hnsw \
  --b nd=prof le.query_ d_type=str ng \
  --b nd=prof le. ndex_ d_type=str ng \
  --b nd=prof le. tr c=Cos ne \
  --b nd=prof le.hnsw_ef=400,600,800 \
  --b nd=prof le.embedd ng_d  ns on=100 \
  --b nd=prof le.concurrency_level=8 \
  --b nd=prof le.loadtest_type=remote \
  --b nd=prof le.serv ce_dest nat on=/srv#/stag ng/local/apoorvs/ann-server-test
```

#  n- mory based loadtest for  asur ng recall

Load test can be w h t  above created dataset  n  mory.
For runn ng  n  n- mory mode,  ndex  s created  n  mory, and for that   need `query_set/ ndex_set/truth_set`.
For creat ng t  dataset refer t  [sect on](#knn-truth-set-generator).

Test  s run w h `l ve` vers on loadtest b nary that  s already ava lable  n packer.
Example scr pt  n- mory  ndex bu ld ng and benchmark ng:

```bash
$ aurora job create smf1/<role>/stag ng/ann-loadtest ann/src/ma n/aurora/loadtest/loadtest.aurora \
  --b nd=prof le.na =ann-loadtest \
  --b nd=prof le.role=<role> \
  --b nd=prof le.durat on_sec=10 \
  --b nd=prof le.truth_set_d r=hdfs:///user/cortex/ann_example/dataset/search/query_knn/true_knn \
  --b nd=prof le.query_set_d r=hdfs:///user/cortex/ann_example/dataset/search/query_knn/query_set \
  --b nd=prof le. ndex_set_d r=hdfs:///user/cortex/ann_example/dataset/search/query_knn/ ndex_set \
  --b nd=prof le.number_of_ne ghbors=10 \
  --b nd=prof le.qps=200 \
  --b nd=prof le.algo=hnsw \
  --b nd=prof le.query_ d_type=str ng \
  --b nd=prof le. ndex_ d_type=str ng \
  --b nd=prof le. tr c=Cos ne \
  --b nd=prof le.hnsw_ef_construct on=15 \
  --b nd=prof le.hnsw_max_m=10 \
  --b nd=prof le.hnsw_ef=400,600,800 \
  --b nd=prof le.embedd ng_d  ns on=100 \
  --b nd=prof le.concurrency_level=8 \
  --b nd=prof le.loadtest_type=local
```

# Loadtest fa ss

```bash
$ aurora job create smf1/<role>/stag ng/ann-loadtest-serv ce ann/src/ma n/aurora/loadtest/loadtest.aurora \
  --b nd=prof le.na =ann-loadtest-serv ce \
  --b nd=prof le.role=<role> \
  --b nd=prof le.durat on_sec=10 \
  --b nd=prof le.number_of_ne ghbors=10 \
  --b nd=prof le.qps=200 \
  --b nd=prof le.algo=fa ss \ # Changed to fa ss
  --b nd=prof le.fa ss_nprobe=1,3,9,27,81,128,256,512 \ # Added
  --b nd=prof le.fa ss_quant zerKfactorRF=1,2 \ # Pass a l st to do gr d search
  --b nd=prof le.fa ss_quant zerNprobe=128 \ # Added
  --b nd=prof le. tr c=Cos ne \
  --b nd=prof le. ndex_ d_type= nt \
  --b nd=prof le.embedd ng_d  ns on=3 \
  --b nd=prof le.concurrency_level=8 \
  --b nd=prof le.loadtest_type=remote \
  --b nd=prof le.serv ce_dest nat on=/srv#/stag ng/local/apoorvs/ann-server-test \
  --b nd=prof le.w h_random_quer es=True \
  --b nd=prof le.random_quer es_count=50000 \
  --b nd=prof le.random_embedd ng_m n_value=-10.0 \
  --b nd=prof le.random_embedd ng_max_value=10.0
```

Full l st of fa ss spec f c para ters. [Exact def n  on of all ava lable para ters](https://g hub.com/facebookresearch/fa ss/blob/36f2998a6469280cef3b0afcde2036935a29aa1f/fa ss/AutoTune.cpp#L444). Please reach out  f   need to use para ters wh ch aren't shown below

```
fa ss_nprobe                = Default(Str ng, '1')
fa ss_quant zerEf           = Default(Str ng, '0')
fa ss_quant zerKfactorRF    = Default(Str ng, '0')
fa ss_quant zerNprobe       = Default(Str ng, '0')
fa ss_ht                    = Default(Str ng, '0')
```

# Query Set Generator

Sample quer es can be generated from t  embedd ngs dataset and can be used d rectly w h load test  n tab format.
To generate sample quer es `Embedd ngSampl ngJob` can be used as follows.

```bash
$ ./bazel bundle cortex-core/ent y-embedd ngs/src/scala/ma n/com/tw ter/scald ng/ut l/Embedd ngFormat:embedd ngformat-deploy

$ export  NPUT_PATH=/user/cortex/embedd ngs/user/tfwproducersg/embedd ng_datarecords_on_data/2018/05/01
$ export ENT TY_K ND=user
$ export EMBEDD NG_ NPUT_FORMAT=usertensor
$ export OUTPUT_PATH=/user/$USER/sample_embedd ngs
$ export SAMPLE_PERCENT=0.1

$ oscar hdfs \
    --screen --tee log.txt \
    --hadoop-cl ent- mory 6000 \
    --hadoop-propert es "yarn.app.mapreduce.am.res ce.mb=6000;yarn.app.mapreduce.am.command-opts='-Xmx7500m';mapreduce.map. mory.mb=7500;mapreduce.reduce.java.opts='-Xmx6000m';mapreduce.reduce. mory.mb=7500;mapred.task.t  out=36000000;" \
    --m n-spl -s ze 284217728 \
    --bundle embedd ngformat-deploy \
    --host hadoopnest1.smf1.tw ter.com \
    --tool com.tw ter.scald ng.ent yembedd ngs.ut l.Embedd ngFormat.Embedd ngSampl ngJob -- \
    --ent y_k nd $ENT TY_K ND \
    -- nput.embedd ng_path $ NPUT_PATH \
    -- nput.embedd ng_format $EMBEDD NG_ NPUT_FORMAT \
    --output.embedd ng_path $OUTPUT_PATH \
    --output.embedd ng_format tab \
    --sample_percent $SAMPLE_PERCENT
```

  w ll sample 0.1% of embedd ngs and store t m  n `tab` format to hdfs that can be d recly used as `query_set` for loadtest.

# Knn Truth Set Generator

To use load test fra work to benchmark recall,   need to spl  y  data set  nto  ndex_set, query_set and knn_truth

-  ndex_set: data that w ll be  ndexed for ann
- query_set: data that w ll be used for quer es
- truth_set: t  real nearest ne ghbor used as truth to compute recall

And also   need to f gure out t  d  ns on for y  embedd ng vectors.

KnnTruthSetGenerator can  lp to prepare data sets:

```bash
$ ./bazel bundle ann/src/ma n/scala/com/tw ter/ann/scald ng/offl ne:ann-offl ne-deploy

$ export QUERY_EMBEDD NGS_PATH=/user/cortex-mlx/off c al_examples/ann/non_p  _random_user_embedd ngs_tab_format
$ export  NDEX_EMBEDD NGS_PATH=/user/cortex-mlx/off c al_examples/ann/non_p  _random_user_embedd ngs_tab_format
$ export TRUTH_SET_PATH=/user/$USER/truth_set
$ export  NDEX_SET_PATH=/user/$USER/ ndex_set
$ export QUERY_SET_PATH=/user/$USER/query_set
$ export METR C= nnerProduct
$ export QUERY_ENT TY_K ND=user
$ export  NDEX_ENT TY_K ND=user
$ export NE GHBOURS=10

$ oscar hdfs \
  --screen --tee log.txt \
  --hadoop-cl ent- mory 6000 \
  --hadoop-propert es "yarn.app.mapreduce.am.res ce.mb=6000;yarn.app.mapreduce.am.command-opts='-Xmx7500m';mapreduce.map. mory.mb=7500;mapreduce.reduce.java.opts='-Xmx6000m';mapreduce.reduce. mory.mb=7500;mapred.task.t  out=36000000;" \
  --bundle ann-offl ne-deploy \
  --m n-spl -s ze 284217728 \
  --host hadoopnest1.smf1.tw ter.com \
  --tool com.tw ter.ann.scald ng.offl ne.KnnTruthSetGenerator -- \
  --ne ghbors $NE GHBOURS \
  -- tr c $METR C \
  --query_ent y_k nd $QUERY_ENT TY_K ND \
  --query.embedd ng_path $QUERY_EMBEDD NGS_PATH \
  --query.embedd ng_format tab \
  --query_sample_percent 50.0 \
  -- ndex_ent y_k nd $ NDEX_ENT TY_K ND \
  -- ndex.embedd ng_path $ NDEX_EMBEDD NGS_PATH \
  -- ndex.embedd ng_format tab \
  -- ndex_sample_percent 90.0 \
  --query_set_output.embedd ng_path $QUERY_SET_PATH \
  --query_set_output.embedd ng_format tab \
  -- ndex_set_output.embedd ng_path $ NDEX_SET_PATH \
  -- ndex_set_output.embedd ng_format tab \
  --truth_set_output_path $TRUTH_SET_PATH \
  --reducers 100
```

  w ll sample 90% of  ndex set embedd ngs and 50% of query embedd ngs from total and t n   w ll generate 3 datasets from t  sa  that are  ndex set, query set and true nearest ne ghb s from query to  ndex  n t  tab format.
`Note`: T  reason for us ng h gh sample percent  s due to t  fact t  sample embedd ngs dataset  s small. For real use cases query set should be really small.
Set `--reducers` accord ng to t  embedd ngs dataset s ze.

# FAQ

T re are mult ple type of `query_ d_type` and ` ndex_ d_type` that can be used. So  nat ve types l ke str ng/ nt/long or related to ent y embedd ngs
l ke t et/word/user/url... for more  nfo: [L nk](https://cg .tw ter.b z/s ce/tree/src/scala/com/tw ter/cortex/ml/embedd ngs/common/Ent yK nd.scala#n8)
