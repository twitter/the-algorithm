scala_library(
    name = "common",
    sources = ["*.scala"],
    platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
    ],
    dependencies = [
        "src/java/com/X/ml/api:api-base",
        "src/java/com/X/ml/api/constant",
        "src/java/com/X/ml/api/transform",
        "src/java/com/X/search/modeling/tweet_ranking",
        "src/scala/com/X/ml/api/util",
        "src/scala/com/X/timelines/prediction/features/common",
        "src/scala/com/X/timelines/prediction/features/itl",
        "src/scala/com/X/timelines/prediction/features/real_graph",
        "src/scala/com/X/timelines/prediction/features/recap",
        "src/scala/com/X/timelines/prediction/features/request_context",
        "src/scala/com/X/timelines/prediction/features/time_features",
        "src/thrift/com/X/ml/api:data-java",
        "src/thrift/com/X/ml/api:transform-java",
    ],
)
