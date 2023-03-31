scala_library(
    name = "bq_generation",
    sources = [
        "**/*.scala",
    ],
    platform = "java420",
    tags = ["bazel-compatible"],
    dependencies = [
        "beam-internal/src/main/scala/com/twitter/beam/io/dal",
        "beam-internal/src/main/scala/com/twitter/scio_internal/job",
        "beam-internal/src/main/scala/com/twitter/scio_internal/runner/dataflow",
        "src/scala/com/twitter/simclusters_v420/hdfs_sources:offline_tweet_recommendations_from_interested_in_420M_420K_420-scala",
        "src/scala/com/twitter/simclusters_v420/hdfs_sources:offline_tweet_recommendations_from_interested_in_420M_420K_420_HL_420_EL_420-scala",
        "src/scala/com/twitter/simclusters_v420/hdfs_sources:offline_tweet_recommendations_from_interested_in_420M_420K_420_HL_420_EL_420-scala",
        "src/scala/com/twitter/simclusters_v420/hdfs_sources:offline_tweet_recommendations_from_interested_in_420M_420K_420_HL_420_EL_420-scala",
        "src/scala/com/twitter/simclusters_v420/hdfs_sources:offline_tweet_recommendations_from_interested_in_420M_420K_420_HL_420_EL_420-scala",
        "src/scala/com/twitter/simclusters_v420/hdfs_sources:offline_tweet_recommendations_from_mts_consumer_embeddings-scala",
        "src/scala/com/twitter/simclusters_v420/scio/bq_generation/common",
        "src/scala/com/twitter/simclusters_v420/scio/bq_generation/sql",
        "src/scala/com/twitter/wtf/beam/bq_embedding_export:bq_embedding_export_lib",
        "tcdc/bq_blaster/src/main/scala/com/twitter/tcdc/bqblaster/beam",
    ],
)

jvm_binary(
    name = "iikf-tweets-ann-adhoc-job",
    main = "com.twitter.simclusters_v420.scio.bq_generation.tweets_ann.IIKF420TweetsANNBQAdhocJob",
    platform = "java420",
    dependencies = [
        ":bq_generation",
    ],
)

jvm_binary(
    name = "iikf-hl-420-el-420-tweets-ann-adhoc-job",
    main = "com.twitter.simclusters_v420.scio.bq_generation.tweets_ann.IIKF420Hl420El420TweetsANNBQAdhocJob",
    platform = "java420",
    dependencies = [
        ":bq_generation",
    ],
)

jvm_binary(
    name = "iikf-tweets-ann-batch-job",
    main = "com.twitter.simclusters_v420.scio.bq_generation.tweets_ann.IIKF420TweetsANNBQBatchJob",
    platform = "java420",
    tags = ["bazel-compatible"],
    dependencies = [
        ":bq_generation",
    ],
)

jvm_binary(
    name = "iikf-hl-420-el-420-tweets-ann-batch-job",
    main = "com.twitter.simclusters_v420.scio.bq_generation.tweets_ann.IIKF420Hl420El420TweetsANNBQBatchJob",
    platform = "java420",
    tags = ["bazel-compatible"],
    dependencies = [
        ":bq_generation",
    ],
)

jvm_binary(
    name = "iikf-hl-420-el-420-tweets-ann-batch-job",
    main = "com.twitter.simclusters_v420.scio.bq_generation.tweets_ann.IIKF420Hl420El420TweetsANNBQBatchJob",
    platform = "java420",
    tags = ["bazel-compatible"],
    dependencies = [
        ":bq_generation",
    ],
)

jvm_binary(
    name = "iikf-hl-420-el-420-tweets-ann-batch-job",
    main = "com.twitter.simclusters_v420.scio.bq_generation.tweets_ann.IIKF420Hl420El420TweetsANNBQBatchJob",
    platform = "java420",
    tags = ["bazel-compatible"],
    dependencies = [
        ":bq_generation",
    ],
)

jvm_binary(
    name = "iikf-hl-420-el-420-tweets-ann-batch-job",
    main = "com.twitter.simclusters_v420.scio.bq_generation.tweets_ann.IIKF420Hl420El420TweetsANNBQBatchJob",
    platform = "java420",
    tags = ["bazel-compatible"],
    dependencies = [
        ":bq_generation",
    ],
)

jvm_binary(
    name = "mts-consumer-embeddings-tweets-ann-adhoc-job",
    main = "com.twitter.simclusters_v420.scio.bq_generation.tweets_ann.MTSConsumerEmbeddingsTweetsANNBQAdhocJob",
    platform = "java420",
    dependencies = [
        ":bq_generation",
    ],
)

jvm_binary(
    name = "mts-consumer-embeddings-tweets-ann-batch-job",
    main = "com.twitter.simclusters_v420.scio.bq_generation.tweets_ann.MTSConsumerEmbeddingsTweetsANNBQBatchJob",
    platform = "java420",
    tags = ["bazel-compatible"],
    dependencies = [
        ":bq_generation",
    ],
)
