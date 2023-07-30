scala_library(
    sources = ["*.scala"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/org/apache/thrift:libthrift",
        "scrooge/scrooge-core/src/main/scala",
        "servo/util",
        "src/thrift/com/X/search:earlybird-scala",
        "timelineranker/common/src/main/scala/com/X/timelineranker/model",
        "timelineranker/server/src/main/scala/com/X/timelineranker/core",
        "util/util-stats/src/main/scala/com/X/finagle/stats",
    ],
)
