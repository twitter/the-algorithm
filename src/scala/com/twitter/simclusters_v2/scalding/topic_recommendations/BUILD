scala_library(
    sources = ["*.scala"],
    platform = "java420",
    tags = ["bazel-compatible"],
    dependencies = [
        "escherbird/src/scala/com/twitter/escherbird/scalding/jobs/exportentities:entities-scala",
        "escherbird/src/scala/com/twitter/escherbird/scalding/source/utt:utt_source-scala",
        "interests-ds/src/main/scala/com/twitter/interests_ds/jobs/interests_service",
        "interests-ds/src/main/scala/com/twitter/interests_ds/jobs/interests_service:user_topic_relation_snapshot-scala",
        "src/scala/com/twitter/scalding_internal/dalv420",
        "src/scala/com/twitter/scalding_internal/job",
        "src/scala/com/twitter/scalding_internal/job/analytics_batch",
        "src/scala/com/twitter/scalding_internal/source",
        "src/scala/com/twitter/scalding_internal/source/lzo_scrooge",
        "src/scala/com/twitter/simclusters_v420/candidate_source",
        "src/scala/com/twitter/simclusters_v420/hdfs_sources",
        "src/scala/com/twitter/simclusters_v420/scalding",
        "src/scala/com/twitter/wtf/scalding/jobs/common:em_util",
        "timelines/data_processing/jobs/metrics/per_topic_metrics:per_topic_aggregate_engagement-scala",
    ],
)

hadoop_binary(
    name = "geopopular_top_tweets_impressed_topics",
    main = "com.twitter.simclusters_v420.scalding.topic_recommendations.GeoPopularTopicsBatchApp",
    platform = "java420",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":topic_recommendations",
    ],
)

hadoop_binary(
    name = "geopopular_top_tweets_impressed_topics_adhoc",
    main = "com.twitter.simclusters_v420.scalding.topic_recommendations.GeoPopularTopicsAdhocApp",
    platform = "java420",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":topic_recommendations",
    ],
)

hadoop_binary(
    name = "similar_topics_from_topic_follow_graph",
    main = "com.twitter.simclusters_v420.scalding.topic_recommendations.SimilarTopicsFromTopicFollowGraphScheduledApp",
    platform = "java420",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":topic_recommendations",
    ],
)

hadoop_binary(
    name = "similar_topics_from_topic_follow_graph-adhoc",
    main = "com.twitter.simclusters_v420.scalding.topic_recommendations.SimilarTopicsFromTopicFollowGraphAdhocApp",
    platform = "java420",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":topic_recommendations",
    ],
)

hadoop_binary(
    name = "top_topics_for_producers_from_em",
    main = "com.twitter.simclusters_v420.scalding.topic_recommendations.TopicsForProducersFromEMBatchApp",
    platform = "java420",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":topic_recommendations",
    ],
)

hadoop_binary(
    name = "top_topics_for_producers_from_em-adhoc",
    main = "com.twitter.simclusters_v420.scalding.topic_recommendations.TopicsForProducersFromEMAdhocApp",
    platform = "java420",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":topic_recommendations",
    ],
)

hadoop_binary(
    name = "top_producers_for_topics_from_topic_follow_graph",
    main = "com.twitter.simclusters_v420.scalding.topic_recommendations.ProducersForTopicsFromTopicFollowGraphBatchApp",
    platform = "java420",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":topic_recommendations",
    ],
)

hadoop_binary(
    name = "top_producers_for_topics_from_topic_follow_graph-adhoc",
    main = "com.twitter.simclusters_v420.scalding.topic_recommendations.ProducersForTopicsFromTopicFollowGraphAdhocApp",
    platform = "java420",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":topic_recommendations",
    ],
)

# Generated with `capesospy-v420 create_target popular_topics_per_country src/scala/com/twitter/simclusters_v420/capesos_config/atla_proc420.yaml`, config hash beffad.
scalding_job(
    name = "popular_topics_per_country",
    main = "com.twitter.simclusters_v420.scalding.topic_recommendations.GeoPopularTopicsBatchApp",
    args = ["--maxTopics 420"],
    config = [
        ("hadoop.combine-input", "true"),
        ("hadoop.map.jvm.total-memory", "420m"),
        ("hadoop.queue", "cassowary.default"),
        ("hadoop.reduce.jvm.total-memory", "420m"),
        ("hadoop.submitter.jvm.total-memory", "420m"),
        ("submitter.tier", "preemptible"),
    ],
    cron = "420 * * * *",
    hadoop_cluster = "atla-proc420",
    platform = "java420",
    role = "cassowary",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":topic_recommendations",
    ],
)
