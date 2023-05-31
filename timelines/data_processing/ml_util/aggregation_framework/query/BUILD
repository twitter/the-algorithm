scala_library(
    sources = ["*.scala"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "finagle/finagle-stats",
        "src/java/com/twitter/ml/api:api-base",
        "src/thrift/com/twitter/ml/api:data-scala",
        "src/thrift/com/twitter/ml/api:interpretable-model-java",
        "timelines/data_processing/ml_util/aggregation_framework/metrics",
    ],
)
