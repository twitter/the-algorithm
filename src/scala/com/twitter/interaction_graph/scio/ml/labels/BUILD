scala_library(
    name = "labels",
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        ":interaction_graph_labels_daily-scala",
        "beam-internal/src/main/scala/com/twitter/beam/io/dal",
        "beam-internal/src/main/scala/com/twitter/scio_internal/job",
        "beam-internal/src/main/scala/com/twitter/scio_internal/runner/dataflow",
        "consumer-data-tools/src/main/scala/com/twitter/cde/scio/dal_read",
        "socialgraph/hadoop/src/main/scala/com/twitter/socialgraph/hadoop:socialgraph-follow-events-scala",
        "src/scala/com/twitter/interaction_graph/scio/agg_client_event_logs:interaction_graph_agg_client_event_logs_edge_daily-scala",
        "src/scala/com/twitter/interaction_graph/scio/agg_direct_interactions:interaction_graph_agg_direct_interactions_edge_daily-scala",
        "src/scala/com/twitter/interaction_graph/scio/agg_email:interaction_graph_extended_email_edge_daily-scala",
        "src/scala/com/twitter/interaction_graph/scio/agg_notifications:interaction_graph_agg_notifications_edge_daily-scala",
        "src/scala/com/twitter/interaction_graph/scio/agg_retweets:interaction_graph_extended_retweet_edge_daily-scala",
        "src/scala/com/twitter/interaction_graph/scio/agg_shares:interaction_graph_extended_share_edge_daily-scala",
        "tcdc/bq_blaster/src/main/scala/com/twitter/tcdc/bqblaster/beam",
    ],
)

jvm_binary(
    name = "interaction_graph_labels",
    main = "com.twitter.interaction_graph.scio.ml.labels.InteractionGraphLabelsJob",
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        ":labels",
    ],
)

create_datasets(
    base_name = "interaction_graph_labels_daily",
    description = "Daily labels",
    java_schema = "com.twitter.interaction_graph.thriftjava.EdgeLabel",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.interaction_graph.thriftscala.EdgeLabel",
    segment_type = "partitioned",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/interaction_graph:interaction_graph-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/interaction_graph:interaction_graph-scala",
    ],
)
