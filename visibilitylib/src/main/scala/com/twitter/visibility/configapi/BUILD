scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "abdecider/src/main/scala",
        "configapi/configapi-abdecider",
        "configapi/configapi-core",
        "configapi/configapi-featureswitches:v2",
        "decider",
        "featureswitches/featureswitches-core/src/main/scala",
        "finagle/finagle-stats",
        "servo/decider/src/main/scala",
        "visibility/lib/src/main/scala/com/twitter/visibility/configapi/configs",
        "visibility/lib/src/main/scala/com/twitter/visibility/models",
    ],
)
