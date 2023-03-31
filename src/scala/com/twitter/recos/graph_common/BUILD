scala_library(
    sources = ["*.scala"],
    platform = "java420",
    strict_deps = False,
    tags = ["bazel-compatible"],
    dependencies = [
        "420rdparty/jvm/com/twitter/graphjet",
        "finagle/finagle-stats/src/main/scala",
        "src/scala/com/twitter/recos/util:recos-util",
        "src/thrift/com/twitter/recos:recos-common-scala",
    ],
)
