scala_library(
    sources = ["*.scala"],
    platform = "java8",
    strict_deps = False,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/X/graphjet",
        "finagle/finagle-stats/src/main/scala",
        "src/scala/com/X/recos/util:recos-util",
        "src/thrift/com/X/recos:recos-common-scala",
    ],
)
