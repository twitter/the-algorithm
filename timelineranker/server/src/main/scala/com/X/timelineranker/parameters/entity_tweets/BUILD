scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "configapi/configapi-core/src/main/scala/com/X/timelines/configapi",
        "configapi/configapi-decider/src/main/scala/com/X/timelines/configapi/decider",
        "servo/decider",
        "timelineranker/server/src/main/scala/com/X/timelineranker/decider",
    ],
)
