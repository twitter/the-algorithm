target(
    name = "adapter",
    dependencies = ["timelineranker/common/src/main/scala/com/twitter/timelineranker/adapter"],
)

target(
    name = "model",
    dependencies = ["timelineranker/common/src/main/scala/com/twitter/timelineranker/model"],
)

target(
    tags = ["bazel-compatible"],
    dependencies = [
        ":adapter",
        ":model",
    ],
)
