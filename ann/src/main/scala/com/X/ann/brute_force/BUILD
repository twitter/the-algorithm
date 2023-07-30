scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "ann/src/main/scala/com/X/ann/common",
        "ann/src/main/scala/com/X/ann/serialization",
        "ann/src/main/thrift/com/X/ann/serialization:serialization-scala",
        "src/java/com/X/search/common/file",
    ],
)
