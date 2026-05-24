scala_library(
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "tweetypie/servo/repo",
        "tweetypie/servo/util",
        "snowflake:id",
        "tweetypie/server/src/main/scala/com/twitter/tweetypie",
        "tweetypie/server/src/main/scala/com/twitter/tweetypie/core",
        "tweetypie/server/src/main/thrift:compiled-scala",
        "util/util-slf4j-api/src/main/scala/com/twitter/util/logging",
    ],
)
