scala_library(
    sources = ["*.scala"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/org/apache/thrift:libthrift",
        "scrooge/scrooge-core/src/main/scala",
        "servo/util",
        "src/thrift/com/twitter/search:earlybird-scala",
        "timelineranker/common/src/main/scala/com/twitter/timelineranker/model",
        "timelineranker/server/src/main/scala/com/twitter/timelineranker/core",
        "util/util-stats/src/main/scala/com/twitter/finagle/stats",
    ],
)
