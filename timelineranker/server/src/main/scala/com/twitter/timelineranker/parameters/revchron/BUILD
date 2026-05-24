scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    tags = ["bazel-compatible"],
    dependencies = [
        "configapi/configapi-core",
        "configapi/configapi-core/src/main/scala/com/twitter/timelines/configapi",
        "servo/decider",
        "timelineranker/common/src/main/scala/com/twitter/timelineranker/model",
        "timelineranker/server/src/main/scala/com/twitter/timelineranker/config",
        "timelineranker/server/src/main/scala/com/twitter/timelineranker/decider",
        "timelineranker/server/src/main/scala/com/twitter/timelineranker/parameters/util",
        "timelines/src/main/scala/com/twitter/timelines/config",
        "timelines/src/main/scala/com/twitter/timelines/decider",
        "timelines/src/main/scala/com/twitter/timelines/util/bounds",
        "util/util-stats/src/main/scala",
    ],
)
