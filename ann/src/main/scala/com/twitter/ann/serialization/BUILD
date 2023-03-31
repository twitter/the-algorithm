scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/twitter/bijection:core",
        "ann/src/main/scala/com/twitter/ann/common",
        "ann/src/main/thrift/com/twitter/ann/serialization:serialization-scala",
        "mediaservices/commons",
        "scrooge/scrooge-core",
        "src/scala/com/twitter/scalding_internal/multiformat/format",
    ],
)
