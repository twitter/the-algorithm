scala_library(
    sources = [
        "*.scala",
        "common/*.scala",
    ],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "escherbird/src/scala/com/twitter/escherbird/scalding/source",
        "flockdb-tools/datasets/flock:flock-blocks-edges-scala",
        "flockdb-tools/datasets/flock:flock-follows-edges-scala",
        "flockdb-tools/datasets/flock:flock-report-as-abuse-edges-scala",
        "flockdb-tools/datasets/flock:flock-report-as-spam-edges-scala",
        "iesource/processing/events/src/main/scala/com/twitter/iesource/processing/events/batch:server_engagements-scala",
        "interests-ds/src/main/scala/com/twitter/interests_ds/jobs/interests_service",
        "interests-ds/src/main/scala/com/twitter/interests_ds/jobs/interests_service:user_topic_relation_snapshot-scala",
        "src/java/com/twitter/common/text/language:locale-util",
        "src/scala/com/twitter/frigate/data_pipeline/scalding/magicrecs/magicrecs_notification_lite:magicrecs_notification_lite_1day_lag-scala",
        "src/scala/com/twitter/onboarding/relevance/source:utt_account_recommendations-scala",
        "src/scala/com/twitter/penguin/scalding/datasets:penguin_user_languages-scala",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources:simclusters_v2_embeddings_lite-scala",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/presto_hdfs_sources",
        "src/scala/com/twitter/simclusters_v2/scalding/common",
        "src/scala/com/twitter/simclusters_v2/scalding/common/matrix",
        "src/scala/com/twitter/wtf/entity_real_graph/common",
        "src/scala/com/twitter/wtf/entity_real_graph/scalding/common",
        "src/scala/com/twitter/wtf/scalding/jobs/common:execution_app",
        "src/scala/com/twitter/wtf/scalding/jobs/common:sources",
        "src/scala/com/twitter/wtf/scalding/jobs/common:stats_util",
        "src/thrift/com/twitter/hermit/candidate:hermit-candidate-scala",
        "src/thrift/com/twitter/onboarding/relevance/candidates:candidates-scala",
        "src/thrift/com/twitter/recos/entities:entities-thrift-scala",
        "src/thrift/com/twitter/search/adaptive/scribing:adaptive-scribing-scala",
        "src/thrift/com/twitter/wtf/entity_real_graph:entity_real_graph-thrift-scala",
        "twadoop_config/configuration/log_categories/group/search:adaptive_search-scala",
        "usersource/snapshot/src/main/scala/com/twitter/usersource/snapshot/flat:usersource_flat-scala",
        "usersource/snapshot/src/main/thrift/com/twitter/usersource/snapshot/flat:flat-scala",
    ],
)

hadoop_binary(
    name = "entity_embeddings_job-adhoc",
    main = "com.twitter.simclusters_v2.scalding.embedding.EntityToSimClustersEmbeddingAdhocApp",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":embedding"],
)

hadoop_binary(
    name = "entity_per_language_embeddings_job-adhoc",
    main = "com.twitter.simclusters_v2.scalding.embedding.LocaleEntitySimClustersEmbeddingAdhocApp",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":embedding"],
)

hadoop_binary(
    name = "semantic_core_entity_embeddings_dec11_model_job",
    main = "com.twitter.simclusters_v2.scalding.embedding.SemanticCoreEntityEmbeddingsDec11ModelApp",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":embedding"],
)

hadoop_binary(
    name = "semantic_core_entity_embeddings_2020_job",
    main = "com.twitter.simclusters_v2.scalding.embedding.SemanticCoreEntityEmbeddings2020App",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":embedding"],
)

hadoop_binary(
    name = "semantic_core_entity_embeddings_per_language_job",
    main = "com.twitter.simclusters_v2.scalding.embedding.LocaleEntitySimClustersEmbeddingScheduledApp",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":embedding"],
)

hadoop_binary(
    name = "locale_entity_simclusters_embedding_v2",
    main = "com.twitter.simclusters_v2.scalding.embedding.LocaleEntitySimClustersEmbeddingV2ScheduledApp",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":embedding"],
)

hadoop_binary(
    name = "locale_entity_simclusters_embedding_v2-adhoc",
    main = "com.twitter.simclusters_v2.scalding.embedding.LocaleEntitySimClustersEmbeddingV2AdhocApp",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":embedding"],
)

hadoop_binary(
    name = "producer_embeddings_from_interested_in-adhoc",
    main = "com.twitter.simclusters_v2.scalding.embedding.ProducerEmbeddingsFromInterestedInAdhocApp",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":embedding"],
)

hadoop_binary(
    name = "producer_embeddings_from_interested_in_by_fav_score",
    main = "com.twitter.simclusters_v2.scalding.embedding.ProducerEmbeddingsFromInterestedInByFavScoreBatchApp",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":embedding"],
)

hadoop_binary(
    name = "producer_embeddings_from_interested_in_by_fav_score_2020",
    main = "com.twitter.simclusters_v2.scalding.embedding.ProducerEmbeddingsFromInterestedInByFavScore2020BatchApp",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":embedding"],
)

hadoop_binary(
    name = "producer_embeddings_from_interested_in_by_fav_score_dec11",
    main = "com.twitter.simclusters_v2.scalding.embedding.ProducerEmbeddingsFromInterestedInByFavScoreDec11BatchApp",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":embedding"],
)

hadoop_binary(
    name = "producer_embeddings_from_interested_in_by_follow_score",
    main = "com.twitter.simclusters_v2.scalding.embedding.ProducerEmbeddingsFromInterestedInByFollowScoreBatchApp",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":embedding"],
)

hadoop_binary(
    name = "producer_embeddings_from_interested_in_by_follow_score_2020",
    main = "com.twitter.simclusters_v2.scalding.embedding.ProducerEmbeddingsFromInterestedInByFollowScore2020BatchApp",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":embedding"],
)

hadoop_binary(
    name = "producer_embeddings_from_interested_in_by_follow_score_dec11",
    main = "com.twitter.simclusters_v2.scalding.embedding.ProducerEmbeddingsFromInterestedInByFollowScoreDec11BatchApp",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":embedding"],
)

hadoop_binary(
    name = "similar_users_by_simclusters_embeddings-adhoc",
    main = "com.twitter.simclusters_v2.scalding.embedding.SimilarUsersBySimClustersEmbeddingAdhocApp",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":embedding"],
)

hadoop_binary(
    name = "similar_users_by_simclusters_embeddings",
    main = "com.twitter.simclusters_v2.scalding.embedding.SimilarUsersBySimClustersEmbeddingBatchApp",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":embedding"],
)

hadoop_binary(
    name = "entity_embedding_from_producer_embedding-adhoc",
    main = "com.twitter.simclusters_v2.scalding.embedding.EntityEmbeddingFromProducerEmbeddingAdhocJob",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":embedding"],
)

hadoop_binary(
    name = "entity_embedding_from_producer_embedding_job",
    main = "com.twitter.simclusters_v2.scalding.embedding.EntityEmbeddingFromProducerEmbeddingScheduledJob",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":embedding"],
)

# Generated with `capesospy-v2 create_target similar_users_by_simclusters_embeddings_job src/scala/com/twitter/simclusters_v2/capesos_config/atla_proc3.yaml`, config hash b8cf4d.
scalding_job(
    name = "similar_users_by_simclusters_embeddings_job",
    main = "com.twitter.simclusters_v2.scalding.embedding.SimiliarUsersBySimClustersEmbeddingBatchApp",
    config = [
        ("hadoop.combine-input", "true"),
        ("hadoop.map.jvm.total-memory", "3072m"),
        ("hadoop.queue", "cassowary.default"),
        ("hadoop.reduce.jvm.total-memory", "3072m"),
        ("hadoop.submitter.jvm.total-memory", "5120m"),
        ("submitter.tier", "preemptible"),
    ],
    cron = "15 * * * *",
    hadoop_cluster = "atla-proc3",
    platform = "java8",
    role = "cassowary",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":embedding"],
)

hadoop_binary(
    name = "global_simclusters_language_embedding_job",
    main = "com.twitter.simclusters_v2.scalding.embedding.GlobalSimClustersLanguageEmbeddingBatchApp",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":embedding"],
)
