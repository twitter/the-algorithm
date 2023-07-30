scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "configapi/configapi-core/src/main/scala/com/X/timelines/configapi",
        "servo/decider/src/main/scala",
        "timelineranker/server/src/main/scala/com/X/timelineranker/decider",
        "timelineranker/server/src/main/scala/com/X/timelineranker/parameters/util",
        "timelines/src/main/scala/com/X/timelines/util/bounds",
    ],
)
