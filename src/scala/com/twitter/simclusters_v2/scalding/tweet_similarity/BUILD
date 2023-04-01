scala_library(
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        ":tweet_similarity_test_datarecords_120min-java",
        ":tweet_similarity_test_datarecords_30min-java",
        ":tweet_similarity_train_datarecords_120min-java",
        ":tweet_similarity_train_datarecords_30min-java",
        ":tweet_similarity_unhydrated_pairs_120min-scala",
        ":tweet_similarity_unhydrated_pairs_30min-scala",
        "3rdparty/jvm/com/twitter/storehaus:algebra",
        "3rdparty/jvm/com/twitter/storehaus:core",
        "dataproducts/insights/common/common",
        "snowflake:id",
        "src/java/com/twitter/ml/api/constant",
        "src/scala/com/twitter/ads/dataservice_account/snapshot/jobs:db_snapshots_promoted_tweets-scala",
        "src/scala/com/twitter/ml/api:api-base",
        "src/scala/com/twitter/ml/featurestore/catalog/features/recommendations:aggregate",
        "src/scala/com/twitter/ml/featurestore/lib/embedding",
        "src/scala/com/twitter/scalding_internal/dalv2",
        "src/scala/com/twitter/scalding_internal/dalv2/dataset",
        "src/scala/com/twitter/scalding_internal/job",
        "src/scala/com/twitter/scalding_internal/job/analytics_batch",
        "src/scala/com/twitter/scalding_internal/source",
        "src/scala/com/twitter/scalding_internal/source/lzo_scrooge",
        "src/scala/com/twitter/simclusters_v2/common",
        "src/scala/com/twitter/simclusters_v2/scalding",
        "src/scala/com/twitter/simclusters_v2/tweet_similarity",
        "src/scala/com/twitter/wtf/scalding/jobs/client_event_processing:user_interaction-scala",
        "twadoop_config/configuration/log_categories/group/timeline:timeline_service_favorites-scala",
        "tweetsource/common:unhydrated_flat-scala",
    ],
)

hadoop_binary(
    name = "training_data_collection-adhoc",
    main = "com.twitter.simclusters_v2.scalding.tweet_similarity.TrainingDataCollectionAdhocApp",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":tweet_similarity",
    ],
)

hadoop_binary(
    name = "training_data_collection_30min",
    main = "com.twitter.simclusters_v2.scalding.tweet_similarity.TrainingDataCollection30MinScheduledApp",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":tweet_similarity",
    ],
)

hadoop_binary(
    name = "training_data_collection_120min",
    main = "com.twitter.simclusters_v2.scalding.tweet_similarity.TrainingDataCollection120MinScheduledApp",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":tweet_similarity",
    ],
)

hadoop_binary(
    name = "unhydrated_pair_collection-adhoc",
    main = "com.twitter.simclusters_v2.scalding.tweet_similarity.UnhydratedPairsCollectionAdhocApp",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":tweet_similarity",
    ],
)

hadoop_binary(
    name = "unhydrated_pair_collection_30min",
    main = "com.twitter.simclusters_v2.scalding.tweet_similarity.UnhydratedPairsCollection30MinScheduledApp",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":tweet_similarity",
    ],
)

hadoop_binary(
    name = "unhydrated_pair_collection_120min",
    main = "com.twitter.simclusters_v2.scalding.tweet_similarity.UnhydratedPairsCollection120MinScheduledApp",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":tweet_similarity",
    ],
)

hadoop_binary(
    name = "model_eval-adhoc",
    main = "com.twitter.simclusters_v2.scalding.tweet_similarity.ModelEvalAdhocApp",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":tweet_similarity",
    ],
)

hadoop_binary(
    name = "dataset_topk_analysis-adhoc",
    main = "com.twitter.simclusters_v2.scalding.tweet_similarity.DatasetTopKAnalysisAdhocApp",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":tweet_similarity",
    ],
)

hadoop_binary(
    name = "dataset_topk_analysis_dump-adhoc",
    main = "com.twitter.simclusters_v2.scalding.tweet_similarity.DatasetTopKAnalysisDumpApp",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":tweet_similarity",
    ],
)

create_datarecord_datasets(
    base_name = "tweet_similarity_train_datarecords_30min",
    platform = "java8",
    role = "cassowary",
    segment_type = "partitioned",
    tags = ["bazel-compatible"],
)

create_datarecord_datasets(
    base_name = "tweet_similarity_test_datarecords_30min",
    platform = "java8",
    role = "cassowary",
    segment_type = "partitioned",
    tags = ["bazel-compatible"],
)

create_datarecord_datasets(
    base_name = "tweet_similarity_train_datarecords_120min",
    platform = "java8",
    role = "cassowary",
    segment_type = "partitioned",
    tags = ["bazel-compatible"],
)

create_datarecord_datasets(
    base_name = "tweet_similarity_test_datarecords_120min",
    platform = "java8",
    role = "cassowary",
    segment_type = "partitioned",
    tags = ["bazel-compatible"],
)

create_datasets(
    base_name = "tweet_similarity_unhydrated_pairs_30min",
    description = "30min coocurrence training pairs before feature hydration",
    java_schema = "com.twitter.simclusters_v2.thriftjava.LabelledTweetPairs",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.LabelledTweetPairs",
    segment_type = "partitioned",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)

create_datasets(
    base_name = "tweet_similarity_unhydrated_pairs_120min",
    description = "120min coocurrence training pairs before feature hydration",
    java_schema = "com.twitter.simclusters_v2.thriftjava.LabelledTweetPairs",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.thriftscala.LabelledTweetPairs",
    segment_type = "partitioned",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
    ],
)
