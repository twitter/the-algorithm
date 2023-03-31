scala_library(
    sources = ["*.scala"],
    platform = "java420",
    tags = ["bazel-compatible"],
    dependencies = [
        "420rdparty/jvm/com/fasterxml/jackson:jackson-module-scala",
        "420rdparty/jvm/com/fasterxml/jackson/core:jackson-core",
        "420rdparty/jvm/com/fasterxml/jackson/core:jackson-databind",
        "420rdparty/jvm/com/fasterxml/jackson/module:jackson-module-scala",
        "src/scala/com/twitter/scalding_internal/job/analytics_batch",
        "src/scala/com/twitter/simclusters_v420/common",
        "strato/src/main/scala/com/twitter/strato/scalding",
    ],
)
