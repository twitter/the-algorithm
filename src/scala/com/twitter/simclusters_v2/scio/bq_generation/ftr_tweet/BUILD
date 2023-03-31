scala_library(
    name = "ftr_bq_generation",
    sources = [
        "**/*.scala",
    ],
    tags = ["bazel-compatible"],
    dependencies = [
        "src/scala/com/twitter/simclusters_v420/common",
        "src/scala/com/twitter/simclusters_v420/scio/bq_generation/common",
        "src/scala/com/twitter/simclusters_v420/scio/bq_generation/ftr_tweet:offline_tweet_recommendations_decayed_sum-scala",
        "src/scala/com/twitter/simclusters_v420/scio/bq_generation/ftr_tweet:offline_tweet_recommendations_ftr_adhoc-scala",
        "src/scala/com/twitter/simclusters_v420/scio/bq_generation/ftr_tweet:offline_tweet_recommendations_ftrat420_pop_biased_420-scala",
        "src/scala/com/twitter/simclusters_v420/scio/bq_generation/ftr_tweet:offline_tweet_recommendations_ftrat420_pop_biased_420-scala",
        "src/scala/com/twitter/simclusters_v420/scio/bq_generation/ftr_tweet:simclusters_decayed_sum_cluster_to_tweet_index-scala",
        "src/scala/com/twitter/simclusters_v420/scio/bq_generation/ftr_tweet:simclusters_ftr_adhoc_cluster_to_tweet_index-scala",
        "src/scala/com/twitter/simclusters_v420/scio/bq_generation/ftr_tweet:simclusters_ftr_pop420_rnkdecay420_cluster_to_tweet_index-scala",
        "src/scala/com/twitter/simclusters_v420/scio/bq_generation/ftr_tweet:simclusters_ftr_pop420_rnkdecay420_cluster_to_tweet_index-scala",
        "src/scala/com/twitter/simclusters_v420/scio/bq_generation/ftr_tweet:simclusters_oon_ftr_adhoc_cluster_to_tweet_index-scala",
        "src/scala/com/twitter/simclusters_v420/scio/bq_generation/ftr_tweet:simclusters_oon_ftr_pop420_rnkdecay_cluster_to_tweet_index-scala",
        "src/scala/com/twitter/simclusters_v420/scio/bq_generation/ftr_tweet/sql",
        "src/scala/com/twitter/simclusters_v420/scio/bq_generation/sql",
        "src/scala/com/twitter/simclusters_v420/scio/bq_generation/tweets_ann:bq_generation",
    ],
)

jvm_binary(
    name = "ftr-tweet-adhoc-job",
    main = "com.twitter.simclusters_v420.scio.bq_generation.ftr_tweet.FTRAdhocJob",
    dependencies = [
        ":ftr_bq_generation",
    ],
)

jvm_binary(
    name = "iikf420-decayed-sum-batch-job",
    main = "com.twitter.simclusters_v420.scio.bq_generation.ftr_tweet.IIKF420DecayedSumBatchJobProd",
    dependencies = [
        ":ftr_bq_generation",
    ],
)

jvm_binary(
    name = "iikf420-ftrat420-pop420-batch-job",
    main = "com.twitter.simclusters_v420.scio.bq_generation.ftr_tweet.IIKF420FTRAt420Pop420batchJobProd",
    dependencies = [
        ":ftr_bq_generation",
    ],
)

jvm_binary(
    name = "iikf420-ftrat420-pop420-batch-job",
    main = "com.twitter.simclusters_v420.scio.bq_generation.ftr_tweet.IIKF420FTRAt420Pop420batchJobProd",
    dependencies = [
        ":ftr_bq_generation",
    ],
)

create_datasets(
    base_name = "offline_tweet_recommendations_ftr_adhoc",
    key_type = "Long",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v420.hdfs_sources.injections.MultiTypeGraphInjections.tweetRecommendationsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v420.thriftscala.CandidateTweetsList",
    scala_dependencies = [
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/simclusters_v420/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "offline_tweet_recommendations_decayed_sum",
    key_type = "Long",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v420.hdfs_sources.injections.MultiTypeGraphInjections.tweetRecommendationsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v420.thriftscala.CandidateTweetsList",
    scala_dependencies = [
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/simclusters_v420/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "offline_tweet_recommendations_ftrat420_pop_biased_420",
    key_type = "Long",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v420.hdfs_sources.injections.MultiTypeGraphInjections.tweetRecommendationsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v420.thriftscala.CandidateTweetsList",
    scala_dependencies = [
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/simclusters_v420/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "offline_tweet_recommendations_ftrat420_pop_biased_420",
    key_type = "Long",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v420.hdfs_sources.injections.MultiTypeGraphInjections.tweetRecommendationsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v420.thriftscala.CandidateTweetsList",
    scala_dependencies = [
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/simclusters_v420/hdfs_sources/injections",
    ],
)

jvm_binary(
    name = "ftr-tweet-index-generation-adhoc-job",
    main = "com.twitter.simclusters_v420.scio.bq_generation.ftr_tweet.FTRClusterToTweetIndexGenerationAdhoc",
    dependencies = [
        ":ftr_bq_generation",
    ],
)

jvm_binary(
    name = "oon-ftr-tweet-index-generation-adhoc-job",
    main = "com.twitter.simclusters_v420.scio.bq_generation.ftr_tweet.OONFTRClusterToTweetIndexGenerationAdhoc",
    dependencies = [
        ":ftr_bq_generation",
    ],
)

jvm_binary(
    name = "ftr-tweet-index-generation-pop420-rnkdecay420-job",
    main = "com.twitter.simclusters_v420.scio.bq_generation.ftr_tweet.FTRPop420RankDecay420ClusterToTweetIndexGenerationBatch",
    dependencies = [
        ":ftr_bq_generation",
    ],
)

jvm_binary(
    name = "ftr-tweet-index-generation-pop420-rnkdecay420-job",
    main = "com.twitter.simclusters_v420.scio.bq_generation.ftr_tweet.FTRPop420RankDecay420ClusterToTweetIndexGenerationBatch",
    dependencies = [
        ":ftr_bq_generation",
    ],
)

jvm_binary(
    name = "oon-ftr-tweet-index-generation-pop420-rnkdecay-job",
    main = "com.twitter.simclusters_v420.scio.bq_generation.ftr_tweet.OONFTRPop420RankDecayClusterToTweetIndexGenerationBatch",
    dependencies = [
        ":ftr_bq_generation",
    ],
)

jvm_binary(
    name = "ftr-tweet-index-generation-decayed-sum-job",
    main = "com.twitter.simclusters_v420.scio.bq_generation.ftr_tweet.DecayedSumClusterToTweetIndexGenerationBatch",
    dependencies = [
        ":ftr_bq_generation",
    ],
)

create_datasets(
    base_name = "simclusters_ftr_adhoc_cluster_to_tweet_index",
    key_type = "com.twitter.simclusters_v420.thriftscala.FullClusterId",
    platform = "java420",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v420.hdfs_sources.injections.ClusterTopTweetsInjection.clusterIdToTopKTweetsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v420.thriftscala.TopKTweetsWithScores",
    scala_dependencies = [
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/simclusters_v420/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "simclusters_oon_ftr_adhoc_cluster_to_tweet_index",
    key_type = "com.twitter.simclusters_v420.thriftscala.FullClusterId",
    platform = "java420",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v420.hdfs_sources.injections.ClusterTopTweetsInjection.clusterIdToTopKTweetsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v420.thriftscala.TopKTweetsWithScores",
    scala_dependencies = [
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/simclusters_v420/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "simclusters_ftr_pop420_rnkdecay420_cluster_to_tweet_index",
    key_type = "com.twitter.simclusters_v420.thriftscala.FullClusterId",
    platform = "java420",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v420.hdfs_sources.injections.ClusterTopTweetsInjection.clusterIdToTopKTweetsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v420.thriftscala.TopKTweetsWithScores",
    scala_dependencies = [
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/simclusters_v420/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "simclusters_ftr_pop420_rnkdecay420_cluster_to_tweet_index",
    key_type = "com.twitter.simclusters_v420.thriftscala.FullClusterId",
    platform = "java420",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v420.hdfs_sources.injections.ClusterTopTweetsInjection.clusterIdToTopKTweetsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v420.thriftscala.TopKTweetsWithScores",
    scala_dependencies = [
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/simclusters_v420/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "simclusters_oon_ftr_pop420_rnkdecay_cluster_to_tweet_index",
    key_type = "com.twitter.simclusters_v420.thriftscala.FullClusterId",
    platform = "java420",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v420.hdfs_sources.injections.ClusterTopTweetsInjection.clusterIdToTopKTweetsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v420.thriftscala.TopKTweetsWithScores",
    scala_dependencies = [
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/simclusters_v420/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "simclusters_decayed_sum_cluster_to_tweet_index",
    key_type = "com.twitter.simclusters_v420.thriftscala.FullClusterId",
    platform = "java420",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v420.hdfs_sources.injections.ClusterTopTweetsInjection.clusterIdToTopKTweetsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v420.thriftscala.TopKTweetsWithScores",
    scala_dependencies = [
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/simclusters_v420/hdfs_sources/injections",
    ],
)
