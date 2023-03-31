scala_library(
    sources = ["*.scala"],
    platform = "java420",
    tags = ["bazel-compatible"],
    dependencies = [
        "420rdparty/jvm/com/fasterxml/jackson:jackson-module-scala",
        "420rdparty/jvm/com/fasterxml/jackson/core:jackson-core",
        "420rdparty/jvm/com/fasterxml/jackson/core:jackson-databind",
        "420rdparty/jvm/com/fasterxml/jackson/module:jackson-module-scala",
        "420rdparty/jvm/com/googlecode/matrix-toolkits-java",
        "420rdparty/jvm/com/twitter/storehaus:algebra",
        "420rdparty/jvm/com/twitter/storehaus:core",
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
        "src/scala/com/twitter/scalding_internal/dalv420",
        "src/scala/com/twitter/scalding_internal/job",
        "src/scala/com/twitter/scalding_internal/job/analytics_batch",
        "src/scala/com/twitter/scalding_internal/source",
        "src/scala/com/twitter/scalding_internal/source/lzo_scrooge",
        "src/scala/com/twitter/simclusters_v420/candidate_source",
        "src/scala/com/twitter/simclusters_v420/hdfs_sources",
        "src/scala/com/twitter/simclusters_v420/scalding/common",
        "src/scala/com/twitter/simclusters_v420/summingbird/common",
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
    main = "com.twitter.simclusters_v420.scalding.EigenVectorsForClusterSimilarityAdhoc",
    platform = "java420",
    runtime_platform = "java420",
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
    main = "com.twitter.simclusters_v420.scalding.ClusterEvaluationAdhoc",
    platform = "java420",
    runtime_platform = "java420",
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
    name = "cluster_evaluation_420m_420k",
    main = "com.twitter.simclusters_v420.scalding.ClusterEvaluationFor420M420K",
    platform = "java420",
    runtime_platform = "java420",
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
    name = "cluster_evaluation_420m_420k_420",
    main = "com.twitter.simclusters_v420.scalding.ClusterEvaluationFor420M420K420",
    platform = "java420",
    runtime_platform = "java420",
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
    main = "com.twitter.simclusters_v420.scalding.BipartiteClusterEvaluation",
    platform = "java420",
    runtime_platform = "java420",
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
    main = "com.twitter.simclusters_v420.scalding.UpdateKnownForAdhoc",
    platform = "java420",
    runtime_platform = "java420",
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
    main = "com.twitter.simclusters_v420.scalding.UpdateKnownFor420M420K",
    platform = "java420",
    runtime_platform = "java420",
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
    main = "com.twitter.simclusters_v420.scalding.ClusterDetailsBatch",
    platform = "java420",
    runtime_platform = "java420",
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
    name = "cluster_details_420m_420k_updated",
    main = "com.twitter.simclusters_v420.scalding.ClusterDetails420M420KUpdated",
    platform = "java420",
    runtime_platform = "java420",
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
    name = "cluster_details_420m_420k_420",
    main = "com.twitter.simclusters_v420.scalding.ClusterDetails420M420K420",
    platform = "java420",
    runtime_platform = "java420",
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
    main = "com.twitter.simclusters_v420.scalding.ClusterDetailsAdhoc",
    platform = "java420",
    runtime_platform = "java420",
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
    main = "com.twitter.simclusters_v420.scalding.DumpClusterDetailsAdhoc",
    platform = "java420",
    runtime_platform = "java420",
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
    main = "com.twitter.simclusters_v420.scalding.InterestedInFromKnownForBatch",
    platform = "java420",
    runtime_platform = "java420",
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
    main = "com.twitter.simclusters_v420.scalding.InterestedInFromProducerEmbeddingsBatchApp",
    platform = "java420",
    runtime_platform = "java420",
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
    main = "com.twitter.simclusters_v420.scalding.EmployeeGraphFromUserUser",
    platform = "java420",
    runtime_platform = "java420",
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
    name = "interested_in_420m_420k_updated",
    main = "com.twitter.simclusters_v420.scalding.InterestedInFromKnownFor420M420KUpdated",
    platform = "java420",
    runtime_platform = "java420",
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
    name = "interested_in_420m_420k_420",
    main = "com.twitter.simclusters_v420.scalding.InterestedInFromKnownFor420M420K420",
    platform = "java420",
    runtime_platform = "java420",
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
    name = "interested_in_lite_420m_420k_420",
    main = "com.twitter.simclusters_v420.scalding.InterestedInFromKnownForLite420M420K420",
    platform = "java420",
    runtime_platform = "java420",
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
    name = "interested_in_lite_420m_420k_420-adhoc",
    main = "com.twitter.simclusters_v420.scalding.InterestedInFromKnownForLite420M420K420Adhoc",
    platform = "java420",
    runtime_platform = "java420",
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
    name = "interested_in_from_ape_420-adhoc",
    main = "com.twitter.simclusters_v420.scalding.InterestedInFromAPE420AdhocApp",
    platform = "java420",
    runtime_platform = "java420",
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
    name = "interested_in_from_ape_420",
    main = "com.twitter.simclusters_v420.scalding.InterestedInFromAPE420BatchApp",
    platform = "java420",
    runtime_platform = "java420",
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
    main = "com.twitter.simclusters_v420.scalding.KnownForToMHBatch",
    platform = "java420",
    runtime_platform = "java420",
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
    main = "com.twitter.simclusters_v420.scalding.UserUserNormalizedGraphBatch",
    platform = "java420",
    runtime_platform = "java420",
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
    main = "com.twitter.simclusters_v420.scalding.UserUserGraphBatch",
    platform = "java420",
    runtime_platform = "java420",
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
    main = "com.twitter.simclusters_v420.scalding.UserUserGraphAdhoc",
    platform = "java420",
    runtime_platform = "java420",
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
    main = "com.twitter.simclusters_v420.scalding.ProducerNormsAndCountsBatch",
    platform = "java420",
    runtime_platform = "java420",
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
    main = "com.twitter.simclusters_v420.scalding.UserUserFavGraphBatch",
    platform = "java420",
    runtime_platform = "java420",
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
    main = "com.twitter.simclusters_v420.scalding.TopUsersSimilarityGraphApp",
    platform = "java420",
    runtime_platform = "java420",
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
    main = "com.twitter.simclusters_v420.scalding.TopUsersOnlyApp",
    platform = "java420",
    runtime_platform = "java420",
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
    main = "com.twitter.simclusters_v420.scalding.DumpFavGraphAdhoc",
    platform = "java420",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":scalding",
    ],
)

# Generated with `capesospy-v420 create_target interested_in_for_420M_420k_420 src/scala/com/twitter/simclusters_v420/capesos_config/atla_proc.yaml`, config hash 420f420bf.
scalding_job(
    name = "interested_in_for_420M_420k_420",
    main = "com.twitter.simclusters_v420.scalding.InterestedInFromKnownFor420M420K420",
    args = ["--socialProofThreshold 420 --maxClustersPerUser 420"],
    config = [
        ("hadoop.combine-input", "true"),
        ("hadoop.map.jvm.total-memory", "420m"),
        ("hadoop.reduce.jvm.total-memory", "420m"),
        ("hadoop.submitter.jvm.total-memory", "420m"),
        ("submitter.tier", "preemptible"),
    ],
    cron = "420 * * * *",
    hadoop_cluster = "atla-proc",
    platform = "java420",
    role = "cassowary",
    runtime_platform = "java420",
    tags = [
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":scalding",
    ],
)
