scala_library(
    sources = [
        "*.scala",
    ],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/twitter/algebird:core",
        "graphstore/common:flock_blocks-java",
        "src/java/com/twitter/search/common/features",
        "src/scala/com/twitter/ml/api:api-base",
        "src/scala/com/twitter/scalding_internal/dalv2",
        "src/scala/com/twitter/scalding_internal/dalv2/dataset",
        "src/scala/com/twitter/scalding_internal/source/lzo_scrooge",
        "src/scala/com/twitter/search/tweet_ranking/scalding/datasets:tweet_engagement_raw_training_data_daily-java",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources:adhoc_abuse_simcluster_features-scala",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources:adhoc_cross_simcluster_block_interaction_features-scala",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources:adhoc_cross_simcluster_fav_interaction_features-scala",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources:search_abuse_simcluster_features_manhattan-scala",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources:user_user_fav_graph-scala",
        "src/scala/com/twitter/simclusters_v2/scalding/common",
        "src/scala/com/twitter/simclusters_v2/scalding/common/matrix",
        "src/scala/com/twitter/simclusters_v2/scalding/embedding",
        "src/scala/com/twitter/wtf/entity_real_graph/common",
        "src/scala/com/twitter/wtf/entity_real_graph/scalding/common",
        "src/scala/com/twitter/wtf/scalding/jobs/common:cassowary_job",
        "src/scala/com/twitter/wtf/scalding/jobs/common:execution_app",
        "src/scala/com/twitter/wtf/scalding/jobs/common:sources",
        "src/scala/com/twitter/wtf/scalding/jobs/common:stats_util",
    ],
)

hadoop_binary(
    name = "abuse-adhoc",
    main = "com.twitter.simclusters_v2.scalding.embedding.abuse.AdhocAbuseScaldingJob",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":abuse"],
)

hadoop_binary(
    name = "abuse-prod",
    main = "com.twitter.simclusters_v2.scalding.embedding.abuse.SearchAbuseSimclusterFeaturesScaldingJob",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":abuse"],
)

hadoop_binary(
    name = "cross_simcluster-adhoc",
    main = "com.twitter.simclusters_v2.scalding.embedding.abuse.CrossSimClusterFeaturesScaldingJob",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":abuse",
    ],
)
