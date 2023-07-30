scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/org/mapdb",
        "ann/src/main/java/com/X/ann/hnsw",
        "ann/src/main/scala/com/X/ann/common",
        "ann/src/main/scala/com/X/ann/serialization",
        "ann/src/main/thrift/com/X/ann/common:ann-common-scala",
        "mediaservices/commons/src/main/scala:futuretracker",
        "src/java/com/X/common_internal/hadoop",
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
