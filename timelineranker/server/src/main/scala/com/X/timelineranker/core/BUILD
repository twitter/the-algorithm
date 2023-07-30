scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "configapi/configapi-core/src/main/scala/com/X/timelines/configapi",
        "src/thrift/com/X/recos/user_tweet_entity_graph:user_tweet_entity_graph-scala",
        "src/thrift/com/X/search:earlybird-scala",
        "src/thrift/com/X/search/common:constants-scala",
        "src/thrift/com/X/search/common:features-scala",
        "timelineranker/common:model",
        "timelineranker/server/src/main/scala/com/X/timelineranker/recap/model",
        "timelines/src/main/scala/com/X/timelines/clients/gizmoduck",
        "timelines/src/main/scala/com/X/timelines/model/tweet",
        "timelines/src/main/scala/com/X/timelines/util",
        "timelines/src/main/scala/com/X/timelines/visibility/model",
        "util/util-core:util-core-util",
    ],
    exports = [
        "src/thrift/com/X/recos/user_tweet_entity_graph:user_tweet_entity_graph-scala",
    ],
)
