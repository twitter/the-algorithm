scala_library(
    name = "agg_notifications",
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        ":interaction_graph_agg_notifications_edge_daily-scala",
        ":interaction_graph_agg_notifications_vertex_daily-scala",
        "beam-internal/src/main/scala/com/twitter/beam/io/dal",
        "beam-internal/src/main/scala/com/twitter/scio_internal/job",
        "beam-internal/src/main/scala/com/twitter/scio_internal/runner/dataflow",
        "consumer-data-tools/src/main/scala/com/twitter/cde/scio/dal_read",
        "src/scala/com/twitter/frigate/data_pipeline_beam/mr-client-event-filtering-job/src/main/scala/com/twitter/client_event_filtering:frigate_filtered_client_events_dataflow-scala",
        "src/scala/com/twitter/interaction_graph/scio/common",
        "src/scala/com/twitter/wtf/scalding/jobs/client_event_processing:user_interaction-scala",
        "tcdc/bq_blaster/src/main/scala/com/twitter/tcdc/bqblaster/beam",
        "twadoop_config/configuration/log_categories/group/frigate:frigate_notifier-scala",
        "tweetsource/public_tweets/src/main/scala/com/twitter/tweetsource/public_tweets:public_tweets-scala",
        "usersource/snapshot/src/main/scala/com/twitter/usersource/snapshot/combined:usersource-scala",
    ],
)

jvm_binary(
    name = "interaction_graph_notifications_scio",
    main = "com.twitter.interaction_graph.scio.agg_notifications.InteractionGraphNotificationsJob",
    platform = "java8",
    dependencies = [
        ":agg_notifications",
    ],
)

create_datasets(
    base_name = "interaction_graph_agg_notifications_edge_daily",
    description = "User-user directed edges with notification features",
    java_schema = "com.twitter.interaction_graph.thriftjava.Edge",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.interaction_graph.thriftscala.Edge",
    segment_type = "partitioned",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/interaction_graph:interaction_graph-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/interaction_graph:interaction_graph-scala",
    ],
)

create_datasets(
    base_name = "interaction_graph_agg_notifications_vertex_daily",
    description = "User vertex with notification features",
    java_schema = "com.twitter.interaction_graph.thriftjava.Vertex",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.interaction_graph.thriftscala.Vertex",
    segment_type = "partitioned",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/interaction_graph:interaction_graph-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/interaction_graph:interaction_graph-scala",
    ],
)
