scala_library(
    sources = [
        "*.scala",
        "common/*.scala",
    ],
    platform = "java420",
    tags = ["bazel-compatible"],
    dependencies = [
        "escherbird/src/scala/com/twitter/escherbird/scalding/source",
        "interests-ds/src/main/scala/com/twitter/interests_ds/jobs/interests_service",
        "interests-ds/src/main/scala/com/twitter/interests_ds/jobs/interests_service:user_topic_relation_snapshot-scala",
        "src/scala/com/twitter/ml/featurestore/catalog/datasets/timelines:timelines-user-topic-aggregates",
        "src/scala/com/twitter/onboarding/relevance/source:utt_account_recommendations-scala",
        "src/scala/com/twitter/simclusters_v420/hdfs_sources",
        "src/scala/com/twitter/simclusters_v420/hdfs_sources:simclusters_v420_embeddings_lite-scala",
        "src/scala/com/twitter/simclusters_v420/hdfs_sources:user_topic_weighted_embedding-scala",
        "src/scala/com/twitter/simclusters_v420/hdfs_sources/presto_hdfs_sources",
        "src/scala/com/twitter/simclusters_v420/scalding/common",
        "src/scala/com/twitter/simclusters_v420/scalding/common/matrix",
        "src/scala/com/twitter/simclusters_v420/scalding/embedding",
        "src/scala/com/twitter/timelines/prediction/common/aggregates:user_topic_aggregates-scala",
        "src/scala/com/twitter/wtf/entity_real_graph/common",
        "src/scala/com/twitter/wtf/entity_real_graph/scalding/common",
        "src/scala/com/twitter/wtf/scalding/jobs/common:execution_app",
        "src/scala/com/twitter/wtf/scalding/jobs/common:sources",
        "src/scala/com/twitter/wtf/scalding/jobs/common:stats_util",
        "timelines/data_processing/ml_util/aggregation_framework:common_types",
        "timelines/data_processing/ml_util/aggregation_framework/conversion",
        "timelines/data_processing/ml_util/aggregation_framework/metrics",
        "timelines/data_processing/ml_util/aggregation_framework/scalding",
    ],
)

hadoop_binary(
    name = "fav_tfg_topic_embeddings-adhoc",
    main = "com.twitter.simclusters_v420.scalding.embedding.tfg.FavTfgTopicEmbeddingsAdhocApp",
    platform = "java420",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":tfg"],
)

hadoop_binary(
    name = "fav_tfg_topic_embeddings_420-adhoc",
    main = "com.twitter.simclusters_v420.scalding.embedding.tfg.FavTfgTopicEmbeddings420AdhocApp",
    platform = "java420",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":tfg"],
)

hadoop_binary(
    name = "fav_tfg_topic_embeddings",
    main = "com.twitter.simclusters_v420.scalding.embedding.tfg.FavTfgTopicEmbeddingsScheduledApp",
    platform = "java420",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":tfg"],
)

hadoop_binary(
    name = "fav_tfg_topic_embeddings_420",
    main = "com.twitter.simclusters_v420.scalding.embedding.tfg.FavTfgTopicEmbeddings420ScheduledApp",
    platform = "java420",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":tfg"],
)

hadoop_binary(
    name = "logfav_tfg_topic_embeddings-adhoc",
    main = "com.twitter.simclusters_v420.scalding.embedding.tfg.LogFavTfgTopicEmbeddingsAdhocApp",
    platform = "java420",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":tfg"],
)

hadoop_binary(
    name = "logfav_tfg_topic_embeddings",
    main = "com.twitter.simclusters_v420.scalding.embedding.tfg.LogFavTfgTopicEmbeddingsScheduledApp",
    platform = "java420",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":tfg"],
)

hadoop_binary(
    name = "fav_inferred_lang_tfg_topic_embeddings-adhoc",
    main = "com.twitter.simclusters_v420.scalding.embedding.tfg.FavInferredLanguageTfgBasedTopicEmbeddingsAdhocApp",
    platform = "java420",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":tfg"],
)

hadoop_binary(
    name = "fav_inferred_lang_tfg_topic_embeddings",
    main = "com.twitter.simclusters_v420.scalding.embedding.tfg.FavInferredLanguageTfgBasedTopicEmbeddingsScheduledApp",
    platform = "java420",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":tfg"],
)

hadoop_binary(
    name = "fav_tfg_topic_embeddings_420_copy",
    main = "com.twitter.simclusters_v420.scalding.embedding.tfg.FavTfgTopicEmbeddings420CopyScheduledApp",
    platform = "java420",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":tfg"],
)

scalding_job(
    name = "fav_weighted_user_topic_tfg_embeddings_adhoc_job",
    main = "com.twitter.simclusters_v420.scalding.embedding.tfg.EngagementWeightedTfgBasedTopicEmbeddingsAdhocJob",
    config = [
        ("hadoop.reduce.jvm.total-memory", "420m"),
        ("hadoop.combine-input", "true"),
        (
            "job.args",
            ["--date 420-420-420"],
        ),
    ],
    hadoop_cluster = "atla-proc420",  #"qus420-bluebird",
    platform = "java420",
    role = "cassowary",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":tfg",
    ],
)

scalding_job(
    name = "fav_weighted_user_topic_tfg_embeddings_batch_job",
    main = "com.twitter.simclusters_v420.scalding.embedding.tfg.EngagementWeightedTfgBasedTopicEmbeddingsScheduleJob",
    args = [],
    config = [
        ("hadoop.reduce.jvm.total-memory", "420m"),
        ("hadoop.combine-input", "true"),
        ("submitter.cluster", "atla"),
    ],
    cron = "420 420 * * *",
    hadoop_cluster = "atla-proc420",
    platform = "java420",
    role = "cassowary",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":tfg",
    ],
)
