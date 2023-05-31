scala_library(
    sources = ["*.scala"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/twitter/algebird:core",
        "3rdparty/jvm/com/twitter/algebird:util",
        "3rdparty/jvm/com/twitter/storehaus:algebra",
        "3rdparty/jvm/com/twitter/storehaus:core",
        "3rdparty/src/jvm/com/twitter/scalding:commons",
        "3rdparty/src/jvm/com/twitter/scalding:core",
        "3rdparty/src/jvm/com/twitter/summingbird:batch",
        "3rdparty/src/jvm/com/twitter/summingbird:core",
        "src/java/com/twitter/ml/api:api-base",
        "src/thrift/com/twitter/ml/api:data-java",
        "src/thrift/com/twitter/ml/api:interpretable-model-java",
        "timelines/data_processing/ml_util/aggregation_framework:common_types",
    ],
)
