# Loadtest ANN query service with random embeddings

An ANN query service can be load-tested with random embeddings as queries, generated automatically by loadtest tool.
Example script to load test a ANN query service with random embeddings:

```bash
$ aurora job create smf1/<role>/staging/ann-loadtest-service ann/src/main/aurora/loadtest/loadtest.aurora \
  --bind=profile.name=ann-loadtest-service \
  --bind=profile.role=<role> \
  --bind=profile.duration_sec=10 \
  --bind=profile.number_of_neighbors=10 \
  --bind=profile.qps=200 \
  --bind=profile.algo=hnsw \
  --bind=profile.metric=Cosine \
  --bind=profile.index_id_type=int \
  --bind=profile.hnsw_ef=400,600,800 \
  --bind=profile.embedding_dimension=3 \
  --bind=profile.concurrency_level=8 \
  --bind=profile.loadtest_type=remote \
  --bind=profile.service_destination=/srv#/staging/local/apoorvs/ann-server-test \
  --bind=profile.with_random_queries=True \
  --bind=profile.random_queries_count=50000 \
  --bind=profile.random_embedding_min_value=-10.0 \
  --bind=profile.random_embedding_max_value=10.0
```

It will run the loadtest with `50000` random embeddings, where each embedding value will be range bounded between `random_embedding_min_value` and `random_embedding_max_value`.
In the above the case it will be bounded between `-10.0` and `10.0`.
If `random_embedding_min_value` and `random_embedding_max_value` are not supplied default value of `-1.0` and `1.0` will be used.

## Results

Load test results will be printed to stdout of an aurora job.

# Loadtest ANN query service with query set

An ANN query service can be load-tested with sample queries drawn from the embeddings dataset.
For creating sample queries i.e `query_set` refer this [section](#query-set-generator).

Test is run with `live` version of loadtest binary that is already available in packer.
Example script to load test a ANN query service:

```bash
$ aurora job create smf1/<role>/staging/ann-loadtest-service ann/src/main/aurora/loadtest/loadtest.aurora \
  --bind=profile.name=ann-loadtest-service \
  --bind=profile.role=<role> \
  --bind=profile.duration_sec=10 \
  --bind=profile.query_set_dir=hdfs:///user/cortex/ann_example/dataset/search/query_knn/query_set \
  --bind=profile.number_of_neighbors=10 \
  --bind=profile.qps=200 \
  --bind=profile.algo=hnsw \
  --bind=profile.query_id_type=string \
  --bind=profile.index_id_type=string \
  --bind=profile.metric=Cosine \
  --bind=profile.hnsw_ef=400,600,800 \
  --bind=profile.embedding_dimension=100 \
  --bind=profile.concurrency_level=8 \
  --bind=profile.loadtest_type=remote \
  --bind=profile.service_destination=/srv#/staging/local/apoorvs/ann-server-test
```

# In-Memory based loadtest for measuring recall

Load test can be with the above created dataset in memory.
For running in in-memory mode, index is created in memory, and for that you need `query_set/index_set/truth_set`.
For creating this dataset refer this [section](#knn-truth-set-generator).

Test is run with `live` version loadtest binary that is already available in packer.
Example script In-Memory index building and benchmarking:

```bash
$ aurora job create smf1/<role>/staging/ann-loadtest ann/src/main/aurora/loadtest/loadtest.aurora \
  --bind=profile.name=ann-loadtest \
  --bind=profile.role=<role> \
  --bind=profile.duration_sec=10 \
  --bind=profile.truth_set_dir=hdfs:///user/cortex/ann_example/dataset/search/query_knn/true_knn \
  --bind=profile.query_set_dir=hdfs:///user/cortex/ann_example/dataset/search/query_knn/query_set \
  --bind=profile.index_set_dir=hdfs:///user/cortex/ann_example/dataset/search/query_knn/index_set \
  --bind=profile.number_of_neighbors=10 \
  --bind=profile.qps=200 \
  --bind=profile.algo=hnsw \
  --bind=profile.query_id_type=string \
  --bind=profile.index_id_type=string \
  --bind=profile.metric=Cosine \
  --bind=profile.hnsw_ef_construction=15 \
  --bind=profile.hnsw_max_m=10 \
  --bind=profile.hnsw_ef=400,600,800 \
  --bind=profile.embedding_dimension=100 \
  --bind=profile.concurrency_level=8 \
  --bind=profile.loadtest_type=local
```

# Loadtest faiss

```bash
$ aurora job create smf1/<role>/staging/ann-loadtest-service ann/src/main/aurora/loadtest/loadtest.aurora \
  --bind=profile.name=ann-loadtest-service \
  --bind=profile.role=<role> \
  --bind=profile.duration_sec=10 \
  --bind=profile.number_of_neighbors=10 \
  --bind=profile.qps=200 \
  --bind=profile.algo=faiss \ # Changed to faiss
  --bind=profile.faiss_nprobe=1,3,9,27,81,128,256,512 \ # Added
  --bind=profile.faiss_quantizerKfactorRF=1,2 \ # Pass a list to do grid search
  --bind=profile.faiss_quantizerNprobe=128 \ # Added
  --bind=profile.metric=Cosine \
  --bind=profile.index_id_type=int \
  --bind=profile.embedding_dimension=3 \
  --bind=profile.concurrency_level=8 \
  --bind=profile.loadtest_type=remote \
  --bind=profile.service_destination=/srv#/staging/local/apoorvs/ann-server-test \
  --bind=profile.with_random_queries=True \
  --bind=profile.random_queries_count=50000 \
  --bind=profile.random_embedding_min_value=-10.0 \
  --bind=profile.random_embedding_max_value=10.0
```

Full list of faiss specific parameters. [Exact definition of all available parameters](https://github.com/facebookresearch/faiss/blob/36f2998a6469280cef3b0afcde2036935a29aa1f/faiss/AutoTune.cpp#L444). Please reach out if you need to use parameters which aren't shown below

```
faiss_nprobe                = Default(String, '1')
faiss_quantizerEf           = Default(String, '0')
faiss_quantizerKfactorRF    = Default(String, '0')
faiss_quantizerNprobe       = Default(String, '0')
faiss_ht                    = Default(String, '0')
```

# Query Set Generator

Sample queries can be generated from the embeddings dataset and can be used directly with load test in tab format.
To generate sample queries `EmbeddingSamplingJob` can be used as follows.

```bash
$ ./bazel bundle cortex-core/entity-embeddings/src/scala/main/com/twitter/scalding/util/EmbeddingFormat:embeddingformat-deploy

$ export INPUT_PATH=/user/cortex/embeddings/user/tfwproducersg/embedding_datarecords_on_data/2018/05/01
$ export ENTITY_KIND=user
$ export EMBEDDING_INPUT_FORMAT=usertensor
$ export OUTPUT_PATH=/user/$USER/sample_embeddings
$ export SAMPLE_PERCENT=0.1

$ oscar hdfs \
    --screen --tee log.txt \
    --hadoop-client-memory 6000 \
    --hadoop-properties "yarn.app.mapreduce.am.resource.mb=6000;yarn.app.mapreduce.am.command-opts='-Xmx7500m';mapreduce.map.memory.mb=7500;mapreduce.reduce.java.opts='-Xmx6000m';mapreduce.reduce.memory.mb=7500;mapred.task.timeout=36000000;" \
    --min-split-size 284217728 \
    --bundle embeddingformat-deploy \
    --host hadoopnest1.smf1.twitter.com \
    --tool com.twitter.scalding.entityembeddings.util.EmbeddingFormat.EmbeddingSamplingJob -- \
    --entity_kind $ENTITY_KIND \
    --input.embedding_path $INPUT_PATH \
    --input.embedding_format $EMBEDDING_INPUT_FORMAT \
    --output.embedding_path $OUTPUT_PATH \
    --output.embedding_format tab \
    --sample_percent $SAMPLE_PERCENT
```

It will sample 0.1% of embeddings and store them in `tab` format to hdfs that can be direcly used as `query_set` for loadtest.

# Knn Truth Set Generator

To use load test framework to benchmark recall, you need to split your data set into index_set, query_set and knn_truth

- index_set: data that will be indexed for ann
- query_set: data that will be used for queries
- truth_set: the real nearest neighbor used as truth to compute recall

And also you need to figure out the dimension for your embedding vectors.

KnnTruthSetGenerator can help to prepare data sets:

```bash
$ ./bazel bundle ann/src/main/scala/com/twitter/ann/scalding/offline:ann-offline-deploy

$ export QUERY_EMBEDDINGS_PATH=/user/cortex-mlx/official_examples/ann/non_pii_random_user_embeddings_tab_format
$ export INDEX_EMBEDDINGS_PATH=/user/cortex-mlx/official_examples/ann/non_pii_random_user_embeddings_tab_format
$ export TRUTH_SET_PATH=/user/$USER/truth_set
$ export INDEX_SET_PATH=/user/$USER/index_set
$ export QUERY_SET_PATH=/user/$USER/query_set
$ export METRIC=InnerProduct
$ export QUERY_ENTITY_KIND=user
$ export INDEX_ENTITY_KIND=user
$ export NEIGHBOURS=10

$ oscar hdfs \
  --screen --tee log.txt \
  --hadoop-client-memory 6000 \
  --hadoop-properties "yarn.app.mapreduce.am.resource.mb=6000;yarn.app.mapreduce.am.command-opts='-Xmx7500m';mapreduce.map.memory.mb=7500;mapreduce.reduce.java.opts='-Xmx6000m';mapreduce.reduce.memory.mb=7500;mapred.task.timeout=36000000;" \
  --bundle ann-offline-deploy \
  --min-split-size 284217728 \
  --host hadoopnest1.smf1.twitter.com \
  --tool com.twitter.ann.scalding.offline.KnnTruthSetGenerator -- \
  --neighbors $NEIGHBOURS \
  --metric $METRIC \
  --query_entity_kind $QUERY_ENTITY_KIND \
  --query.embedding_path $QUERY_EMBEDDINGS_PATH \
  --query.embedding_format tab \
  --query_sample_percent 50.0 \
  --index_entity_kind $INDEX_ENTITY_KIND \
  --index.embedding_path $INDEX_EMBEDDINGS_PATH \
  --index.embedding_format tab \
  --index_sample_percent 90.0 \
  --query_set_output.embedding_path $QUERY_SET_PATH \
  --query_set_output.embedding_format tab \
  --index_set_output.embedding_path $INDEX_SET_PATH \
  --index_set_output.embedding_format tab \
  --truth_set_output_path $TRUTH_SET_PATH \
  --reducers 100
```

It will sample 90% of index set embeddings and 50% of query embeddings from total and then it will generate 3 datasets from the same that are index set, query set and true nearest neighbours from query to index in the tab format.
`Note`: The reason for using high sample percent is due to the fact the sample embeddings dataset is small. For real use cases query set should be really small.
Set `--reducers` according to the embeddings dataset size.

# FAQ

There are multiple type of `query_id_type` and `index_id_type` that can be used. Some native types like string/int/long or related to entity embeddings
like tweet/word/user/url... for more info: [Link](https://cgit.twitter.biz/source/tree/src/scala/com/twitter/cortex/ml/embeddings/common/EntityKind.scala#n8)
