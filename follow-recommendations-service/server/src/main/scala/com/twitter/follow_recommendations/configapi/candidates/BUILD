scala_library(
    compiler_option_sets = ["fatal_warnings"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "3rdparty/jvm/com/google/inject:guice",
        "configapi/configapi-core",
        "configapi/configapi-decider",
        "configapi/configapi-featureswitches:v2",
        "featureswitches/featureswitches-core",
        "featureswitches/featureswitches-core:v2",
        "featureswitches/featureswitches-core/src/main/scala/com/twitter/featureswitches/v2/builder",
        "follow-recommendations-service/common/src/main/scala/com/twitter/follow_recommendations/common/base",
        "follow-recommendations-service/common/src/main/scala/com/twitter/follow_recommendations/common/models",
        "follow-recommendations-service/server/src/main/scala/com/twitter/follow_recommendations/configapi/deciders",
        "follow-recommendations-service/server/src/main/scala/com/twitter/follow_recommendations/configapi/params",
    ],
)
