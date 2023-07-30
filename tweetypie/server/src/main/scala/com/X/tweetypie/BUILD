scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "mediaservices/commons/src/main/thrift:thrift-scala",
        "tweetypie/servo/util",
        "snowflake:id",
        "src/thrift/com/X/gizmoduck:thrift-scala",
        "src/thrift/com/X/gizmoduck:user-thrift-scala",
        "tweetypie/common/src/thrift/com/X/tweetypie:media-entity-scala",
        "tweetypie/common/src/thrift/com/X/tweetypie:tweet-scala",
        "tweetypie/server/src/main/thrift:compiled-scala",
        "tweetypie/common/src/scala/com/X/tweetypie/util",
        "util/util-slf4j-api",
    ],
)
