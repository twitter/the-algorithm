scala_library(
    sources = ["com/X/timelineranker/client/*.scala"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "finagle/finagle-core/src/main",
        "finagle/finagle-stats",
        "finagle/finagle-thrift/src/main/java",
        "servo/client/src/main/scala/com/X/servo/client",
        "src/thrift/com/X/timelineranker:thrift-scala",
        "src/thrift/com/X/timelineranker/server/model:thrift-scala",
        "timelineranker/common:model",
        "timelines/src/main/scala/com/X/timelines/util/stats",
        "util/util-stats/src/main/scala",
    ],
)
