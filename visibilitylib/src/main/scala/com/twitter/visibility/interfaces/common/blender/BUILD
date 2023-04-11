scala_library(
    sources = [
        "*.scala",
    ],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "src/scala/com/twitter/search/blender/services/strato",
        "src/thrift/com/twitter/search/common:constants-scala",
        "stitch/stitch-core",
    ],
)
