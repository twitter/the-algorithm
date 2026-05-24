scala_library(
    sources = ["*.scala"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "src/java/com/twitter/ml/api:api-base",
        "src/thrift/com/twitter/tweetypie:tweet-scala",
    ],
)

scala_library(
    name = "escherbird-features",
    sources = ["EscherbirdFeatures.scala"],
    tags = ["bazel-only"],
    dependencies = [
        "src/java/com/twitter/ml/api:api-base",
        "src/thrift/com/twitter/dal/personal_data:personal_data-java",
    ],
)
