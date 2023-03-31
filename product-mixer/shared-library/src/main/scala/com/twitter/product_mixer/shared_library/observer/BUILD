scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "servo/util",
        "stitch/stitch-core",
        "util/util-core:util-core-util",
        "util/util-stats",
    ],
    exports = [
        "servo/util",
        "stitch/stitch-core",
        "util/util-core:util-core-util",
        "util/util-stats",
    ],
)
