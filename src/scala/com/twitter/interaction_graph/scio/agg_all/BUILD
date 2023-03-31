scala_library(
    name = "agg_all",
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        ":interaction_graph_history_aggregated_raw_edge_daily-scala",
        ":interaction_graph_history_aggregated_vertex_daily-scala",
        ":interaction_graph_aggregated_edge_daily-scala",
        ":interaction_graph_aggregated_vertex_daily-scala",
        ":interaction_graph_history_aggregated_edge_snapshot-scala",
        ":interaction_graph_history_aggregated_vertex_snapshot-scala",
        ":real_graph_features-scala",
        "beam-internal/src/main/scala/com/twitter/beam/io/dal",
        "beam-internal/src/main/scala/com/twitter/scio_internal/job",
        "beam-internal/src/main/scala/com/twitter/scio_internal/runner/dataflow",
        "consumer-data-tools/src/main/scala/com/twitter/cde/scio/dal_read",
        "src/scala/com/twitter/interaction_graph/scio/agg_address_book:interaction_graph_agg_address_book_edge_snapshot-scala",
        "src/scala/com/twitter/interaction_graph/scio/agg_address_book:interaction_graph_agg_address_book_vertex_snapshot-scala",
        "src/scala/com/twitter/interaction_graph/scio/agg_client_event_logs:interaction_graph_agg_client_event_logs_edge_daily-scala",
        "src/scala/com/twitter/interaction_graph/scio/agg_client_event_logs:interaction_graph_agg_client_event_logs_vertex_daily-scala",
        "src/scala/com/twitter/interaction_graph/scio/agg_direct_interactions:interaction_graph_agg_direct_interactions_edge_daily-scala",
        "src/scala/com/twitter/interaction_graph/scio/agg_direct_interactions:interaction_graph_agg_direct_interactions_vertex_daily-scala",
        "src/scala/com/twitter/interaction_graph/scio/agg_flock:interaction_graph_agg_flock_edge_snapshot-scala",
        "src/scala/com/twitter/interaction_graph/scio/agg_flock:interaction_graph_agg_flock_vertex_snapshot-scala",
        "src/scala/com/twitter/interaction_graph/scio/common",
        "src/scala/com/twitter/interaction_graph/scio/ml/scores:real_graph_in_scores-scala",
        "src/scala/com/twitter/interaction_graph/scio/ml/scores:real_graph_oon_scores-scala",
        "src/scala/com/twitter/wtf/dataflow/user_events:valid_user_follows-scala",
        "src/thrift/com/twitter/wtf/candidate:wtf-candidate-scala",
        "tcdc/bq_blaster/src/main/scala/com/twitter/tcdc/bqblaster/beam",
        "usersource/snapshot/src/main/scala/com/twitter/usersource/snapshot/flat:usersource_flat-scala",
    ],
)

create_datasets(
    base_name = "interaction_graph_history_aggregated_raw_edge_daily",
    description = "User-user directed edges with all features",
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
    base_name = "interaction_graph_history_aggregated_vertex_daily",
    description = "User vertex with all features",
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

jvm_binary(
    name = "interaction_graph_aggregation_job_scio",
    main = "com.twitter.interaction_graph.scio.agg_all.InteractionGraphAggregationJob",
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        ":agg_all",
    ],
)

create_datasets(
    base_name = "interaction_graph_history_aggregated_edge_snapshot",
    description = "User-user directed edges with all features",
    java_schema = "com.twitter.interaction_graph.thriftjava.Edge",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.interaction_graph.thriftscala.Edge",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/interaction_graph:interaction_graph-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/interaction_graph:interaction_graph-scala",
    ],
)

create_datasets(
    base_name = "interaction_graph_history_aggregated_vertex_snapshot",
    description = "User vertex with all features",
    java_schema = "com.twitter.interaction_graph.thriftjava.Vertex",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.interaction_graph.thriftscala.Vertex",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    java_dependencies = [
        "src/thrift/com/twitter/interaction_graph:interaction_graph-java",
    ],
    scala_dependencies = [
        "src/thrift/com/twitter/interaction_graph:interaction_graph-scala",
    ],
)

create_datasets(
    base_name = "interaction_graph_aggregated_edge_daily",
    description = "User-user directed edges with all features",
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
    base_name = "interaction_graph_aggregated_vertex_daily",
    description = "User vertex with all features",
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

create_datasets(
    base_name = "real_graph_features",
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

create_datasets(
    base_name = "home_light_ranker_top_k_real_graph_features",
    key_type = "Long",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.interaction_graph.injection.EdgeListInjection.injection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.interaction_graph.thriftscala.EdgeList",
    scala_dependencies = [
        "src/scala/com/twitter/interaction_graph/injection:edge_list_injection",
    ],
)
