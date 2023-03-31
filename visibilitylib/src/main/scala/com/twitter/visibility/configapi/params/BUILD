scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "configapi/configapi-core",
        "finagle/finagle-stats",
        "visibility/common/src/main/scala/com/twitter/visibility/common:model_thresholds",
    ],
    exports = [
        "visibility/common/src/main/scala/com/twitter/visibility/common:model_thresholds",
    ],
)
