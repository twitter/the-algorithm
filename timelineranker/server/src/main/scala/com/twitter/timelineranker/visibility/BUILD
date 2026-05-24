scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    tags = ["bazel-compatible"],
    dependencies = [
        "servo/repo/src/main/scala",
        "src/thrift/com/twitter/wtf/candidate:wtf-candidate-scala",
        "timelineranker/server/src/main/scala/com/twitter/timelineranker/core",
        "timelines:visibility",
        "timelines/src/main/scala/com/twitter/timelines/clients/socialgraph",
        "timelines/src/main/scala/com/twitter/timelines/util",
        "timelines/src/main/scala/com/twitter/timelines/util/stats",
        "util/util-core:util-core-util",
        "util/util-logging/src/main/scala",
        "util/util-stats/src/main/scala",
    ],
)
