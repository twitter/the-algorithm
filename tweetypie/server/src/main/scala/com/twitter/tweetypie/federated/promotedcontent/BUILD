scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "ads-common/loggingclient/src/main/scala",
        "src/scala/com/twitter/ads/internal/pcl/service",
        "src/scala/com/twitter/ads/internal/pcl/strato_adaptor",
        "src/thrift/com/twitter/ads/adserver:ads_shared_types-scala",
        "src/thrift/com/twitter/ads/callback:engagement_request-scala",
        "src/thrift/com/twitter/ads/internal/pcl:promoted_content_input-scala",
        "tweetypie/common/src/thrift/com/twitter/tweetypie:tweet_service_graphql-scala",
        "strato/src/main/scala/com/twitter/strato/server/context",
        "twitter-context/src/main/scala",
        "util/util-stats/src/main/scala/com/twitter/finagle/stats",
    ],
)
