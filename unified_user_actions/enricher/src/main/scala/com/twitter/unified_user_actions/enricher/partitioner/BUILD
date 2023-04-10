scala_library(
    name = "default",
    sources = ["DefaultPartitioner.scala"],
    tags = ["bazel-compatible"],
    dependencies = [
        ":base",
        "unified_user_actions/enricher/src/main/thrift/com/twitter/unified_user_actions/enricher/internal:internal-scala",
    ],
)

scala_library(
    name = "base",
    sources = ["Partitioner.scala"],
    tags = ["bazel-compatible"],
    dependencies = [
        "unified_user_actions/enricher/src/main/thrift/com/twitter/unified_user_actions/enricher/internal:internal-scala",
    ],
)
