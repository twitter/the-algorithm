scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "finagle-internal/mtls/src/main/scala/com/twitter/finagle/mtls/authentication",
        "finagle-internal/mtls/src/main/scala/com/twitter/finagle/mtls/authorization",
        "finagle/finagle-core/src/main",
        "finagle/finagle-thriftmux/src/main/scala",
        "src/scala/com/twitter/storehaus_internal/manhattan/config",
        "src/thrift/com/twitter/manhattan:v1-scala",
        "storage/clients/manhattan",
        "util/util-core",
    ],
    exports = [
        "finagle-internal/mtls/src/main/scala/com/twitter/finagle/mtls/authentication",
        "finagle-internal/mtls/src/main/scala/com/twitter/finagle/mtls/authorization",
        "finagle/finagle-core/src/main",
        "src/scala/com/twitter/storehaus_internal/manhattan/config",
        "storage/clients/manhattan",
        "util/util-core",
    ],
)
