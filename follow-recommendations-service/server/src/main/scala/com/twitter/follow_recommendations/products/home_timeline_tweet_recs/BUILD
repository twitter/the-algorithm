scala_library(
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "finatra/inject/inject-app/src/main/scala",
        "finatra/inject/inject-core/src/main/scala",
        "follow-recommendations-service/server/src/main/scala/com/twitter/follow_recommendations/blenders",
        "follow-recommendations-service/server/src/main/scala/com/twitter/follow_recommendations/flows/content_recommender_flow",
        "follow-recommendations-service/server/src/main/scala/com/twitter/follow_recommendations/products/common",
        "follow-recommendations-service/server/src/main/scala/com/twitter/follow_recommendations/products/home_timeline_tweet_recs/configapi",
    ],
)
