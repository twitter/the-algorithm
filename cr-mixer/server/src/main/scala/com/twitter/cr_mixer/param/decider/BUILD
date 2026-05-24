scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/javax/inject:javax.inject",
        "decider/src/main/scala",
        "finagle/finagle-base-http/src/main",
        "finagle/finagle-core/src/main",
        "finagle/finagle-http/src/main/scala",
        "servo/decider",
        "src/scala/com/twitter/simclusters_v2/common",
    ],
)
