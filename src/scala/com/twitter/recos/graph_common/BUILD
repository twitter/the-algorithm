scala_library(
    sources = ["*.scala"],
    platform = "java8",
    strict_deps = False,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/twitter/graphjet",
        "finagle/finagle-stats/src/main/scala",
        "src/scala/com/twitter/recos/util:recos-util",
        "src/thrift/com/twitter/recos:recos-common-scala",
    ],
)
