scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/ExTwitter/bijection:core",
        "ann/src/main/scala/com/ExTwitter/ann/common",
        "ann/src/main/thrift/com/ExTwitter/ann/serialization:serialization-scala",
        "mediaservices/commons",
        "scrooge/scrooge-core",
        "src/scala/com/ExTwitter/scalding_internal/multiformat/format",
    ],
)
