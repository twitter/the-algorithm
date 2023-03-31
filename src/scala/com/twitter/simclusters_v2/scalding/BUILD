scala_library(
    sources = ["*.scala"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/fasterxml/jackson:jackson-module-scala",
        "3rdparty/jvm/com/fasterxml/jackson/core:jackson-core",
        "3rdparty/jvm/com/fasterxml/jackson/core:jackson-databind",
        "3rdparty/jvm/com/fasterxml/jackson/module:jackson-module-scala",
        "3rdparty/jvm/com/googlecode/matrix-toolkits-java",
        "3rdparty/jvm/com/twitter/storehaus:algebra",
        "3rdparty/jvm/com/twitter/storehaus:core",
        "escherbird/src/scala/com/twitter/escherbird/scalding/source",
        "flockdb-tools/datasets/flock:flock-follows-edges-scala",
        "src/java/com/twitter/ml/api/constant",
        "src/java/com/twitter/sbf/core",
        "src/java/com/twitter/sbf/graph",
        "src/scala/com/twitter/frigate/user_sampler/common",
        "src/scala/com/twitter/ml/api:api-base",
        "src/scala/com/twitter/ml/api/bq",
        "src/scala/com/twitter/pluck/source/cassowary:sims",
        "src/scala/com/twitter/pluck/source/core_workflows/user_model:condensed_user_state-scala",
        "src/scala/com/twitter/scalding_internal/dalv2",
        "src/scala/com/twitter/scalding_internal/job",
        "src/scala/com/twitter/scalding_internal/job/analytics_batch",
        "src/scala/com/twitter/scalding_internal/source",
        "src/scala/com/twitter/scalding_internal/source/lzo_scrooge",
        "src/scala/com/twitter/simclusters_v2/candidate_source",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources",
        "src/scala/com/twitter/simclusters_v2/scalding/common",
        "src/scala/com/twitter/simclusters_v2/summingbird/common",
        "src/scala/com/twitter/timelines/prediction/features/common",
        "src/scala/com/twitter/timelines/prediction/features/itl",
        "src/scala/com/twitter/timelines/prediction/features/recap",
        "src/scala/com/twitter/wtf/entity_real_graph/scalding/common",
        "src/thrift/com/twitter/hermit/candidate:hermit-candidate-scala",
        "src/thrift/com/twitter/wtf/scalding/sims:sims-thrift-scala",
        "twadoop_config/configuration/log_categories/group/recos-platform:content_recommender_get_content_recommendations-scala",
        "twadoop_config/configuration/log_categories/group/recos-platform:content_recommender_get_topic_tweets_recommendations-scala",
        "twadoop_config/configuration/log_categories/group/timeline:timeline_service_favorites-scala",
        "usersource/snapshot/src/main/scala/com/twitter/usersource/snapshot/flat:usersource_flat-scala",
        "usersource/snapshot/src/main/thrift/com/twitter/usersource/snapshot/flat:flat-scala",
        "util/util-core:util-core-util",
    ],
)

hadoop_binary(
    name = "evd_cluster_similarity",
    main = "com.twitter.simclusters_v2.scalding.EigenVectorsForClusterSimilarityAdhoc",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":scalding",
    ],
)

hadoop_binary(
    name = "cluster_evaluation",
    main = "com.twitter.simclusters_v2.scalding.ClusterEvaluationAdhoc",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":scalding",
    ],
)

hadoop_binary(
    name = "cluster_evaluation_20m_145k",
    main = "com.twitter.simclusters_v2.scalding.ClusterEvaluationFor20M145K",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":scalding",
    ],
)

hadoop_binary(
    name = "cluster_evaluation_20m_145k_2020",
    main = "com.twitter.simclusters_v2.scalding.ClusterEvaluationFor20M145K2020",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":scalding",
    ],
)

hadoop_binary(
    name = "bp_cluster_evaluation",
    main = "com.twitter.simclusters_v2.scalding.BipartiteClusterEvaluation",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":scalding",
    ],
)

hadoop_binary(
    name = "update_knownfor",
    main = "com.twitter.simclusters_v2.scalding.UpdateKnownForAdhoc",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":scalding",
    ],
)

hadoop_binary(
    name = "update_knownfor_prod",
    main = "com.twitter.simclusters_v2.scalding.UpdateKnownFor20M145K",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":scalding",
    ],
)

hadoop_binary(
    name = "cluster_details",
    main = "com.twitter.simclusters_v2.scalding.ClusterDetailsBatch",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":scalding",
    ],
)

hadoop_binary(
    name = "cluster_details_20m_145k_updated",
    main = "com.twitter.simclusters_v2.scalding.ClusterDetails20M145KUpdated",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":scalding",
    ],
)

hadoop_binary(
    name = "cluster_details_20m_145k_2020",
    main = "com.twitter.simclusters_v2.scalding.ClusterDetails20M145K2020",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":scalding",
    ],
)

hadoop_binary(
    name = "cluster_details-adhoc",
    main = "com.twitter.simclusters_v2.scalding.ClusterDetailsAdhoc",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":scalding",
    ],
)

hadoop_binary(
    name = "cluster_details-dump",
    main = "com.twitter.simclusters_v2.scalding.DumpClusterDetailsAdhoc",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":scalding",
    ],
)

hadoop_binary(
    name = "interested_in",
    main = "com.twitter.simclusters_v2.scalding.InterestedInFromKnownForBatch",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":scalding",
    ],
)

hadoop_binary(
    name = "interested_in_from_producer_embeddings",
    main = "com.twitter.simclusters_v2.scalding.InterestedInFromProducerEmbeddingsBatchApp",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":scalding",
    ],
)

hadoop_binary(
    name = "employee_graph_from_user_user",
    main = "com.twitter.simclusters_v2.scalding.EmployeeGraphFromUserUser",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":scalding",
    ],
)

hadoop_binary(
    name = "interested_in_20m_145k_updated",
    main = "com.twitter.simclusters_v2.scalding.InterestedInFromKnownFor20M145KUpdated",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":scalding",
    ],
)

hadoop_binary(
    name = "interested_in_20m_145k_2020",
    main = "com.twitter.simclusters_v2.scalding.InterestedInFromKnownFor20M145K2020",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":scalding",
    ],
)

hadoop_binary(
    name = "interested_in_lite_20m_145k_2020",
    main = "com.twitter.simclusters_v2.scalding.InterestedInFromKnownForLite20M145K2020",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":scalding",
    ],
)

hadoop_binary(
    name = "interested_in_lite_20m_145k_2020-adhoc",
    main = "com.twitter.simclusters_v2.scalding.InterestedInFromKnownForLite20M145K2020Adhoc",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":scalding",
    ],
)

hadoop_binary(
    name = "interested_in_from_ape_2020-adhoc",
    main = "com.twitter.simclusters_v2.scalding.InterestedInFromAPE2020AdhocApp",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":scalding",
    ],
)

hadoop_binary(
    name = "interested_in_from_ape_2020",
    main = "com.twitter.simclusters_v2.scalding.InterestedInFromAPE2020BatchApp",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":scalding",
    ],
)

hadoop_binary(
    name = "known_for_to_mh",
    main = "com.twitter.simclusters_v2.scalding.KnownForToMHBatch",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":scalding",
    ],
)

hadoop_binary(
    name = "user_user_normalized_graph",
    main = "com.twitter.simclusters_v2.scalding.UserUserNormalizedGraphBatch",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":scalding",
    ],
)

hadoop_binary(
    name = "user_user_graph",
    main = "com.twitter.simclusters_v2.scalding.UserUserGraphBatch",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":scalding",
    ],
)

hadoop_binary(
    name = "user_user_graph-adhoc",
    main = "com.twitter.simclusters_v2.scalding.UserUserGraphAdhoc",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":scalding",
    ],
)

hadoop_binary(
    name = "producer_norms_and_counts",
    main = "com.twitter.simclusters_v2.scalding.ProducerNormsAndCountsBatch",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":scalding",
    ],
)

hadoop_binary(
    name = "fav_graph",
    main = "com.twitter.simclusters_v2.scalding.UserUserFavGraphBatch",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":scalding",
    ],
)

hadoop_binary(
    name = "top_users_similarity_graph",
    main = "com.twitter.simclusters_v2.scalding.TopUsersSimilarityGraphApp",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":scalding",
    ],
)

hadoop_binary(
    name = "top_users_only",
    main = "com.twitter.simclusters_v2.scalding.TopUsersOnlyApp",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":scalding",
    ],
)

hadoop_binary(
    name = "dump_fav_graph_adhoc",
    main = "com.twitter.simclusters_v2.scalding.DumpFavGraphAdhoc",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":scalding",
    ],
)

# Generated with `capesospy-v2 create_target interested_in_for_20M_145k_2020 src/scala/com/twitter/simclusters_v2/capesos_config/atla_proc.yaml`, config hash 8f19bf.
scalding_job(
    name = "interested_in_for_20M_145k_2020",
    main = "com.twitter.simclusters_v2.scalding.InterestedInFromKnownFor20M145K2020",
    args = ["--socialProofThreshold 2 --maxClustersPerUser 50"],
    config = [
        ("hadoop.combine-input", "true"),
        ("hadoop.map.jvm.total-memory", "3072m"),
        ("hadoop.reduce.jvm.total-memory", "3072m"),
        ("hadoop.submitter.jvm.total-memory", "5120m"),
        ("submitter.tier", "preemptible"),
    ],
    cron = "14 * * * *",
    hadoop_cluster = "atla-proc",
    platform = "java8",
    role = "cassowary",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":scalding",
    ],
)
