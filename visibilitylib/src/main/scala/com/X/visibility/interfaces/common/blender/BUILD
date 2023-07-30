scala_library(
    sources = [
        "*.scala",
    ],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "src/scala/com/X/search/blender/services/strato",
        "src/thrift/com/X/search/common:constants-scala",
        "stitch/stitch-core",
    ],
)
