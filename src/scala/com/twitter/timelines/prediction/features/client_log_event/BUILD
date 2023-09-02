scala_library(
    sources = ["*.scala"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "src/java/com/twitter/ml/api:api-base",
        "src/scala/com/twitter/suggests/controller_data",
        "src/thrift/com/twitter/dal/personal_data:personal_data-java",
        "src/thrift/com/twitter/timelineservice/server/suggests/logging:thrift-scala",
    ],
)
