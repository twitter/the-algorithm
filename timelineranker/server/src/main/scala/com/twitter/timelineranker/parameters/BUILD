scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    tags = ["bazel-compatible"],
    dependencies = [
        "configapi/configapi-core",
        "configapi/configapi-core/src/main/scala/com/twitter/timelines/configapi",
        "servo/decider",
        "timelineranker/server/src/main/scala/com/twitter/timelineranker/parameters/entity_tweets",
        "timelineranker/server/src/main/scala/com/twitter/timelineranker/parameters/in_network_tweets",
        "timelineranker/server/src/main/scala/com/twitter/timelineranker/parameters/monitoring",
        "timelineranker/server/src/main/scala/com/twitter/timelineranker/parameters/recap",
        "timelineranker/server/src/main/scala/com/twitter/timelineranker/parameters/recap_author",
        "timelineranker/server/src/main/scala/com/twitter/timelineranker/parameters/recap_hydration",
        "timelineranker/server/src/main/scala/com/twitter/timelineranker/parameters/revchron",
        "timelineranker/server/src/main/scala/com/twitter/timelineranker/parameters/uteg_liked_by_tweets",
    ],
)
