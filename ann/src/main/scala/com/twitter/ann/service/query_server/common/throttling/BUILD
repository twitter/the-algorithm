scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    tags = ["bazel-compatible"],
    dependencies = [
        "ann/src/main/scala/com/twitter/ann/common",
        "ann/src/main/scala/com/twitter/ann/faiss",
        "ann/src/main/scala/com/twitter/ann/hnsw",
        "twitter-server-internal/src/main/scala",
        "util/util-stats/src/main/scala/com/twitter/finagle/stats",
    ],
)
