scala_library(
    sources = ["*.scala"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/fasterxml/jackson:jackson-module-scala",
        "3rdparty/jvm/com/fasterxml/jackson/core:jackson-core",
        "3rdparty/jvm/com/fasterxml/jackson/core:jackson-databind",
        "3rdparty/jvm/com/fasterxml/jackson/module:jackson-module-scala",
        "src/scala/com/twitter/scalding_internal/job/analytics_batch",
        "src/scala/com/twitter/simclusters_v2/common",
        "strato/src/main/scala/com/twitter/strato/scalding",
    ],
)
