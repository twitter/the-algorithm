scala_library(
    sources = ["com/twitter/timelineranker/client/*.scala"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "finagle/finagle-core/src/main",
        "finagle/finagle-stats",
        "finagle/finagle-thrift/src/main/java",
        "servo/client/src/main/scala/com/twitter/servo/client",
        "src/thrift/com/twitter/timelineranker:thrift-scala",
        "src/thrift/com/twitter/timelineranker/server/model:thrift-scala",
        "timelineranker/common:model",
        "timelines/src/main/scala/com/twitter/timelines/util/stats",
        "util/util-stats/src/main/scala",
    ],
)
