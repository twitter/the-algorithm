scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "finagle/finagle-memcached/src/main/scala",
        "finatra-internal/mtls-http/src/main/scala",
        "finatra-internal/mtls-thriftmutwitter/src/main/scala",
        "servo/repo/src/main/scala",
    ],
    etwitterports = [
        "finagle/finagle-memcached/src/main/scala",
        "finatra-internal/mtls-http/src/main/scala",
        "finatra-internal/mtls-thriftmutwitter/src/main/scala",
        "servo/repo/src/main/scala",
    ],
)
