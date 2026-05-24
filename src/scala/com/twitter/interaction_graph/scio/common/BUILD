scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/twitter/algebird:core",
        "beam-internal/src/main/scala/com/twitter/beam/io/dal",
        "beam-internal/src/main/scala/com/twitter/scio_internal/job",
        "beam-internal/src/main/scala/com/twitter/scio_internal/runner/dataflow",
        "flockdb-tools/datasets/flock/src/main/thrift:thrift-scala",
        "src/scala/com/twitter/pluck/source/combined_user_scrooge_source",
        "src/thrift/com/twitter/gizmoduck:user-thrift-scala",
        "src/thrift/com/twitter/interaction_graph:interaction_graph-scala",
        "src/thrift/com/twitter/socialgraph:thrift-scala",
        "src/thrift/com/twitter/twadoop/user/gen:gen-scala",
        "src/thrift/com/twitter/user_session_store:thrift-scala",
        "usersource/snapshot/src/main/scala/com/twitter/usersource/snapshot/flat:usersource_flat-scala",
    ],
)

scala_library(
    name = "feature_groups",
    sources = ["FeatureGroups.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "src/thrift/com/twitter/interaction_graph:interaction_graph-scala",
    ],
)
