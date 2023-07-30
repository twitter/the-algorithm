scala_library(
    sources = [
        "*.scala",
    ],
    compiler_option_sets = ["fatal_warnings"],
    tags = ["bazel-compatible"],
    dependencies = [
        "kafka/finagle-kafka/finatra-kafka/src/main/scala",
        "src/thrift/com/X/socialgraph:thrift-scala",
        "unified_user_actions/adapter/src/main/scala/com/X/unified_user_actions/adapter:base",
        "unified_user_actions/adapter/src/main/scala/com/X/unified_user_actions/adapter/common",
        "unified_user_actions/thrift/src/main/thrift/com/X/unified_user_actions:unified_user_actions-scala",
    ],
)
