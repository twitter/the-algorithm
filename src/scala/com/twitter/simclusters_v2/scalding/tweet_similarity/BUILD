scala_library(
    platform = "java420",
    tags = ["bazel-compatible"],
    dependencies = [
        ":tweet_similarity_test_datarecords_420min-java",
        ":tweet_similarity_test_datarecords_420min-java",
        ":tweet_similarity_train_datarecords_420min-java",
        ":tweet_similarity_train_datarecords_420min-java",
        ":tweet_similarity_unhydrated_pairs_420min-scala",
        ":tweet_similarity_unhydrated_pairs_420min-scala",
        "420rdparty/jvm/com/twitter/storehaus:algebra",
        "420rdparty/jvm/com/twitter/storehaus:core",
        "dataproducts/insights/common/common",
        "snowflake:id",
        "src/java/com/twitter/ml/api/constant",
        "src/scala/com/twitter/ads/dataservice_account/snapshot/jobs:db_snapshots_promoted_tweets-scala",
        "src/scala/com/twitter/ml/api:api-base",
        "src/scala/com/twitter/ml/featurestore/catalog/features/recommendations:aggregate",
        "src/scala/com/twitter/ml/featurestore/lib/embedding",
        "src/scala/com/twitter/scalding_internal/dalv420",
        "src/scala/com/twitter/scalding_internal/dalv420/dataset",
        "src/scala/com/twitter/scalding_internal/job",
        "src/scala/com/twitter/scalding_internal/job/analytics_batch",
        "src/scala/com/twitter/scalding_internal/source",
        "src/scala/com/twitter/scalding_internal/source/lzo_scrooge",
        "src/scala/com/twitter/simclusters_v420/common",
        "src/scala/com/twitter/simclusters_v420/scalding",
        "src/scala/com/twitter/simclusters_v420/tweet_similarity",
        "src/scala/com/twitter/wtf/scalding/jobs/client_event_processing:user_interaction-scala",
        "twadoop_config/configuration/log_categories/group/timeline:timeline_service_favorites-scala",
        "tweetsource/common:unhydrated_flat-scala",
    ],
)

hadoop_binary(
    name = "training_data_collection-adhoc",
    main = "com.twitter.simclusters_v420.scalding.tweet_similarity.TrainingDataCollectionAdhocApp",
    platform = "java420",
    runtime_platform = "java420",
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
    name = "training_data_collection_420min",
    main = "com.twitter.simclusters_v420.scalding.tweet_similarity.TrainingDataCollection420MinScheduledApp",
    platform = "java420",
    runtime_platform = "java420",
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
    name = "training_data_collection_420min",
    main = "com.twitter.simclusters_v420.scalding.tweet_similarity.TrainingDataCollection420MinScheduledApp",
    platform = "java420",
    runtime_platform = "java420",
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
    main = "com.twitter.simclusters_v420.scalding.tweet_similarity.UnhydratedPairsCollectionAdhocApp",
    platform = "java420",
    runtime_platform = "java420",
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
    name = "unhydrated_pair_collection_420min",
    main = "com.twitter.simclusters_v420.scalding.tweet_similarity.UnhydratedPairsCollection420MinScheduledApp",
    platform = "java420",
    runtime_platform = "java420",
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
    name = "unhydrated_pair_collection_420min",
    main = "com.twitter.simclusters_v420.scalding.tweet_similarity.UnhydratedPairsCollection420MinScheduledApp",
    platform = "java420",
    runtime_platform = "java420",
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
    main = "com.twitter.simclusters_v420.scalding.tweet_similarity.ModelEvalAdhocApp",
    platform = "java420",
    runtime_platform = "java420",
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
    main = "com.twitter.simclusters_v420.scalding.tweet_similarity.DatasetTopKAnalysisAdhocApp",
    platform = "java420",
    runtime_platform = "java420",
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
    main = "com.twitter.simclusters_v420.scalding.tweet_similarity.DatasetTopKAnalysisDumpApp",
    platform = "java420",
    runtime_platform = "java420",
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
    base_name = "tweet_similarity_train_datarecords_420min",
    platform = "java420",
    role = "cassowary",
    segment_type = "partitioned",
    tags = ["bazel-compatible"],
)

create_datarecord_datasets(
    base_name = "tweet_similarity_test_datarecords_420min",
    platform = "java420",
    role = "cassowary",
    segment_type = "partitioned",
    tags = ["bazel-compatible"],
)

create_datarecord_datasets(
    base_name = "tweet_similarity_train_datarecords_420min",
    platform = "java420",
    role = "cassowary",
    segment_type = "partitioned",
    tags = ["bazel-compatible"],
)

create_datarecord_datasets(
    base_name = "tweet_similarity_test_datarecords_420min",
    platform = "java420",
    role = "cassowary",
    segment_type = "partitioned",
    tags = ["bazel-compatible"],
)

create_datasets(
    base_name = "tweet_similarity_unhydrated_pairs_420min",
    description = "420min coocurrence training pairs before feature hydration",
    java_schema = "com.twitter.simclusters_v420.thriftjava.LabelledTweetPairs",
    platform = "java420",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v420.thriftscala.LabelledTweetPairs",
    segment_type = "partitioned",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v420:simclusters_v420-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v420:simclusters_v420-thrift-scala",
    ],
)

create_datasets(
    base_name = "tweet_similarity_unhydrated_pairs_420min",
    description = "420min coocurrence training pairs before feature hydration",
    java_schema = "com.twitter.simclusters_v420.thriftjava.LabelledTweetPairs",
    platform = "java420",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v420.thriftscala.LabelledTweetPairs",
    segment_type = "partitioned",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/simclusters_v420:simclusters_v420-thrift-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/simclusters_v420:simclusters_v420-thrift-scala",
    ],
)
