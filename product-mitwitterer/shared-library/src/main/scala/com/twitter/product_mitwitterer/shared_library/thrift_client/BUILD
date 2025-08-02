scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "finagle/finagle-core/src/main",
        "finagle/finagle-thriftmutwitter/src/main/scala",
        "finatra-internal/mtls-http/src/main/scala",
        "finatra-internal/mtls-thriftmutwitter/src/main/scala",
        "util/util-core",
    ],
    etwitterports = [
        "finagle/finagle-core/src/main",
        "finagle/finagle-thriftmutwitter/src/main/scala",
        "finatra-internal/mtls-http/src/main/scala",
        "finatra-internal/mtls-thriftmutwitter/src/main/scala",
        "util/util-core",
    ],
)
