# Without this alias, library :follow-recommendations-service_lib would conflict with :bin
alias(
    name = "follow-recommendations-service",
    target = ":follow-recommendations-service_lib",
)

target(
    name = "follow-recommendations-service_lib",
    dependencies = [
        "follow-recommendations-service/server/src/main/scala/com/twitter/follow_recommendations",
        "follow-recommendations-service/server/src/main/scala/com/twitter/follow_recommendations/models",
    ],
)

jvm_binary(
    name = "bin",
    basename = "follow-recommendations-service",
    main = "com.twitter.follow_recommendations.FollowRecommendationsServiceThriftServerMain",
    runtime_platform = "java11",
    tags = ["bazel-compatible"],
    dependencies = [
        ":follow-recommendations-service",
        "3rdparty/jvm/ch/qos/logback:logback-classic",
        "finagle/finagle-zipkin-scribe/src/main/scala",
        "finatra/inject/inject-logback/src/main/scala",
        "loglens/loglens-logback/src/main/scala/com/twitter/loglens/logback",
        "twitter-server-internal/src/main/scala",
        "twitter-server/logback-classic/src/main/scala",
    ],
)

#  Aurora Workflows build phase convention requires a jvm_app named with ${project-name}-app
jvm_app(
    name = "follow-recommendations-service-app",
    archive = "zip",
    binary = ":bin",
    bundles = [
        bundle(
            fileset = [
                "server/src/main/resources/*",
                "server/src/main/resources/**/*",
            ],
            owning_target = "follow-recommendations-service/server/src/main/resources:frs_resources",
            relative_to = "server/src/main/resources",
        ),
    ],
    tags = ["bazel-compatible"],
)
