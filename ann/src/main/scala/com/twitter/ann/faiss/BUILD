scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/org/mapdb",
        "ann/src/main/java/com/twitter/ann/faiss",
        "ann/src/main/scala/com/twitter/ann/common",
        "ann/src/main/scala/com/twitter/ann/serialization",
        "ann/src/main/thrift/com/twitter/ann/common:ann-common-scala",
        "mediaservices/commons/src/main/scala:futuretracker",
        "src/java/com/twitter/common_internal/hadoop",
        "src/java/com/twitter/search/common/file",
        "src/scala/com/twitter/ml/api/embedding",
    ],
    exports = [
        "ann/src/main/scala/com/twitter/ann/common",
        "src/java/com/twitter/common_internal/hadoop",
        "src/java/com/twitter/search/common/file",
        "src/scala/com/twitter/ml/api/embedding",
    ],
)
