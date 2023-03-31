scala_library(
    name = "model_evaluation",
    sources = ["*.scala"],
    platform = "java8",
    strict_deps = False,
    dependencies = [
        "3rdparty/src/jvm/com/twitter/scalding:json",
        "src/scala/com/twitter/ml/api:api-base",
        "src/scala/com/twitter/ml/api/prediction_engine",
        "src/scala/com/twitter/ml/api/util",
        "src/scala/com/twitter/scalding_internal/job",
        "src/scala/com/twitter/timelines/prediction/adapters/recap",
        "src/scala/com/twitter/timelines/prediction/features/recap",
        "timelines/data_processing/ad_hoc/earlybird_ranking/common",
        "timelines/data_processing/util:rich-request",
        "timelines/data_processing/util/example",
        "timelines/data_processing/util/execution",
        "twadoop_config/configuration/log_categories/group/timelines:timelineservice_injection_request_log-scala",
    ],
)

hadoop_binary(
    name = "bin",
    basename = "earlybird_model_evaluation-deploy",
    main = "com.twitter.timelines.data_processing.ad_hoc.earlybird_ranking.model_evaluation.EarlybirdModelEvaluationJob",
    platform = "java8",
    runtime_platform = "java8",
    tags = [
        "bazel-compatible",
        "bazel-compatible:migrated",
        "bazel-only",
    ],
    dependencies = [
        ":model_evaluation",
    ],
)
