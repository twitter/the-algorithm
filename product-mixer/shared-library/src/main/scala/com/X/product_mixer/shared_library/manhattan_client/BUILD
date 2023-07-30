scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "finagle-internal/mtls/src/main/scala/com/X/finagle/mtls/authentication",
        "finagle-internal/mtls/src/main/scala/com/X/finagle/mtls/authorization",
        "finagle/finagle-core/src/main",
        "finagle/finagle-thriftmux/src/main/scala",
        "src/scala/com/X/storehaus_internal/manhattan/config",
        "src/thrift/com/X/manhattan:v1-scala",
        "storage/clients/manhattan",
        "util/util-core",
    ],
    exports = [
        "finagle-internal/mtls/src/main/scala/com/X/finagle/mtls/authentication",
        "finagle-internal/mtls/src/main/scala/com/X/finagle/mtls/authorization",
        "finagle/finagle-core/src/main",
        "src/scala/com/X/storehaus_internal/manhattan/config",
        "storage/clients/manhattan",
        "util/util-core",
    ],
)
