scala_library(
    name = "ftr_bq_generation",
    sources = [
        "**/*.scala",
    ],
    tags = ["bazel-compatible"],
    dependencies = [
        "src/scala/com/twitter/simclusters_v2/common",
        "src/scala/com/twitter/simclusters_v2/scio/bq_generation/common",
        "src/scala/com/twitter/simclusters_v2/scio/bq_generation/ftr_tweet:offline_tweet_recommendations_decayed_sum-scala",
        "src/scala/com/twitter/simclusters_v2/scio/bq_generation/ftr_tweet:offline_tweet_recommendations_ftr_adhoc-scala",
        "src/scala/com/twitter/simclusters_v2/scio/bq_generation/ftr_tweet:offline_tweet_recommendations_ftrat5_pop_biased_1000-scala",
        "src/scala/com/twitter/simclusters_v2/scio/bq_generation/ftr_tweet:offline_tweet_recommendations_ftrat5_pop_biased_10000-scala",
        "src/scala/com/twitter/simclusters_v2/scio/bq_generation/ftr_tweet:simclusters_decayed_sum_cluster_to_tweet_index-scala",
        "src/scala/com/twitter/simclusters_v2/scio/bq_generation/ftr_tweet:simclusters_ftr_adhoc_cluster_to_tweet_index-scala",
        "src/scala/com/twitter/simclusters_v2/scio/bq_generation/ftr_tweet:simclusters_ftr_pop10000_rnkdecay11_cluster_to_tweet_index-scala",
        "src/scala/com/twitter/simclusters_v2/scio/bq_generation/ftr_tweet:simclusters_ftr_pop1000_rnkdecay11_cluster_to_tweet_index-scala",
        "src/scala/com/twitter/simclusters_v2/scio/bq_generation/ftr_tweet:simclusters_oon_ftr_adhoc_cluster_to_tweet_index-scala",
        "src/scala/com/twitter/simclusters_v2/scio/bq_generation/ftr_tweet:simclusters_oon_ftr_pop1000_rnkdecay_cluster_to_tweet_index-scala",
        "src/scala/com/twitter/simclusters_v2/scio/bq_generation/ftr_tweet/sql",
        "src/scala/com/twitter/simclusters_v2/scio/bq_generation/sql",
        "src/scala/com/twitter/simclusters_v2/scio/bq_generation/tweets_ann:bq_generation",
    ],
)

jvm_binary(
    name = "ftr-tweet-adhoc-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.ftr_tweet.FTRAdhocJob",
    dependencies = [
        ":ftr_bq_generation",
    ],
)

jvm_binary(
    name = "iikf2020-decayed-sum-batch-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.ftr_tweet.IIKF2020DecayedSumBatchJobProd",
    dependencies = [
        ":ftr_bq_generation",
    ],
)

jvm_binary(
    name = "iikf2020-ftrat5-pop1000-batch-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.ftr_tweet.IIKF2020FTRAt5Pop1000batchJobProd",
    dependencies = [
        ":ftr_bq_generation",
    ],
)

jvm_binary(
    name = "iikf2020-ftrat5-pop10000-batch-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.ftr_tweet.IIKF2020FTRAt5Pop10000batchJobProd",
    dependencies = [
        ":ftr_bq_generation",
    ],
)

create_datasets(
    base_name = "offline_tweet_recommendations_ftr_adhoc",
    key_type = "Long",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.MultiTypeGraphInjections.tweetRecommendationsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.CandidateTweetsList",
    scala_dependencies = [
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "offline_tweet_recommendations_decayed_sum",
    key_type = "Long",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.MultiTypeGraphInjections.tweetRecommendationsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.CandidateTweetsList",
    scala_dependencies = [
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "offline_tweet_recommendations_ftrat5_pop_biased_1000",
    key_type = "Long",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.MultiTypeGraphInjections.tweetRecommendationsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.CandidateTweetsList",
    scala_dependencies = [
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "offline_tweet_recommendations_ftrat5_pop_biased_10000",
    key_type = "Long",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.MultiTypeGraphInjections.tweetRecommendationsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.CandidateTweetsList",
    scala_dependencies = [
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

jvm_binary(
    name = "ftr-tweet-index-generation-adhoc-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.ftr_tweet.FTRClusterToTweetIndexGenerationAdhoc",
    dependencies = [
        ":ftr_bq_generation",
    ],
)

jvm_binary(
    name = "oon-ftr-tweet-index-generation-adhoc-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.ftr_tweet.OONFTRClusterToTweetIndexGenerationAdhoc",
    dependencies = [
        ":ftr_bq_generation",
    ],
)

jvm_binary(
    name = "ftr-tweet-index-generation-pop1000-rnkdecay11-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.ftr_tweet.FTRPop1000RankDecay11ClusterToTweetIndexGenerationBatch",
    dependencies = [
        ":ftr_bq_generation",
    ],
)

jvm_binary(
    name = "ftr-tweet-index-generation-pop10000-rnkdecay11-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.ftr_tweet.FTRPop10000RankDecay11ClusterToTweetIndexGenerationBatch",
    dependencies = [
        ":ftr_bq_generation",
    ],
)

jvm_binary(
    name = "oon-ftr-tweet-index-generation-pop1000-rnkdecay-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.ftr_tweet.OONFTRPop1000RankDecayClusterToTweetIndexGenerationBatch",
    dependencies = [
        ":ftr_bq_generation",
    ],
)

jvm_binary(
    name = "ftr-tweet-index-generation-decayed-sum-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.ftr_tweet.DecayedSumClusterToTweetIndexGenerationBatch",
    dependencies = [
        ":ftr_bq_generation",
    ],
)

create_datasets(
    base_name = "simclusters_ftr_adhoc_cluster_to_tweet_index",
    key_type = "com.twitter.simclusters_v2.thriftscala.FullClusterId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ClusterTopTweetsInjection.clusterIdToTopKTweetsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.TopKTweetsWithScores",
    scala_dependencies = [
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "simclusters_oon_ftr_adhoc_cluster_to_tweet_index",
    key_type = "com.twitter.simclusters_v2.thriftscala.FullClusterId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ClusterTopTweetsInjection.clusterIdToTopKTweetsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.TopKTweetsWithScores",
    scala_dependencies = [
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "simclusters_ftr_pop1000_rnkdecay11_cluster_to_tweet_index",
    key_type = "com.twitter.simclusters_v2.thriftscala.FullClusterId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ClusterTopTweetsInjection.clusterIdToTopKTweetsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.TopKTweetsWithScores",
    scala_dependencies = [
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "simclusters_ftr_pop10000_rnkdecay11_cluster_to_tweet_index",
    key_type = "com.twitter.simclusters_v2.thriftscala.FullClusterId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ClusterTopTweetsInjection.clusterIdToTopKTweetsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.TopKTweetsWithScores",
    scala_dependencies = [
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "simclusters_oon_ftr_pop1000_rnkdecay_cluster_to_tweet_index",
    key_type = "com.twitter.simclusters_v2.thriftscala.FullClusterId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ClusterTopTweetsInjection.clusterIdToTopKTweetsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.TopKTweetsWithScores",
    scala_dependencies = [
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)

create_datasets(
    base_name = "simclusters_decayed_sum_cluster_to_tweet_index",
    key_type = "com.twitter.simclusters_v2.thriftscala.FullClusterId",
    platform = "java8",
    role = "cassowary",
    scala_schema = "com.twitter.simclusters_v2.hdfs_sources.injections.ClusterTopTweetsInjection.clusterIdToTopKTweetsInjection",
    segment_type = "snapshot",
    tags = ["bazel-compatible"],
    val_type = "com.twitter.simclusters_v2.thriftscala.TopKTweetsWithScores",
    scala_dependencies = [
        "src/scala/com/twitter/scalding_internal/multiformat/format",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources/injections",
    ],
)
