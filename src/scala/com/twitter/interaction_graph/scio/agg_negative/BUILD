scala_library(
    name = "agg_negative",
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        ":real_graph_negative_features-scala",
        "beam-internal/src/main/scala/com/twitter/beam/io/dal",
        "beam-internal/src/main/scala/com/twitter/scio_internal/job",
        "beam-internal/src/main/scala/com/twitter/scio_internal/runner/dataflow",
        "flockdb-tools/datasets/flock:flock-blocks-edges-scala",
        "flockdb-tools/datasets/flock:flock-mutes-edges-scala",
        "flockdb-tools/datasets/flock:flock-report-as-abuse-edges-scala",
        "flockdb-tools/datasets/flock:flock-report-as-spam-edges-scala",
        "socialgraph/hadoop/src/main/scala/com/twitter/socialgraph/hadoop:socialgraph-unfollows-scala",
        "src/scala/com/twitter/interaction_graph/scio/common",
        "tcdc/bq_blaster/src/main/scala/com/twitter/tcdc/bqblaster/beam",
    ],
)

jvm_binary(
    name = "interaction_graph_negative_scio",
    main = "com.twitter.interaction_graph.scio.agg_negative.InteractionGraphNegativeJob",
    platform = "java8",
    dependencies = [
        ":agg_negative",
    ],
)

create_datasets(
    base_name = "real_graph_negative_features",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.interaction_graph.injection.UserSessionInjection.injection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.user_session_store.thriftscala.UserSession",
    scala_dependencies = [
        "src/scala/com/twitter/interaction_graph/injection:user_session_inj",
    ],
)
