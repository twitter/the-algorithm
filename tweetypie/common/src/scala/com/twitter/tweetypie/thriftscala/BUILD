scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "finagle/finagle-core/src/main",
        "scrooge/scrooge-core/src/main/scala",
        "tweetypie/common/src/thrift/com/twitter/tweetypie:service-scala",
        "util/util-core:scala",
    ],
)
