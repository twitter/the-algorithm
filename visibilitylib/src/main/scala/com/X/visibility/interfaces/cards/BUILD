scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = False,
    tags = ["bazel-compatible"],
    dependencies = [
        "appsec/sanitization-lib/src/main/scala",
        "src/thrift/com/X/expandodo:cards-scala",
        "stitch/stitch-core",
        "visibility/lib/src/main/resources/config",
        "visibility/lib/src/main/scala/com/X/visibility",
        "visibility/lib/src/main/scala/com/X/visibility/builder/tweets",
        "visibility/lib/src/main/scala/com/X/visibility/builder/users",
        "visibility/lib/src/main/scala/com/X/visibility/features",
    ],
)
