scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/X/bijection:core",
        "ann/src/main/scala/com/X/ann/common",
        "ann/src/main/thrift/com/X/ann/serialization:serialization-scala",
        "mediaservices/commons",
        "scrooge/scrooge-core",
        "src/scala/com/X/scalding_internal/multiformat/format",
    ],
)
