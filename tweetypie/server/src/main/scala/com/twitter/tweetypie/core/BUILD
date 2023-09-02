scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/twitter/bijection:core",
        "featureswitches/featureswitches-core/src/main/scala",
        "scrooge/scrooge-serializer/src/main/scala",
        "tweetypie/servo/repo",
        "tweetypie/servo/util",
        "tweetypie/common/src/thrift/com/twitter/tweetypie:service-scala",
        "tweetypie/common/src/thrift/com/twitter/tweetypie:tweet-scala",
        "stitch/stitch-core",
        "tweetypie/server/src/main/scala/com/twitter/tweetypie",
        "tweetypie/server/src/main/thrift:compiled-scala",
    ],
)
