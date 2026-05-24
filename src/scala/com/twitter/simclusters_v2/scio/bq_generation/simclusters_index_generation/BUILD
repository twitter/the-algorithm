scala_library(
    name = "simclusters_index_generation",
    sources = [
        "**/*.scala",
    ],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "src/scala/com/twitter/simclusters_v2/hdfs_sources:ads_fav_based_simclusters_cluster_to_tweet_index-scala",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources:ads_fav_click_based_simclusters_cluster_to_tweet_index-scala",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources:fav_based_evergreen_content_simclusters_cluster_to_tweet_index-scala",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources:fav_based_simclusters_cluster_to_tweet_index-scala",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources:fav_based_video_simclusters_cluster_to_tweet_index-scala",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources:push_open_based_simclusters_cluster_to_tweet_index-scala",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources:reply_based_simclusters_cluster_to_tweet_index-scala",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources:retweet_based_simclusters_cluster_to_tweet_index-scala",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources:video_view_based_simclusters_cluster_to_tweet_index-scala",
        "src/scala/com/twitter/simclusters_v2/scio/bq_generation/common",
        "src/scala/com/twitter/simclusters_v2/scio/bq_generation/sql",
        "src/scala/com/twitter/simclusters_v2/scio/bq_generation/tweets_ann:bq_generation",
        "unified_user_actions/thrift/src/main/thrift/com/twitter/unified_user_actions:unified_user_actions-scala",
    ],
)

jvm_binary(
    name = "fav-based-cluster-to-tweet-index-generation-adhoc-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.simclusters_index_generation.FavBasedClusterToTweetIndexGenerationAdhocJob",
    dependencies = [
        ":simclusters_index_generation",
    ],
)

jvm_binary(
    name = "fav-based-cluster-to-tweet-index-generation-batch-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.simclusters_index_generation.FavBasedClusterToTweetIndexGenerationBatchJob",
    dependencies = [
        ":simclusters_index_generation",
    ],
)

jvm_binary(
    name = "video-view-based-cluster-to-tweet-index-generation-adhoc-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.simclusters_index_generation.VideoViewBasedClusterToTweetIndexGenerationAdhocJob",
    dependencies = [
        ":simclusters_index_generation",
    ],
)

jvm_binary(
    name = "video-view-based-cluster-to-tweet-index-generation-batch-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.simclusters_index_generation.VideoViewBasedClusterToTweetIndexGenerationBatchJob",
    dependencies = [
        ":simclusters_index_generation",
    ],
)

jvm_binary(
    name = "retweet-based-cluster-to-tweet-index-generation-adhoc-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.simclusters_index_generation.RetweetBasedClusterToTweetIndexGenerationAdhocJob",
    dependencies = [
        ":simclusters_index_generation",
    ],
)

jvm_binary(
    name = "retweet-based-cluster-to-tweet-index-generation-batch-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.simclusters_index_generation.RetweetBasedClusterToTweetIndexGenerationBatchJob",
    dependencies = [
        ":simclusters_index_generation",
    ],
)

jvm_binary(
    name = "reply-based-cluster-to-tweet-index-generation-adhoc-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.simclusters_index_generation.ReplyBasedClusterToTweetIndexGenerationAdhocJob",
    dependencies = [
        ":simclusters_index_generation",
    ],
)

jvm_binary(
    name = "reply-based-cluster-to-tweet-index-generation-batch-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.simclusters_index_generation.ReplyBasedClusterToTweetIndexGenerationBatchJob",
    dependencies = [
        ":simclusters_index_generation",
    ],
)

jvm_binary(
    name = "push-open-based-cluster-to-tweet-index-generation-adhoc-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.simclusters_index_generation.PushOpenBasedClusterToTweetIndexGenerationAdhocJob",
    dependencies = [
        ":simclusters_index_generation",
    ],
)

jvm_binary(
    name = "push-open-based-cluster-to-tweet-index-generation-batch-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.simclusters_index_generation.PushOpenBasedClusterToTweetIndexGenerationBatchJob",
    dependencies = [
        ":simclusters_index_generation",
    ],
)

jvm_binary(
    name = "ads-fav-based-cluster-to-tweet-index-generation-adhoc-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.simclusters_index_generation.AdsFavBasedClusterToTweetIndexGenerationAdhocJob",
    dependencies = [
        ":simclusters_index_generation",
    ],
)

jvm_binary(
    name = "ads-fav-based-cluster-to-tweet-index-generation-batch-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.simclusters_index_generation.AdsFavBasedClusterToTweetIndexGenerationBatchJob",
    dependencies = [
        ":simclusters_index_generation",
    ],
)

jvm_binary(
    name = "ads-fav-click-based-cluster-to-tweet-index-generation-adhoc-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.simclusters_index_generation.AdsFavClickBasedClusterToTweetIndexGenerationAdhocJob",
    dependencies = [
        ":simclusters_index_generation",
    ],
)

jvm_binary(
    name = "ads-fav-click-based-cluster-to-tweet-index-generation-batch-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.simclusters_index_generation.AdsFavClickBasedClusterToTweetIndexGenerationBatchJob",
    dependencies = [
        ":simclusters_index_generation",
    ],
)

jvm_binary(
    name = "fav-based-evergreen-content-cluster-to-tweet-index-generation-adhoc-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.simclusters_index_generation.FavBasedEvergreenContentClusterToTweetIndexGenerationAdhocJob",
    dependencies = [
        ":simclusters_index_generation",
    ],
)

jvm_binary(
    name = "fav-based-evergreen-content-cluster-to-tweet-index-generation-batch-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.simclusters_index_generation.FavBasedEvergreenContentClusterToTweetIndexGenerationBatchJob",
    dependencies = [
        ":simclusters_index_generation",
    ],
)

jvm_binary(
    name = "fav-based-video-cluster-to-tweet-index-generation-adhoc-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.simclusters_index_generation.FavBasedVideoClusterToTweetIndexGenerationAdhocJob",
    dependencies = [
        ":simclusters_index_generation",
    ],
)

jvm_binary(
    name = "fav-based-video-cluster-to-tweet-index-generation-batch-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.simclusters_index_generation.FavBasedVideoClusterToTweetIndexGenerationBatchJob",
    dependencies = [
        ":simclusters_index_generation",
    ],
)
