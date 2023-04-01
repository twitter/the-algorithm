scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = False,
    tags = ["bazel-compatible"],
    dependencies = [
        "appsec/sanitization-lib/src/main/scala",
        "src/thrift/com/twitter/expandodo:cards-scala",
        "stitch/stitch-core",
        "visibility/lib/src/main/resources/config",
        "visibility/lib/src/main/scala/com/twitter/visibility",
        "visibility/lib/src/main/scala/com/twitter/visibility/builder/tweets",
        "visibility/lib/src/main/scala/com/twitter/visibility/builder/users",
        "visibility/lib/src/main/scala/com/twitter/visibility/features",
    ],
)
