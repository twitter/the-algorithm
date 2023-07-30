scala_library(
    sources = [
        "*.scala",
    ],
    tags = ["bazel-compatible"],
    dependencies = [
        "common-internal/analytics/client-analytics-data-layer/src/main/scala",
        "kafka/finagle-kafka/finatra-kafka/src/main/scala",
        "src/scala/com/X/loggedout/analytics/common",
        "src/thrift/com/X/clientapp/gen:clientapp-scala",
        "twadoop_config/configuration/log_categories/group/scribelib:client_event-scala",
        "unified_user_actions/adapter/src/main/scala/com/X/unified_user_actions/adapter:base",
        "unified_user_actions/adapter/src/main/scala/com/X/unified_user_actions/adapter/common",
        "unified_user_actions/thrift/src/main/thrift/com/X/unified_user_actions:unified_user_actions-scala",
    ],
)
