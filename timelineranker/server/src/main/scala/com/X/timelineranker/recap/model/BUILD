scala_library(
    sources = ["*.scala"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "src/thrift/com/X/escherbird:tweet-annotation-scala",
        "src/thrift/com/X/timelines/content_features:thrift-scala",
        "src/thrift/com/X/tweetypie:media-entity-scala",
        "src/thrift/com/X/tweetypie:tweet-scala",
    ],
    exports = [
        "src/thrift/com/X/timelines/content_features:thrift-scala",
    ],
)
