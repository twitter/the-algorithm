scala_library(
    name = "agg_direct_interactions",
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        ":interaction_graph_agg_direct_interactions_edge_daily-scala",
        ":interaction_graph_agg_direct_interactions_vertex_daily-scala",
        "beam-internal/src/main/scala/com/twitter/beam/io/dal",
        "beam-internal/src/main/scala/com/twitter/scio_internal/job",
        "beam-internal/src/main/scala/com/twitter/scio_internal/runner/dataflow",
        "consumer-data-tools/src/main/scala/com/twitter/cde/scio/dal_read",
        "src/scala/com/twitter/interaction_graph/scio/common",
        "src/thrift/com/twitter/timelineservice/server/internal:thrift-scala",
        "twadoop_config/configuration/log_categories/group/timeline:timeline_service_favorites-scala",
        "twadoop_config/configuration/log_categories/group/tweetypie:tweetypie_media_tag_events-scala",
        "tweetsource/common:unhydrated_flat-scala",
        "usersource/snapshot/src/main/scala/com/twitter/usersource/snapshot/combined:usersource-scala",
    ],
)

jvm_binary(
    name = "interaction_graph_agg_direct_interactions_scio",
    main = "com.twitter.interaction_graph.scio.agg_direct_interactions.InteractionGraphAggDirectInteractionsJob",
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        ":agg_direct_interactions",
    ],
)

create_datasets(
    base_name = "interaction_graph_agg_direct_interactions_edge_daily",
    description = "User-user directed edges with direct interactions features",
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
    base_name = "interaction_graph_agg_direct_interactions_vertex_daily",
    description = "User vertex with direct interactions features",
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
