scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/spotify:annoy-java",
        "3rdparty/jvm/com/spotify:annoy-snapshot",
        "3rdparty/jvm/com/X/storehaus:core",
        "ann/src/main/scala/com/X/ann/common",
        "ann/src/main/scala/com/X/ann/file_store",
        "ann/src/main/thrift/com/X/ann/common:ann-common-scala",
        "mediaservices/commons",
        "src/java/com/X/search/common/file",
        "src/scala/com/X/ml/api/embedding",
    ],
    exports = [
        "ann/src/main/scala/com/X/ann/common",
        "src/java/com/X/common_internal/hadoop",
        "src/java/com/X/search/common/file",
        "src/scala/com/X/ml/api/embedding",
    ],
)
