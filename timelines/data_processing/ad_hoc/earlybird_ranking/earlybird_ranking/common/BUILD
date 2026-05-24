scala_library(
    name = "common",
    sources = ["*.scala"],
    platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
    ],
    dependencies = [
        "src/java/com/twitter/ml/api:api-base",
        "src/java/com/twitter/ml/api/constant",
        "src/java/com/twitter/ml/api/transform",
        "src/java/com/twitter/search/modeling/tweet_ranking",
        "src/scala/com/twitter/ml/api/util",
        "src/scala/com/twitter/timelines/prediction/features/common",
        "src/scala/com/twitter/timelines/prediction/features/itl",
        "src/scala/com/twitter/timelines/prediction/features/real_graph",
        "src/scala/com/twitter/timelines/prediction/features/recap",
        "src/scala/com/twitter/timelines/prediction/features/request_context",
        "src/scala/com/twitter/timelines/prediction/features/time_features",
        "src/thrift/com/twitter/ml/api:data-java",
        "src/thrift/com/twitter/ml/api:transform-java",
    ],
)
