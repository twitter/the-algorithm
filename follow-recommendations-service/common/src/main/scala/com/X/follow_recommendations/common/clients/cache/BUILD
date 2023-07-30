scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "cache/client",
        "finagle/finagle-memcached/src/main/scala",
        "finatra/inject/inject-core/src/main/scala",
        "finatra/inject/inject-thrift-client/src/main/scala",
        "stitch/stitch-core",
        "util/util-core:scala",
        "util/util-thrift",
    ],
)
