scala_library(
    sources = ["DeciderGates.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/google/guava",
        "decider",
        "finagle/finagle-toggle/src/main/scala/com/twitter/finagle/server",
        "tweetypie/servo/decider",
        "tweetypie/servo/util/src/main/scala",
        "util/util-core:scala",
    ],
)
