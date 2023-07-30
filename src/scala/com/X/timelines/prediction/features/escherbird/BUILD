scala_library(
    sources = ["*.scala"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "src/java/com/X/ml/api:api-base",
        "src/thrift/com/X/tweetypie:tweet-scala",
    ],
)

scala_library(
    name = "escherbird-features",
    sources = ["EscherbirdFeatures.scala"],
    tags = ["bazel-only"],
    dependencies = [
        "src/java/com/X/ml/api:api-base",
        "src/thrift/com/X/dal/personal_data:personal_data-java",
    ],
)
