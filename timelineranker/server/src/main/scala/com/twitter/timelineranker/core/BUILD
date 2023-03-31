scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "configapi/configapi-core/src/main/scala/com/twitter/timelines/configapi",
        "src/thrift/com/twitter/recos/user_tweet_entity_graph:user_tweet_entity_graph-scala",
        "src/thrift/com/twitter/search:earlybird-scala",
        "src/thrift/com/twitter/search/common:constants-scala",
        "src/thrift/com/twitter/search/common:features-scala",
        "timelineranker/common:model",
        "timelineranker/server/src/main/scala/com/twitter/timelineranker/recap/model",
        "timelines/src/main/scala/com/twitter/timelines/clients/gizmoduck",
        "timelines/src/main/scala/com/twitter/timelines/model/tweet",
        "timelines/src/main/scala/com/twitter/timelines/util",
        "timelines/src/main/scala/com/twitter/timelines/visibility/model",
        "util/util-core:util-core-util",
    ],
    exports = [
        "src/thrift/com/twitter/recos/user_tweet_entity_graph:user_tweet_entity_graph-scala",
    ],
)
