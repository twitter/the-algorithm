scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/X/algebird:core",
        "beam-internal/src/main/scala/com/X/beam/io/dal",
        "beam-internal/src/main/scala/com/X/scio_internal/job",
        "beam-internal/src/main/scala/com/X/scio_internal/runner/dataflow",
        "flockdb-tools/datasets/flock/src/main/thrift:thrift-scala",
        "src/scala/com/X/pluck/source/combined_user_scrooge_source",
        "src/thrift/com/X/gizmoduck:user-thrift-scala",
        "src/thrift/com/X/interaction_graph:interaction_graph-scala",
        "src/thrift/com/X/socialgraph:thrift-scala",
        "src/thrift/com/X/twadoop/user/gen:gen-scala",
        "src/thrift/com/X/user_session_store:thrift-scala",
        "usersource/snapshot/src/main/scala/com/X/usersource/snapshot/flat:usersource_flat-scala",
    ],
)

scala_library(
    name = "feature_groups",
    sources = ["FeatureGroups.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "src/thrift/com/X/interaction_graph:interaction_graph-scala",
    ],
)
