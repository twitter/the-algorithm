scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "configapi/configapi-core/src/main/scala/com/twitter/timelines/configapi",
        "servo/decider",
        "timelineranker/server/src/main/scala/com/twitter/timelineranker/decider",
        "timelineranker/server/src/main/scala/com/twitter/timelineranker/parameters/util",
    ],
)
