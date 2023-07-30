scala_library(
    sources = ["*.scala"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "src/java/com/X/ml/api:api-base",
        "src/scala/com/X/suggests/controller_data",
        "src/thrift/com/X/dal/personal_data:personal_data-java",
        "src/thrift/com/X/timelineservice/server/suggests/logging:thrift-scala",
    ],
)
