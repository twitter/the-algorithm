scala_library(
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        ":candidate-algorithm-adapter",
        ":client-context-adapter",
        ":post-nux-algorithm-adapter",
        ":pre-fetched-feature-adapter",
    ],
)

target(
    name = "common",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/org/slf4j:slf4j-api",
        "follow-recommendations-service/common/src/main/scala/com/ExTwitter/follow_recommendations/common/base",
        "follow-recommendations-service/common/src/main/scala/com/ExTwitter/follow_recommendations/common/clients/strato",
        "follow-recommendations-service/common/src/main/scala/com/ExTwitter/follow_recommendations/common/constants",
        "follow-recommendations-service/common/src/main/scala/com/ExTwitter/follow_recommendations/common/feature_hydration/common",
        "follow-recommendations-service/common/src/main/scala/com/ExTwitter/follow_recommendations/common/models",
        "follow-recommendations-service/common/src/main/scala/com/ExTwitter/follow_recommendations/common/utils",
        "src/java/com/ExTwitter/ml/api:api-base",
        "src/scala/com/ExTwitter/ml/api/util",
        "src/scala/com/ExTwitter/onboarding/relevance/util/metadata",
        "util/util-slf4j-api/src/main/scala",
    ],
)

scala_library(
    name = "candidate-algorithm-adapter",
    sources = [
        "CandidateAlgorithmAdapter.scala",
    ],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        ":common",
        "hermit/hermit-core/src/main/scala/com/ExTwitter/hermit/constants",
    ],
)

scala_library(
    name = "client-context-adapter",
    sources = [
        "ClientContextAdapter.scala",
    ],
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        ":common",
        "snowflake/src/main/scala/com/ExTwitter/snowflake/id",
    ],
)

scala_library(
    name = "post-nux-algorithm-adapter",
    sources = [
        "PostNuxAlgorithmAdapter.scala",
    ],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        ":common",
        "src/scala/com/ExTwitter/ml/featurestore/catalog/features/customer_journey:post-nux-algorithm-aggregate",
    ],
)

scala_library(
    name = "pre-fetched-feature-adapter",
    sources = [
        "PreFetchedFeatureAdapter.scala",
    ],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        ":common",
    ],
)
