scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "finagle/finagle-core/src/main",
        "finagle/finagle-http/src/main/scala",
        "finagle/finagle-thriftmux/src/main/scala",
        "finatra-internal/mtls-http/src/main/scala",
        "finatra-internal/mtls-thriftmux/src/main/scala",
        "util/util-core",
    ],
    exports = [
        "finagle/finagle-core/src/main",
        "finagle/finagle-http/src/main/scala",
        "finagle/finagle-thriftmux/src/main/scala",
        "finatra-internal/mtls-http/src/main/scala",
        "finatra-internal/mtls-thriftmux/src/main/scala",
        "util/util-core",
    ],
)
