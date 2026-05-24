scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "finatra-internal/mtls-thriftmux/src/main/scala",
        "finatra/inject/inject-thrift-client",
        "follow-recommendations-service/common/src/main/scala/com/twitter/follow_recommendations/common/clients/common",
        "follow-recommendations-service/common/src/main/scala/com/twitter/follow_recommendations/common/models",
        "graph-feature-service/src/main/thrift/com/twitter/graph_feature_service:graph_feature_service_thrift-scala",
        "stitch/stitch-core",
    ],
)
