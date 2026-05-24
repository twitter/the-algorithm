scala_library(
    name = "agg_flock",
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        ":interaction_graph_agg_flock_edge_snapshot-scala",
        ":interaction_graph_agg_flock_vertex_snapshot-scala",
        "3rdparty/jvm/com/twitter/storehaus:algebra",
        "beam-internal/src/main/scala/com/twitter/beam/io/dal",
        "beam-internal/src/main/scala/com/twitter/scio_internal/job",
        "beam-internal/src/main/scala/com/twitter/scio_internal/runner/dataflow",
        "consumer-data-tools/src/main/scala/com/twitter/cde/scio/dal_read",
        "flockdb-tools/datasets/flock:flock-blocks-edges-scala",
        "flockdb-tools/datasets/flock:flock-mutes-edges-scala",
        "flockdb-tools/datasets/flock:flock-report-as-abuse-edges-scala",
        "flockdb-tools/datasets/flock:flock-report-as-spam-edges-scala",
        "src/scala/com/twitter/interaction_graph/scio/common",
        "src/scala/com/twitter/wtf/dataflow/user_events:valid_user_follows-scala",
        "src/thrift/com/twitter/core_workflows/user_model:user_model-scala",
        "src/thrift/com/twitter/twadoop/user/gen:gen-java",
        "src/thrift/com/twitter/twadoop/user/gen:gen-scala",
        "usersource/snapshot/src/main/scala/com/twitter/usersource/snapshot/combined:usersource-scala",
    ],
)

jvm_binary(
    name = "interaction_graph_agg_flock_scio",
    main = "com.twitter.interaction_graph.scio.agg_flock.InteractionGraphAggFlockJob",
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        ":agg_flock",
    ],
)

create_datasets(
    base_name = "interaction_graph_agg_flock_edge_snapshot",
    description = "User-user directed edges with flock features",
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
    base_name = "interaction_graph_agg_flock_vertex_snapshot",
    description = "User vertex with flock features",
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
