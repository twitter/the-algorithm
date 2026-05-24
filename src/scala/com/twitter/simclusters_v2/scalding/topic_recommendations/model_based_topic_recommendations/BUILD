scala_library(
    sources = ["*.scala"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        ":topic_recommendations_test_datarecords-java",
        ":topic_recommendations_train_datarecords-java",
        "escherbird/src/scala/com/twitter/escherbird/scalding/jobs/exportentities:entities-scala",
        "interests-ds/src/main/scala/com/twitter/interests_ds/jobs/interests_service",
        "interests-ds/src/main/scala/com/twitter/interests_ds/jobs/interests_service:user_topic_relation_snapshot-scala",
        "src/java/com/twitter/ml/api/constant",
        "src/scala/com/twitter/scalding_internal/dalv2",
        "src/scala/com/twitter/scalding_internal/dalv2/dataset",
        "src/scala/com/twitter/scalding_internal/job",
        "src/scala/com/twitter/scalding_internal/job/analytics_batch",
        "src/scala/com/twitter/scalding_internal/source",
        "src/scala/com/twitter/scalding_internal/source/lzo_scrooge",
        "src/scala/com/twitter/simclusters_v2/candidate_source",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources",
        "src/scala/com/twitter/simclusters_v2/scalding/common",
        "timelines/data_processing/jobs/metrics/per_topic_metrics:per_topic_aggregate_engagement-scala",
        "twml/runtime/src/main/scala/com/twitter/twml/runtime/scalding",
    ],
)

hadoop_binary(
    name = "training_data_for_topic_recommendations-adhoc",
    main = "com.twitter.simclusters_v2.scalding.topic_recommendations.model_based_topic_recommendations.UserTopicFeatureHydrationAdhocApp",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":model_based_topic_recommendations",
    ],
)

hadoop_binary(
    name = "training_data_for_topic_recommendations",
    main = "com.twitter.simclusters_v2.scalding.topic_recommendations.model_based_topic_recommendations.UserTopicFeatureHydrationScheduledApp",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":model_based_topic_recommendations",
    ],
)

create_datarecord_datasets(
    base_name = "topic_recommendations_train_datarecords",
    platform = "java8",
    role = "cassowary",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
)

create_datarecord_datasets(
    base_name = "topic_recommendations_test_datarecords",
    platform = "java8",
    role = "cassowary",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
)
