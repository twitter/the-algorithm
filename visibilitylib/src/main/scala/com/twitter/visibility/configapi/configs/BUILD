scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "configapi/configapi-core",
        "configapi/configapi-decider",
        "decider",
        "finagle/finagle-stats",
        "servo/decider",
        "servo/decider/src/main/scala",
        "visibility/lib/src/main/scala/com/twitter/visibility/configapi/params",
        "visibility/lib/src/main/scala/com/twitter/visibility/models",
    ],
)
