scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "finagle-internal/mtls/src/main/scala/com/X/finagle/mtls/authentication",
        "finagle-internal/mtls/src/main/scala/com/X/finagle/mtls/transport",
        "finagle/finagle-thrift/src/main/scala",
        "tweetypie/servo/util/src/main/scala:exception",
        "strato/src/main/scala/com/X/strato/access",
        "strato/src/main/scala/com/X/strato/data",
    ],
)
