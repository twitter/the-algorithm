scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "configapi/configapi-core",
        "src/thrift/com/X/timelineservice/server/internal:thrift-scala",
        "src/thrift/com/X/tweetypie:tweet-scala",
        "timelineranker/common:model",
        "timelines/src/main/scala/com/X/timelines/clientconfig",
        "timelines/src/main/scala/com/X/timelines/model/tweet",
    ],
)
