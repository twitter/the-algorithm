scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "tweetypie/servo/util",
        "tweetypie/common/src/thrift/com/twitter/tweetypie:media-entity-scala",
        "tweetypie/common/src/thrift/com/twitter/tweetypie:tweet-scala",
        "tco-util",
        "tweetypie/common/src/scala/com/twitter/tweetypie/tweettext",
        "tweetypie/common/src/scala/com/twitter/tweetypie/util",
        "twitter-text/lib/java/src/main/java/com/twitter/twittertext",
    ],
)
