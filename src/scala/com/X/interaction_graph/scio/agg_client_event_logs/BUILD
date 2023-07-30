scala_library(
    name = "agg_client_event_logs",
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        ":interaction_graph_agg_client_event_logs_edge_daily-scala",
        ":interaction_graph_agg_client_event_logs_vertex_daily-scala",
        "beam-internal/src/main/scala/com/X/beam/io/dal",
        "beam-internal/src/main/scala/com/X/scio_internal/job",
        "beam-internal/src/main/scala/com/X/scio_internal/runner/dataflow",
        "consumer-data-tools/src/main/scala/com/X/cde/scio/dal_read",
        "src/scala/com/X/interaction_graph/scio/common",
        "src/scala/com/X/wtf/scalding/jobs/client_event_processing:user_interaction-scala",
        "usersource/snapshot/src/main/scala/com/X/usersource/snapshot/combined:usersource-scala",
    ],
)

jvm_binary(
    name = "interaction_graph_client_event_logs_scio",
    main = "com.X.interaction_graph.scio.agg_client_event_logs.InteractionGraphClientEventLogsJob",
    platform = "java8",
    dependencies = [
        ":agg_client_event_logs",
    ],
)

create_datasets(
    base_name = "interaction_graph_agg_client_event_logs_edge_daily",
    description = "User-user directed edges with client events features",
    java_schema = "com.X.interaction_graph.thriftjava.Edge",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.X.interaction_graph.thriftscala.Edge",
    segment_type = "partitioned",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/X/interaction_graph:interaction_graph-java",
    ],
    scala_dependencies = [
        "src/thrift/com/X/interaction_graph:interaction_graph-scala",
    ],
)

create_datasets(
    base_name = "interaction_graph_agg_client_event_logs_vertex_daily",
    description = "User vertex with client events features",
    java_schema = "com.X.interaction_graph.thriftjava.Vertex",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.X.interaction_graph.thriftscala.Vertex",
    segment_type = "partitioned",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/X/interaction_graph:interaction_graph-java",
    ],
    scala_dependencies = [
        "src/thrift/com/X/interaction_graph:interaction_graph-scala",
    ],
)
