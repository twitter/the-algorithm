scala_library(
    sources = ["*.scala"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "graph-feature-service/src/main/thrift/com/twitter/graph_feature_service:graph_feature_service_thrift-scala",
        "src/java/com/twitter/ml/api:api-base",
        "src/thrift/com/twitter/dal/personal_data:personal_data-java",
    ],
)
