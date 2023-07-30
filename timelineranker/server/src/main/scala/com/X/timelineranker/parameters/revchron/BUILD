scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    tags = ["bazel-compatible"],
    dependencies = [
        "configapi/configapi-core",
        "configapi/configapi-core/src/main/scala/com/X/timelines/configapi",
        "servo/decider",
        "timelineranker/common/src/main/scala/com/X/timelineranker/model",
        "timelineranker/server/src/main/scala/com/X/timelineranker/config",
        "timelineranker/server/src/main/scala/com/X/timelineranker/decider",
        "timelineranker/server/src/main/scala/com/X/timelineranker/parameters/util",
        "timelines/src/main/scala/com/X/timelines/config",
        "timelines/src/main/scala/com/X/timelines/decider",
        "timelines/src/main/scala/com/X/timelines/util/bounds",
        "util/util-stats/src/main/scala",
    ],
)
