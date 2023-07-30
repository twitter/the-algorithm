scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "ads-common/loggingclient/src/main/scala",
        "src/scala/com/X/ads/internal/pcl/service",
        "src/scala/com/X/ads/internal/pcl/strato_adaptor",
        "src/thrift/com/X/ads/adserver:ads_shared_types-scala",
        "src/thrift/com/X/ads/callback:engagement_request-scala",
        "src/thrift/com/X/ads/internal/pcl:promoted_content_input-scala",
        "tweetypie/common/src/thrift/com/X/tweetypie:tweet_service_graphql-scala",
        "strato/src/main/scala/com/X/strato/server/context",
        "X-context/src/main/scala",
        "util/util-stats/src/main/scala/com/X/finagle/stats",
    ],
)
