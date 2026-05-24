scala_library(
    sources = ["*.scala"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "src/thrift/com/twitter/escherbird:tweet-annotation-scala",
        "src/thrift/com/twitter/timelines/content_features:thrift-scala",
        "src/thrift/com/twitter/tweetypie:media-entity-scala",
        "src/thrift/com/twitter/tweetypie:tweet-scala",
    ],
    exports = [
        "src/thrift/com/twitter/timelines/content_features:thrift-scala",
    ],
)
