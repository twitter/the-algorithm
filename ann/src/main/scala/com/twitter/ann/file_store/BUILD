scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/ExTwitter/storehaus:core",
        "ann/src/main/scala/com/ExTwitter/ann/common",
        "ann/src/main/thrift/com/ExTwitter/ann/common:ann-common-scala",
        "mediaservices/commons/src/main/scala",
    ],
)
