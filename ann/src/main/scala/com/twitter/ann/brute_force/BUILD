scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "ann/src/main/scala/com/twitter/ann/common",
        "ann/src/main/scala/com/twitter/ann/serialization",
        "ann/src/main/thrift/com/twitter/ann/serialization:serialization-scala",
        "src/java/com/twitter/search/common/file",
    ],
)
