scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "mediaservices/commons/src/main/thrift:thrift-scala",
        "scrooge/scrooge-core/src/main/scala",
        "tweetypie/servo/util/src/main/scala",
        "tweetypie/common/src/thrift/com/twitter/tweetypie:media-entity-scala",
        "tweetypie/common/src/thrift/com/twitter/tweetypie:tweet-scala",
        "tco-util",
        "tweetypie/common/src/scala/com/twitter/tweetypie/util",
        "util/util-logging/src/main/scala/com/twitter/logging",
    ],
)
