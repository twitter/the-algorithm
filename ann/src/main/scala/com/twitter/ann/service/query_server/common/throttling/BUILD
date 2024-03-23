scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    tags = ["bazel-compatible"],
    dependencies = [
        "ann/src/main/scala/com/ExTwitter/ann/common",
        "ann/src/main/scala/com/ExTwitter/ann/faiss",
        "ann/src/main/scala/com/ExTwitter/ann/hnsw",
        "ExTwitter-server-internal/src/main/scala",
        "util/util-stats/src/main/scala/com/ExTwitter/finagle/stats",
    ],
)
