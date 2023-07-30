scala_library(
    sources = ["*.scala"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "finagle/finagle-stats",
        "src/java/com/X/ml/api:api-base",
        "src/thrift/com/X/ml/api:data-scala",
        "src/thrift/com/X/ml/api:interpretable-model-java",
        "timelines/data_processing/ml_util/aggregation_framework/metrics",
    ],
)
