scala_library(
    sources = ["*.scala"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "src/java/com/X/ml/api:api-base",
        "src/scala/com/X/ml/featurestore/catalog/entities/core",
        "src/scala/com/X/ml/featurestore/catalog/entities/timelines",
        "src/scala/com/X/ml/featurestore/catalog/features/timelines:realgraph",
        "src/scala/com/X/ml/featurestore/lib/entity",
        "src/scala/com/X/ml/featurestore/lib/feature",
        "src/thrift/com/X/dal/personal_data:personal_data-java",
        "src/thrift/com/X/timelines/real_graph:real_graph-scala",
    ],
)
