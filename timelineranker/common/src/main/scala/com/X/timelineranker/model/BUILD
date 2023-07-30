scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "src/java/com/X/common/text/language:locale-util",
        "src/thrift/com/X/search:earlybird-scala",
        "src/thrift/com/X/search/common:features-scala",
        "src/thrift/com/X/timelineranker/server/model:thrift-scala",
        "timelines:config-api-base",
        "timelines/src/main/scala/com/X/timelines/common/model",
        "timelines/src/main/scala/com/X/timelines/earlybird/common/options",
        "timelines/src/main/scala/com/X/timelines/earlybird/common/utils",
        "timelines/src/main/scala/com/X/timelines/model/candidate",
        "timelines/src/main/scala/com/X/timelines/model/tweet",
        "timelines/src/main/scala/com/X/timelines/util",
        "timelineservice/common/src/main/scala/com/X/timelineservice/model",
    ],
    exports = [
        "timelines:config-api-base",
    ],
)
