scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "ann/src/main/scala/com/ExTwitter/ann/common",
        "ann/src/main/scala/com/ExTwitter/ann/serialization",
        "ann/src/main/thrift/com/ExTwitter/ann/serialization:serialization-scala",
        "src/java/com/ExTwitter/search/common/file",
    ],
)
