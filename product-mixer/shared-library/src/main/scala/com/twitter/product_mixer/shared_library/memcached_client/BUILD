scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "finagle/finagle-memcached/src/main/scala",
        "finatra-internal/mtls-http/src/main/scala",
        "finatra-internal/mtls-thriftmux/src/main/scala",
        "servo/repo/src/main/scala",
    ],
    exports = [
        "finagle/finagle-memcached/src/main/scala",
        "finatra-internal/mtls-http/src/main/scala",
        "finatra-internal/mtls-thriftmux/src/main/scala",
        "servo/repo/src/main/scala",
    ],
)
