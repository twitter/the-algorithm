scala_library(
    sources = ["*.scala"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "src/java/com/twitter/ml/api:api-base",
        "src/thrift/com/twitter/dal/personal_data:personal_data-java",
        "src/thrift/com/twitter/simclusters_v2:simclusters_v2-thrift-scala",
        "src/thrift/com/twitter/timelines/suggests/common:record-scala",
        "timelines/data_processing/ml_util/aggregation_framework:common_types",
        "timelines/data_processing/ml_util/aggregation_framework/conversion:for-timelines",
    ],
)
