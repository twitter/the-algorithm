target(
    name = "adapter",
    dependencies = ["timelineranker/common/src/main/scala/com/X/timelineranker/adapter"],
)

target(
    name = "model",
    dependencies = ["timelineranker/common/src/main/scala/com/X/timelineranker/model"],
)

target(
    tags = ["bazel-compatible"],
    dependencies = [
        ":adapter",
        ":model",
    ],
)
