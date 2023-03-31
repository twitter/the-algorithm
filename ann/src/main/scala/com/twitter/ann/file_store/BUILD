scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/twitter/storehaus:core",
        "ann/src/main/scala/com/twitter/ann/common",
        "ann/src/main/thrift/com/twitter/ann/common:ann-common-scala",
        "mediaservices/commons/src/main/scala",
    ],
)
