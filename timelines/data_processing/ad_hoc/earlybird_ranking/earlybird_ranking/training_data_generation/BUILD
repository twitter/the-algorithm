create_datarecord_datasets(
    base_name = "earlybird_recap_data_records",
    platform = "java8",
    role = "timelines",
    segment_type = "partitioned",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
    ],
)

create_datarecord_datasets(
    base_name = "earlybird_rectweet_data_records",
    platform = "java8",
    role = "timelines",
    segment_type = "partitioned",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
    ],
)

scala_library(
    name = "training_data_generation",
    sources = ["*.scala"],
    platform = "java8",
    strict_deps = True,
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
    ],
    dependencies = [
        ":earlybird_recap_data_records-java",
        ":earlybird_rectweet_data_records-java",
        "3rdparty/jvm/com/ibm/icu:icu4j",
        "3rdparty/src/jvm/com/twitter/scalding:json",
        "src/java/com/twitter/ml/api:api-base",
        "src/java/com/twitter/ml/api/constant",
        "src/java/com/twitter/ml/api/matcher",
        "src/java/com/twitter/search/common/features",
        "src/scala/com/twitter/ml/api:api-base",
        "src/scala/com/twitter/ml/api/analytics",
        "src/scala/com/twitter/ml/api/util",
        "src/scala/com/twitter/scalding_internal/dalv2",
        "src/scala/com/twitter/scalding_internal/dalv2/dataset",
        "src/scala/com/twitter/scalding_internal/job",
        "src/scala/com/twitter/scalding_internal/job/analytics_batch",
        "src/scala/com/twitter/timelines/prediction/features/common",
        "src/scala/com/twitter/timelines/prediction/features/recap",
        "src/thrift/com/twitter/ml/api:data-java",
        "src/thrift/com/twitter/ml/api:dataset-analytics-java",
        "timelines/data_processing/ad_hoc/earlybird_ranking/common",
        "timelines/data_processing/ad_hoc/recap/dataset_utils",
        "timelines/data_processing/ad_hoc/recap/offline_execution",
        "timelines/data_processing/util/execution",
    ],
)

hadoop_binary(
    name = "bin",
    basename = "earlybird_training_data_generation-deploy",
    main = "com.twitter.scalding.Tool",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":training_data_generation",
    ],
)

hadoop_binary(
    name = "earlybird_training_data_generation_prod",
    basename = "earlybird_training_data_generation_prod-deploy",
    main = "com.twitter.timelines.data_processing.ad_hoc.earlybird_ranking.training_data_generation.EarlybirdTrainingDataProdJob",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":training_data_generation",
    ],
)
