scala_library(
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/google/inject:guice",
        "3rdparty/jvm/com/google/inject/extensions:guice-assistedinject",
        "3rdparty/jvm/net/codingwell:scala-guice",
        "3rdparty/jvm/org/slf4j:slf4j-api",
        "finatra/inject/inject-core/src/main/scala",
        "follow-recommendations-service/common/src/main/scala/com/twitter/follow_recommendations/common/base",
        "follow-recommendations-service/common/src/main/scala/com/twitter/follow_recommendations/common/clients/deepbirdv2",
        "follow-recommendations-service/common/src/main/scala/com/twitter/follow_recommendations/common/constants",
        "follow-recommendations-service/common/src/main/scala/com/twitter/follow_recommendations/common/feature_hydration/sources",
        "follow-recommendations-service/common/src/main/scala/com/twitter/follow_recommendations/common/models",
        "follow-recommendations-service/common/src/main/scala/com/twitter/follow_recommendations/common/rankers/common",
        "follow-recommendations-service/common/src/main/scala/com/twitter/follow_recommendations/common/rankers/ml_ranker/scoring",
        "follow-recommendations-service/common/src/main/scala/com/twitter/follow_recommendations/common/rankers/utils",
        "follow-recommendations-service/common/src/main/scala/com/twitter/follow_recommendations/common/utils",
        "src/java/com/twitter/ml/api:api-base",
        "util/util-slf4j-api/src/main/scala",
    ],
)

# This is to import only the params from MlRanker, for instance to get request-level heavy ranker.
scala_library(
    name = "ml_ranker_params",
    sources = [
        "MlRankerParams.scala",
    ],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "follow-recommendations-service/common/src/main/scala/com/twitter/follow_recommendations/common/rankers/common",
        "timelines/src/main/scala/com/twitter/timelines/config/configapi",
    ],
)
