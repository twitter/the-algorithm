scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/spotify:annoy-java",
        "3rdparty/jvm/com/spotify:annoy-snapshot",
        "3rdparty/jvm/com/ExTwitter/storehaus:core",
        "ann/src/main/scala/com/ExTwitter/ann/common",
        "ann/src/main/scala/com/ExTwitter/ann/file_store",
        "ann/src/main/thrift/com/ExTwitter/ann/common:ann-common-scala",
        "mediaservices/commons",
        "src/java/com/ExTwitter/search/common/file",
        "src/scala/com/ExTwitter/ml/api/embedding",
    ],
    exports = [
        "ann/src/main/scala/com/ExTwitter/ann/common",
        "src/java/com/ExTwitter/common_internal/hadoop",
        "src/java/com/ExTwitter/search/common/file",
        "src/scala/com/ExTwitter/ml/api/embedding",
    ],
)
