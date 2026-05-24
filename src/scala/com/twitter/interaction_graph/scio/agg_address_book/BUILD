scala_library(
    name = "agg_address_book",
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        ":interaction_graph_agg_address_book_edge_snapshot-scala",
        ":interaction_graph_agg_address_book_vertex_snapshot-scala",
        "3rdparty/jvm/com/twitter/storehaus:algebra",
        "addressbook/jobs/src/main/scala/com/twitter/addressbook/jobs/simplematches:simple_user_matches-scala",
        "beam-internal/src/main/scala/com/twitter/beam/io/dal",
        "beam-internal/src/main/scala/com/twitter/scio_internal/job",
        "beam-internal/src/main/scala/com/twitter/scio_internal/runner/dataflow",
        "consumer-data-tools/src/main/scala/com/twitter/cde/scio/dal_read",
        "src/scala/com/twitter/interaction_graph/scio/common",
    ],
)

jvm_binary(
    name = "interaction_graph_address_book_scio",
    main = "com.twitter.interaction_graph.scio.agg_address_book.InteractionGraphAddressBookJob",
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        ":agg_address_book",
    ],
)

create_datasets(
    base_name = "interaction_graph_agg_address_book_edge_snapshot",
    description = "User-user directed edges with addressbook features",
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
    base_name = "interaction_graph_agg_address_book_vertex_snapshot",
    description = "User vertex with addressbook features",
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
