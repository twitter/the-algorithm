scala_library(
    sources = [
        "*.scala",
    ],
    tags = ["bazel-compatible"],
    dependencies = [
        "client-events/thrift/src/thrift/storage/twitter/behavioral_event:behavioral_event-scala",
        "kafka/finagle-kafka/finatra-kafka/src/main/scala",
        "unified_user_actions/adapter/src/main/scala/com/twitter/unified_user_actions/adapter:base",
        "unified_user_actions/adapter/src/main/scala/com/twitter/unified_user_actions/adapter/common",
        "unified_user_actions/thrift/src/main/thrift/com/twitter/unified_user_actions:unified_user_actions-scala",
    ],
)
