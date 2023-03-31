scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "configapi/configapi-core/src/main/scala/com/twitter/timelines/configapi",
        "configapi/configapi-decider/src/main/scala/com/twitter/timelines/configapi/decider",
        "servo/decider",
        "servo/util/src/main/scala",
        "timelineranker/common/src/main/scala/com/twitter/timelineranker/model",
        "timelineranker/server/src/main/scala/com/twitter/timelineranker/decider",
        "timelineranker/server/src/main/scala/com/twitter/timelineranker/parameters/util",
        "timelines/src/main/scala/com/twitter/timelines/experiment",
        "util/util-logging",
        "util/util-stats",
    ],
)
