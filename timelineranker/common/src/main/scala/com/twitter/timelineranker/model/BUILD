scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "src/java/com/twitter/common/text/language:locale-util",
        "src/thrift/com/twitter/search:earlybird-scala",
        "src/thrift/com/twitter/search/common:features-scala",
        "src/thrift/com/twitter/timelineranker/server/model:thrift-scala",
        "timelines:config-api-base",
        "timelines/src/main/scala/com/twitter/timelines/common/model",
        "timelines/src/main/scala/com/twitter/timelines/earlybird/common/options",
        "timelines/src/main/scala/com/twitter/timelines/earlybird/common/utils",
        "timelines/src/main/scala/com/twitter/timelines/model/candidate",
        "timelines/src/main/scala/com/twitter/timelines/model/tweet",
        "timelines/src/main/scala/com/twitter/timelines/util",
        "timelineservice/common/src/main/scala/com/twitter/timelineservice/model",
    ],
    exports = [
        "timelines:config-api-base",
    ],
)
