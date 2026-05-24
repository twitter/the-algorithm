scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "finagle-internal/mtls/src/main/scala/com/twitter/finagle/mtls/authentication",
        "finagle-internal/mtls/src/main/scala/com/twitter/finagle/mtls/transport",
        "finagle/finagle-thrift/src/main/scala",
        "tweetypie/servo/util/src/main/scala:exception",
        "strato/src/main/scala/com/twitter/strato/access",
        "strato/src/main/scala/com/twitter/strato/data",
    ],
)
