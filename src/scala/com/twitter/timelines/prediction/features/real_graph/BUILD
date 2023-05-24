scala_library(
    sources = ["*.scala"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "src/java/com/twitter/ml/api:api-base",
        "src/scala/com/twitter/ml/featurestore/catalog/entities/core",
        "src/scala/com/twitter/ml/featurestore/catalog/entities/timelines",
        "src/scala/com/twitter/ml/featurestore/catalog/features/timelines:realgraph",
        "src/scala/com/twitter/ml/featurestore/lib/entity",
        "src/scala/com/twitter/ml/featurestore/lib/feature",
        "src/thrift/com/twitter/dal/personal_data:personal_data-java",
        "src/thrift/com/twitter/timelines/real_graph:real_graph-scala",
    ],
)
