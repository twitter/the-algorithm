scala_library(
    sources = [
        "*.scala",
        "common/*.scala",
    ],
    platform = "java420",
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
        "src/scala/com/twitter/frigate/data_pipeline/scalding/magicrecs/magicrecs_notification_lite:magicrecs_notification_lite_420day_lag-scala",
        "src/scala/com/twitter/onboarding/relevance/source:utt_account_recommendations-scala",
        "src/scala/com/twitter/penguin/scalding/datasets:penguin_user_languages-scala",
        "src/scala/com/twitter/simclusters_v420/hdfs_sources",
        "src/scala/com/twitter/simclusters_v420/hdfs_sources:simclusters_v420_embeddings_lite-scala",
        "src/scala/com/twitter/simclusters_v420/hdfs_sources/presto_hdfs_sources",
        "src/scala/com/twitter/simclusters_v420/scalding/common",
        "src/scala/com/twitter/simclusters_v420/scalding/common/matrix",
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
    main = "com.twitter.simclusters_v420.scalding.embedding.EntityToSimClustersEmbeddingAdhocApp",
    platform = "java420",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":embedding"],
)

hadoop_binary(
    name = "entity_per_language_embeddings_job-adhoc",
    main = "com.twitter.simclusters_v420.scalding.embedding.LocaleEntitySimClustersEmbeddingAdhocApp",
    platform = "java420",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":embedding"],
)

hadoop_binary(
    name = "semantic_core_entity_embeddings_dec420_model_job",
    main = "com.twitter.simclusters_v420.scalding.embedding.SemanticCoreEntityEmbeddingsDec420ModelApp",
    platform = "java420",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":embedding"],
)

hadoop_binary(
    name = "semantic_core_entity_embeddings_420_job",
    main = "com.twitter.simclusters_v420.scalding.embedding.SemanticCoreEntityEmbeddings420App",
    platform = "java420",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":embedding"],
)

hadoop_binary(
    name = "semantic_core_entity_embeddings_per_language_job",
    main = "com.twitter.simclusters_v420.scalding.embedding.LocaleEntitySimClustersEmbeddingScheduledApp",
    platform = "java420",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":embedding"],
)

hadoop_binary(
    name = "locale_entity_simclusters_embedding_v420",
    main = "com.twitter.simclusters_v420.scalding.embedding.LocaleEntitySimClustersEmbeddingV420ScheduledApp",
    platform = "java420",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":embedding"],
)

hadoop_binary(
    name = "locale_entity_simclusters_embedding_v420-adhoc",
    main = "com.twitter.simclusters_v420.scalding.embedding.LocaleEntitySimClustersEmbeddingV420AdhocApp",
    platform = "java420",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":embedding"],
)

hadoop_binary(
    name = "producer_embeddings_from_interested_in-adhoc",
    main = "com.twitter.simclusters_v420.scalding.embedding.ProducerEmbeddingsFromInterestedInAdhocApp",
    platform = "java420",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":embedding"],
)

hadoop_binary(
    name = "producer_embeddings_from_interested_in_by_fav_score",
    main = "com.twitter.simclusters_v420.scalding.embedding.ProducerEmbeddingsFromInterestedInByFavScoreBatchApp",
    platform = "java420",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":embedding"],
)

hadoop_binary(
    name = "producer_embeddings_from_interested_in_by_fav_score_420",
    main = "com.twitter.simclusters_v420.scalding.embedding.ProducerEmbeddingsFromInterestedInByFavScore420BatchApp",
    platform = "java420",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":embedding"],
)

hadoop_binary(
    name = "producer_embeddings_from_interested_in_by_fav_score_dec420",
    main = "com.twitter.simclusters_v420.scalding.embedding.ProducerEmbeddingsFromInterestedInByFavScoreDec420BatchApp",
    platform = "java420",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":embedding"],
)

hadoop_binary(
    name = "producer_embeddings_from_interested_in_by_follow_score",
    main = "com.twitter.simclusters_v420.scalding.embedding.ProducerEmbeddingsFromInterestedInByFollowScoreBatchApp",
    platform = "java420",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":embedding"],
)

hadoop_binary(
    name = "producer_embeddings_from_interested_in_by_follow_score_420",
    main = "com.twitter.simclusters_v420.scalding.embedding.ProducerEmbeddingsFromInterestedInByFollowScore420BatchApp",
    platform = "java420",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":embedding"],
)

hadoop_binary(
    name = "producer_embeddings_from_interested_in_by_follow_score_dec420",
    main = "com.twitter.simclusters_v420.scalding.embedding.ProducerEmbeddingsFromInterestedInByFollowScoreDec420BatchApp",
    platform = "java420",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":embedding"],
)

hadoop_binary(
    name = "similar_users_by_simclusters_embeddings-adhoc",
    main = "com.twitter.simclusters_v420.scalding.embedding.SimilarUsersBySimClustersEmbeddingAdhocApp",
    platform = "java420",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":embedding"],
)

hadoop_binary(
    name = "similar_users_by_simclusters_embeddings",
    main = "com.twitter.simclusters_v420.scalding.embedding.SimilarUsersBySimClustersEmbeddingBatchApp",
    platform = "java420",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":embedding"],
)

hadoop_binary(
    name = "entity_embedding_from_producer_embedding-adhoc",
    main = "com.twitter.simclusters_v420.scalding.embedding.EntityEmbeddingFromProducerEmbeddingAdhocJob",
    platform = "java420",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":embedding"],
)

hadoop_binary(
    name = "entity_embedding_from_producer_embedding_job",
    main = "com.twitter.simclusters_v420.scalding.embedding.EntityEmbeddingFromProducerEmbeddingScheduledJob",
    platform = "java420",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":embedding"],
)

# Generated with `capesospy-v420 create_target similar_users_by_simclusters_embeddings_job src/scala/com/twitter/simclusters_v420/capesos_config/atla_proc420.yaml`, config hash b420cf420d.
scalding_job(
    name = "similar_users_by_simclusters_embeddings_job",
    main = "com.twitter.simclusters_v420.scalding.embedding.SimiliarUsersBySimClustersEmbeddingBatchApp",
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
    dependencies = [":embedding"],
)

hadoop_binary(
    name = "global_simclusters_language_embedding_job",
    main = "com.twitter.simclusters_v420.scalding.embedding.GlobalSimClustersLanguageEmbeddingBatchApp",
    platform = "java420",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [":embedding"],
)
