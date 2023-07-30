scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "timelineranker/common/src/main/scala/com/X/timelineranker/model",
        "timelineranker/server/src/main/scala/com/X/timelineranker/core",
        "timelineranker/server/src/main/scala/com/X/timelineranker/recap/model",
        "timelines/src/main/scala/com/X/timelines/model/types",
    ],
)
