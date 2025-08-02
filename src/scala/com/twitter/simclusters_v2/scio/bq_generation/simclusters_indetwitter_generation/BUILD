scala_library(
    name = "simclusters_indetwitter_generation",
    sources = [
        "**/*.scala",
    ],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources:ads_fav_based_simclusters_cluster_to_tweet_indetwitter-scala",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources:ads_fav_click_based_simclusters_cluster_to_tweet_indetwitter-scala",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources:fav_based_evergreen_content_simclusters_cluster_to_tweet_indetwitter-scala",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources:fav_based_simclusters_cluster_to_tweet_indetwitter-scala",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources:fav_based_video_simclusters_cluster_to_tweet_indetwitter-scala",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources:push_open_based_simclusters_cluster_to_tweet_indetwitter-scala",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources:reply_based_simclusters_cluster_to_tweet_indetwitter-scala",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources:retweet_based_simclusters_cluster_to_tweet_indetwitter-scala",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources:video_view_based_simclusters_cluster_to_tweet_indetwitter-scala",
        "src/scala/com/twitter/simclusters_v2/scio/bq_generation/common",
        "src/scala/com/twitter/simclusters_v2/scio/bq_generation/sql",
        "src/scala/com/twitter/simclusters_v2/scio/bq_generation/tweets_ann:bq_generation",
        "unified_user_actions/thrift/src/main/thrift/com/twitter/unified_user_actions:unified_user_actions-scala",
    ],
)

jvm_binary(
    name = "fav-based-cluster-to-tweet-indetwitter-generation-adhoc-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.simclusters_indetwitter_generation.FavBasedClusterToTweetIndetwitterGenerationAdhocJob",
    dependencies = [
        ":simclusters_indetwitter_generation",
    ],
)

jvm_binary(
    name = "fav-based-cluster-to-tweet-indetwitter-generation-batch-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.simclusters_indetwitter_generation.FavBasedClusterToTweetIndetwitterGenerationBatchJob",
    dependencies = [
        ":simclusters_indetwitter_generation",
    ],
)

jvm_binary(
    name = "video-view-based-cluster-to-tweet-indetwitter-generation-adhoc-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.simclusters_indetwitter_generation.VideoViewBasedClusterToTweetIndetwitterGenerationAdhocJob",
    dependencies = [
        ":simclusters_indetwitter_generation",
    ],
)

jvm_binary(
    name = "video-view-based-cluster-to-tweet-indetwitter-generation-batch-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.simclusters_indetwitter_generation.VideoViewBasedClusterToTweetIndetwitterGenerationBatchJob",
    dependencies = [
        ":simclusters_indetwitter_generation",
    ],
)

jvm_binary(
    name = "retweet-based-cluster-to-tweet-indetwitter-generation-adhoc-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.simclusters_indetwitter_generation.RetweetBasedClusterToTweetIndetwitterGenerationAdhocJob",
    dependencies = [
        ":simclusters_indetwitter_generation",
    ],
)

jvm_binary(
    name = "retweet-based-cluster-to-tweet-indetwitter-generation-batch-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.simclusters_indetwitter_generation.RetweetBasedClusterToTweetIndetwitterGenerationBatchJob",
    dependencies = [
        ":simclusters_indetwitter_generation",
    ],
)

jvm_binary(
    name = "reply-based-cluster-to-tweet-indetwitter-generation-adhoc-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.simclusters_indetwitter_generation.ReplyBasedClusterToTweetIndetwitterGenerationAdhocJob",
    dependencies = [
        ":simclusters_indetwitter_generation",
    ],
)

jvm_binary(
    name = "reply-based-cluster-to-tweet-indetwitter-generation-batch-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.simclusters_indetwitter_generation.ReplyBasedClusterToTweetIndetwitterGenerationBatchJob",
    dependencies = [
        ":simclusters_indetwitter_generation",
    ],
)

jvm_binary(
    name = "push-open-based-cluster-to-tweet-indetwitter-generation-adhoc-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.simclusters_indetwitter_generation.PushOpenBasedClusterToTweetIndetwitterGenerationAdhocJob",
    dependencies = [
        ":simclusters_indetwitter_generation",
    ],
)

jvm_binary(
    name = "push-open-based-cluster-to-tweet-indetwitter-generation-batch-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.simclusters_indetwitter_generation.PushOpenBasedClusterToTweetIndetwitterGenerationBatchJob",
    dependencies = [
        ":simclusters_indetwitter_generation",
    ],
)

jvm_binary(
    name = "ads-fav-based-cluster-to-tweet-indetwitter-generation-adhoc-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.simclusters_indetwitter_generation.AdsFavBasedClusterToTweetIndetwitterGenerationAdhocJob",
    dependencies = [
        ":simclusters_indetwitter_generation",
    ],
)

jvm_binary(
    name = "ads-fav-based-cluster-to-tweet-indetwitter-generation-batch-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.simclusters_indetwitter_generation.AdsFavBasedClusterToTweetIndetwitterGenerationBatchJob",
    dependencies = [
        ":simclusters_indetwitter_generation",
    ],
)

jvm_binary(
    name = "ads-fav-click-based-cluster-to-tweet-indetwitter-generation-adhoc-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.simclusters_indetwitter_generation.AdsFavClickBasedClusterToTweetIndetwitterGenerationAdhocJob",
    dependencies = [
        ":simclusters_indetwitter_generation",
    ],
)

jvm_binary(
    name = "ads-fav-click-based-cluster-to-tweet-indetwitter-generation-batch-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.simclusters_indetwitter_generation.AdsFavClickBasedClusterToTweetIndetwitterGenerationBatchJob",
    dependencies = [
        ":simclusters_indetwitter_generation",
    ],
)

jvm_binary(
    name = "fav-based-evergreen-content-cluster-to-tweet-indetwitter-generation-adhoc-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.simclusters_indetwitter_generation.FavBasedEvergreenContentClusterToTweetIndetwitterGenerationAdhocJob",
    dependencies = [
        ":simclusters_indetwitter_generation",
    ],
)

jvm_binary(
    name = "fav-based-evergreen-content-cluster-to-tweet-indetwitter-generation-batch-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.simclusters_indetwitter_generation.FavBasedEvergreenContentClusterToTweetIndetwitterGenerationBatchJob",
    dependencies = [
        ":simclusters_indetwitter_generation",
    ],
)

jvm_binary(
    name = "fav-based-video-cluster-to-tweet-indetwitter-generation-adhoc-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.simclusters_indetwitter_generation.FavBasedVideoClusterToTweetIndetwitterGenerationAdhocJob",
    dependencies = [
        ":simclusters_indetwitter_generation",
    ],
)

jvm_binary(
    name = "fav-based-video-cluster-to-tweet-indetwitter-generation-batch-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.simclusters_indetwitter_generation.FavBasedVideoClusterToTweetIndetwitterGenerationBatchJob",
    dependencies = [
        ":simclusters_indetwitter_generation",
    ],
)
