scala_library(
    name = "bq_generation",
    sources = [
        "**/*.scala",
    ],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "beam-internal/src/main/scala/com/twitter/beam/io/dal",
        "beam-internal/src/main/scala/com/twitter/scio_internal/job",
        "beam-internal/src/main/scala/com/twitter/scio_internal/runner/dataflow",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources:offline_tweet_recommendations_from_interested_in_20M_145K_2020-scala",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources:offline_tweet_recommendations_from_interested_in_20M_145K_2020_HL_0_EL_15-scala",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources:offline_tweet_recommendations_from_interested_in_20M_145K_2020_HL_2_EL_15-scala",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources:offline_tweet_recommendations_from_interested_in_20M_145K_2020_HL_2_EL_50-scala",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources:offline_tweet_recommendations_from_interested_in_20M_145K_2020_HL_8_EL_50-scala",
        "src/scala/com/twitter/simclusters_v2/hdfs_sources:offline_tweet_recommendations_from_mts_consumer_embeddings-scala",
        "src/scala/com/twitter/simclusters_v2/scio/bq_generation/common",
        "src/scala/com/twitter/simclusters_v2/scio/bq_generation/sql",
        "src/scala/com/twitter/wtf/beam/bq_embedding_export:bq_embedding_export_lib",
        "tcdc/bq_blaster/src/main/scala/com/twitter/tcdc/bqblaster/beam",
    ],
)

jvm_binary(
    name = "iikf-tweets-ann-adhoc-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.tweets_ann.IIKF2020TweetsANNBQAdhocJob",
    platform = "java8",
    dependencies = [
        ":bq_generation",
    ],
)

jvm_binary(
    name = "iikf-hl-8-el-50-tweets-ann-adhoc-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.tweets_ann.IIKF2020Hl8El50TweetsANNBQAdhocJob",
    platform = "java8",
    dependencies = [
        ":bq_generation",
    ],
)

jvm_binary(
    name = "iikf-tweets-ann-batch-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.tweets_ann.IIKF2020TweetsANNBQBatchJob",
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        ":bq_generation",
    ],
)

jvm_binary(
    name = "iikf-hl-0-el-15-tweets-ann-batch-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.tweets_ann.IIKF2020Hl0El15TweetsANNBQBatchJob",
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        ":bq_generation",
    ],
)

jvm_binary(
    name = "iikf-hl-2-el-15-tweets-ann-batch-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.tweets_ann.IIKF2020Hl2El15TweetsANNBQBatchJob",
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        ":bq_generation",
    ],
)

jvm_binary(
    name = "iikf-hl-2-el-50-tweets-ann-batch-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.tweets_ann.IIKF2020Hl2El50TweetsANNBQBatchJob",
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        ":bq_generation",
    ],
)

jvm_binary(
    name = "iikf-hl-8-el-50-tweets-ann-batch-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.tweets_ann.IIKF2020Hl8El50TweetsANNBQBatchJob",
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        ":bq_generation",
    ],
)

jvm_binary(
    name = "mts-consumer-embeddings-tweets-ann-adhoc-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.tweets_ann.MTSConsumerEmbeddingsTweetsANNBQAdhocJob",
    platform = "java8",
    dependencies = [
        ":bq_generation",
    ],
)

jvm_binary(
    name = "mts-consumer-embeddings-tweets-ann-batch-job",
    main = "com.twitter.simclusters_v2.scio.bq_generation.tweets_ann.MTSConsumerEmbeddingsTweetsANNBQBatchJob",
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        ":bq_generation",
    ],
)
